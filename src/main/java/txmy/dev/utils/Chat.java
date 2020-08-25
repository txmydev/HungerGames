package txmy.dev.utils;

import org.bukkit.ChatColor;
import txmy.dev.HungerGames;

public class Chat {

    private StringBuilder builder = new StringBuilder();

    private Chat(String s) {
        builder.append(s);
    }

    public static Chat of(String s) {
        return new Chat(s);
    }

    public static Chat of() {
        return new Chat("");
    }

    public Chat withManagementPrefix() {
        builder.append(HungerGames.MANAGEMENT_PREFIX);
        return this;
    }

    public Chat with(String s) {
        builder.append(s);
        return this;
    }

    public String get() {
        return Common.colorize(builder.toString());
    }

    public Chat withSpace() {
        builder.append(" ");
        return this;
    }

}
