package txmy.dev.utils;

import txmy.dev.HungerGames;

public class Language {

    private static final ConfigCursor configCursor = new ConfigCursor(HungerGames.getInstance().getLangConfig(), "");

    public static String getGame(String path) {
        return Common.colorize(configCursor.getString("game." + path));
    }


}
