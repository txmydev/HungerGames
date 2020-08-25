package txmy.dev.utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Common {

    public static void broadcast(PermLevel permLevel,  String message){
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.hasPermission(permLevel.getPermission())) player.sendMessage(colorize(message));
        });

        Bukkit.getConsoleSender().sendMessage(colorize(message));
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void send(Player player, String message) {
        player.sendMessage(colorize(message));
    }

    public static List<String> colorize(List<String> entries) {
        List<String> list = Lists.newArrayList();
        entries.forEach(str ->list.add(colorize(str)));

        return list;
    }
}
