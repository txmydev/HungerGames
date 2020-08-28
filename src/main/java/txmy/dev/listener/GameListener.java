package txmy.dev.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import txmy.dev.HungerGames;
import txmy.dev.event.GameJoinEvent;
import txmy.dev.event.GameLeaveEvent;
import txmy.dev.event.GamePreStartEvent;
import txmy.dev.event.GameStartEvent;
import txmy.dev.game.Game;
import txmy.dev.game.GameManager;
import txmy.dev.profile.Profile;
import txmy.dev.utils.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

        switch (game.getState()) {
            case LOBBY:
                if (game.getRequired() == game.getPlayers().size()) {
                    GamePreStartEvent preStart = new GamePreStartEvent(game);
                    preStart.call();
                    return;
                }

                game.announce(Language.getGame("join"));
                player.teleport(game.getSpawn());
                break;
            case STARTING:
                if (game.getSpawnLocations().size() <= 0) {
                    player.sendMessage(Language.getGame("no-spawns-available"));
                    break;
                }

                player.teleport(game.getSpawnLocations().remove(0));
                PlayerUtil.prepareStart(player);
                break;
            case GAME:
                PlayerUtil.prepareSpectator(player);

                List<Player> list = game.getPlayers().stream().filter(other -> other.getUniqueId() != player.getUniqueId())
                        .collect(Collectors.toList());

                Player random = Common.getRandom(list);
                if (random == null) break;

                player.teleport(random);
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

        switch (game.getState()) {

        }
    }

    @EventHandler
    public void onGamePreStart(GamePreStartEvent event) {
        Game game = event.getGame();

        game.getStartTask().call();

        // Teleport players
        // For now doing list.remove(0) because every time a game is loaded it fill's up again so
        game.getPlayers().forEach(player -> {
            player.teleport(game.getSpawnLocations().remove(0));
            HungerGames.getInstance().getNmsHandler().mount(player);
        });

        FileConfig fileConfig = HungerGames.getInstance().getMainConfig();
        ConfigCursor configCursor = new ConfigCursor(fileConfig, "");

        if (configCursor.getBoolean("fill-chest-while-game")) return;

        GameManager gameManager = HungerGames.getInstance().getGameManager();
        game.getLoot().forEach(gameManager::fillChest);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        Game game = event.getGame();
        game.getGameTask().call();

        game.getPlayers().forEach(HungerGames.getInstance().getNmsHandler()::unMount);
    }
}
