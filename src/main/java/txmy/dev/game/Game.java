package txmy.dev.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import txmy.dev.HungerGames;
import txmy.dev.adapter.object.LocationAdapter;
import txmy.dev.chest.HungerChest;
import txmy.dev.profile.Profile;
import txmy.dev.tasks.AreaRecognitionTask;
import txmy.dev.tasks.GameStartTask;
import txmy.dev.tasks.GameTask;
import txmy.dev.utils.Common;
import txmy.dev.utils.FileConfig;

import java.util.*;

@Getter
@Setter
public class Game {

    private final String id;
    private Location spawn;
    private List<Location> spawnLocations;
    private Set<HungerChest> loot;
    private World world;
    private boolean ended;
    private State state = State.LOBBY;
    private int required, startTime, gameTime;
    private Set<Player> watchers;
    private int max;

    private AreaRecognitionTask recognitionTask;
    private GameStartTask startTask;
    private GameTask gameTask;

    public Game(String id) {
        this.id = id;

        this.spawnLocations = new ArrayList<>();
        this.loot = new HashSet<>();
        this.watchers = new HashSet<>();

        this.recognitionTask = new AreaRecognitionTask(this);
        this.startTask = new GameStartTask(this);
        this.gameTask = new GameTask(this);
    }

    // Save only the things that are set
    public void save() {
        FileConfig fileConfig = HungerGames.getInstance().getArenasConfig();
        String path = id + ".";

        if (spawn != null) fileConfig.set(path + "spawn", LocationAdapter.serialize(spawn));
        if (spawnLocations.size() != 0)
            fileConfig.set(path + "spawnLocations", LocationAdapter.serializeMultiple(spawnLocations.toArray(spawnLocations.toArray(new Location[0]))));

        fileConfig.save();
    }


    // TODO: On dead add the player to this list
    public Set<Player> getWatchers() {
        return watchers;
    }

    // Simple enum for the state
    public enum State {
        LOBBY, STARTING, GAME;
    }

    // We could have a static list but meh...
    public Set<Player> getPlayers() {
        Set<Player> set = new HashSet<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            Profile profile = HungerGames.getInstance().getProfileHandler().getProfile(player.getUniqueId());
            if(profile == null || !profile.isPlaying() || !profile.getGame().getId().equals(id)) continue;

            set.add(player);
        }

        return set;
    }

    public void announce(String message) {
        getPlayers().forEach(player -> player.sendMessage(Common.colorize(message)));
    }
}
