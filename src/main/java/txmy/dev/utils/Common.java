package txmy.dev.utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import txmy.dev.HungerGames;

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

    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        HungerGames.getInstance().getNmsHandler().sendTitle(player, title, subTitle, fadeIn, stay, fadeOut);
    }
    
    public static void sendActionBar(Player player, String actionBar){
        HungerGames.getInstance().getNmsHandler().sendActionBar(player, actionBar);
    }
}
