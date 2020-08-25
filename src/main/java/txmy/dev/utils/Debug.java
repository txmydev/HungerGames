package txmy.dev.utils;

import org.bukkit.Bukkit;
import txmy.dev.HungerGames;

public class Debug {

    public static void debug(String message) {
        if(!HungerGames.getInstance().isDebug()) return;

        Bukkit.getLogger().info(message);
    }

}
