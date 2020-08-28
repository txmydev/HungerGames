package txmy.dev.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import txmy.dev.HungerGames;
import txmy.dev.event.PlayerChangeStateEvent;
import txmy.dev.scoreboard.HungerBoard;
import txmy.dev.utils.Common;
import txmy.dev.utils.ConfigCursor;
import txmy.dev.utils.FileConfig;

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

        FileConfig fileConfig = HungerGames.getInstance().getLangConfig();
        ConfigCursor configCursor = new ConfigCursor(fileConfig, "titles.join");

        if (!configCursor.getBoolean("enabled")) return;

        Common.sendTitle(player, configCursor.getString("text"),
                configCursor.getString("subTitle"),
                configCursor.getInt("fadeIn"),
                configCursor.getInt("stay"),
                configCursor.getInt("fadeOut"));
    }

    @EventHandler
    public void onChangeState(PlayerChangeStateEvent event) {
    }

    /*
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);

        Profile profile = HungerGames.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if(profile.isPlaying()) {
            Game game = profile.getGame();
        }
    }
    */
}
