package txmy.dev.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import txmy.dev.HungerGames;
import txmy.dev.game.Game;
import txmy.dev.profile.Profile;
import txmy.dev.utils.Common;
import txmy.dev.utils.ConfigCursor;
import txmy.dev.utils.FileConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HungerBoard extends Board{

    private static final SimpleDateFormat DEF_FORMAT = new SimpleDateFormat("mm:ss");
    private final Profile profile;

    public HungerBoard(Player player) {
        super(player);

        this.profile = HungerGames.getInstance().getProfileHandler().getProfile(player.getUniqueId());
    }

    @Override
    public void update() {
        FileConfig fileConfig = HungerGames.getInstance().getScoreboardConfig();
        ConfigCursor mainCursor = new ConfigCursor(fileConfig, "");
        ConfigCursor singleCursor=  new ConfigCursor(fileConfig, "single");

        setTitle(mainCursor.getString("title"));

        List<String> entries = new ArrayList<>();

        if(profile.isPlaying()) {
            Game game = profile.getGame();

            switch(game.getState()) {
                case LOBBY:
                    mainCursor.getStringList("game-lobby").forEach(str -> entries.add(format(str)));
                    break;
                case STARTING:
                    mainCursor.getStringList("starting").forEach(str -> {
                        if ("{starting}".equals(str)) singleCursor.getStringList("starting").forEach(val -> entries.add(format(val.replace("{time}", game.getStartTime() + ""))));
                         else entries.add(format(str));
                    });
                    break;
                case GAME:
                    mainCursor.getStringList("game").forEach(str -> {
                        switch(str) {
                            case "{nextevent}": break;
                            default:
                                entries.add(format(str));
                                break;
                        }
                    });
                    break;
            }
        } else mainCursor.getStringList("lobby").forEach(str -> entries.add(format(str)));

        setSlotsFromList(Common.colorize(entries));
    }

    private String format(String s) {
        return Common.colorize(s
        .replace("{wins}", profile.getWins() + "")
        .replace("{kills}", profile.getKills()+"")
        .replace("{killstreakrecord}", profile.getKillStreakRecord()+"")
        .replace("{killstreak}", profile.getKillStreak()+"")
        .replace("{deaths}", profile.getDeaths()+"")
        .replace("{global}", Bukkit.getOnlinePlayers().size()+"")
        .replace("{rating}", "TODO")
        .replace("{game-id}", profile.isPlaying() ? profile.getGame().getId() : "None")
        .replace("{required}", profile.isPlaying() ? profile.getGame().getPlayers().size() - profile.getGame().getRequired() + "" : "")
        .replace("{gametime}", profile.isPlaying() ? formatTime(profile.getGame().getGameTime()) : 0 + "")
        .replace("{watching}", profile.isPlaying() ? profile.getGame().getWatchers().size() + "" : ""));
    }

    private String formatTime(int gameTime) {
        return DEF_FORMAT.format(new Date(gameTime * 1000L));
    }
}
