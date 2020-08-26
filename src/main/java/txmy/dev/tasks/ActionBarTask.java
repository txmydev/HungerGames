package txmy.dev.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import txmy.dev.HungerGames;
import txmy.dev.game.Game;
import txmy.dev.profile.Profile;
import txmy.dev.utils.Common;
import txmy.dev.utils.ConfigCursor;
import txmy.dev.utils.FileConfig;

public class ActionBarTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(this::updateActionBar);
    }

    private void updateActionBar(Player player) {
        FileConfig fileConfig = HungerGames.getInstance().getLangConfig();
        ConfigCursor configCursor = new ConfigCursor(fileConfig, "actionbar");
        if (!configCursor.getBoolean("enabled")) return;

        Profile profile = HungerGames.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        String path = "";

        if (!profile.isPlaying()) path = "main-lobby";
        else {
            Game game = profile.getGame();

            switch (game.getState()) {
                case LOBBY:
                    path = "lobby";
                    break;
                case STARTING:
                    path = "starting";
                    break;
                case GAME:
                    path = "game";
                    break;
            }
        }

        String result = configCursor.getString(path);
        if(result.equals("none")) return;

        Common.sendActionBar(player, result);
    }
}
