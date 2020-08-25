package txmy.dev.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import txmy.dev.utils.Common;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class Board {

    @Getter private static HashMap<UUID, Board> boards = new HashMap<>();

    private Player player;
    private Scoreboard scoreboard;
    private Objective sidebar;

    public Board(Player player) {
        this.player = player;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        Objective name = scoreboard.registerNewObjective("name", "health");
        name.setDisplaySlot(DisplaySlot.BELOW_NAME);
        name.setDisplayName("§4❤");

        Objective tab = scoreboard.registerNewObjective("tab", "health");
        tab.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        player.setScoreboard(scoreboard);

        for (int i = 1; i <= 15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }

        boards.put(player.getUniqueId(), this);
    }

    public void setTitle(String title) {
        if (title.length() > 32) title = title.substring(0, 32);
        if (!sidebar.getDisplayName().equals(title)) sidebar.setDisplayName(Common.colorize(title));
    }

    private void setSlot(int slot, String text) {
        if (slot > 15) return;

        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);

        if (!scoreboard.getEntries().contains(entry)) sidebar.getScore(entry).setScore(slot);

        String prefix = getFirstSplit(text);

        int lastIndex = prefix.lastIndexOf(167);
        String lastColor = lastIndex >= 14 ? prefix.substring(lastIndex, prefix.length()) : ChatColor.getLastColors(prefix);

        if (lastIndex >= 14) prefix = prefix.substring(0, lastIndex);

        String suffix = getFirstSplit(lastColor + getSecondSplit(text));

        if (!team.getPrefix().equals(prefix)) team.setPrefix(prefix);
        if (!team.getSuffix().equals(suffix)) team.setSuffix(suffix);
    }

    private void removeSlot(int slot) {
        String entry = genEntry(slot);
        if (scoreboard.getEntries().contains(entry)) scoreboard.resetScores(entry);
    }

   public void setSlotsFromList(List<String> list) {
        int slot = list.size();
        if (slot < 15) for (int i = (slot + 1); i <= 15; i++) removeSlot(i);

       for (String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length() > 16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if (s.length() > 32) s = s.substring(0, 32);
        return s.length() > 16 ? s.substring(16, s.length()) : "";
    }

    public abstract void update();
}
