package txmy.dev.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import txmy.dev.HungerGames;
import txmy.dev.event.GameJoinEvent;
import txmy.dev.event.GameLeaveEvent;
import txmy.dev.event.GamePreStartEvent;
import txmy.dev.game.Game;
import txmy.dev.profile.Profile;
import txmy.dev.utils.Language;
import txmy.dev.utils.PlayerUtil;

public class GameListener implements Listener {

    /*
    Limit to handle 5 events in this class, later create another, so everything is
    ordered.
     */

    @EventHandler
    public void onGameJoinEvent(GameJoinEvent event) {
        Game game = event.getGame();
        Player player = event.getPlayer();
        Profile profile = HungerGames.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        // TODO: Move to the command
        profile.setGame(game);

        switch(game.getState()) {
            case LOBBY:
                if(game.getRequired() == game.getPlayers().size()) {
                    GamePreStartEvent preStart = new GamePreStartEvent(game);
                    preStart.call();
                    return;
                }

                game.announce(Language.getGame("join"));
                player.teleport(game.getSpawn());
                break;
            case STARTING:
                if(game.getSpawnLocations().size() <= 0) {
                    player.sendMessage(Language.getGame("no-spawns-available"));
                    break;
                }

                player.teleport(game.getSpawnLocations().remove(0));
                PlayerUtil.prepareStart(player);
                break;
            case GAME:
                PlayerUtil.prepareSpectator(player);
                break;
        }


    }

    @EventHandler
    public void onGameLeaveEvent(GameLeaveEvent event) {
        Game game = event.getGame();
        Player player = event.getPlayer();
        Profile profile = HungerGames.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        // TODO: Move to the command
        profile.setGame(null);


    }
}
