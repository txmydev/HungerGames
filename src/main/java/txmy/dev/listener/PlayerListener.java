package txmy.dev.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import txmy.dev.HungerGames;
import txmy.dev.event.PlayerChangeStateEvent;
import txmy.dev.profile.Profile;
import txmy.dev.profile.ProfileHandler;
import txmy.dev.scoreboard.HungerBoard;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onAsyncLogin(AsyncPlayerPreLoginEvent event) {
        UUID id = event.getUniqueId();
        String name = event.getName();

        HungerGames plugin = HungerGames.getInstance();
        plugin.getProfileHandler().load(id, name);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        // Init scoreboard
        new HungerBoard(player);
    }

    @EventHandler
    public void onChangeState(PlayerChangeStateEvent event) {
    }
}
