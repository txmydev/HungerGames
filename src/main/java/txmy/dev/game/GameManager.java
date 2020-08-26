package txmy.dev.game;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.inventory.ItemStack;
import txmy.dev.HungerGames;
import txmy.dev.adapter.object.ItemStackAdapter;
import txmy.dev.adapter.object.LocationAdapter;
import txmy.dev.chest.HungerChest;
import txmy.dev.event.GameRegisterEvent;
import txmy.dev.utils.ConfigCursor;
import txmy.dev.utils.Debug;
import txmy.dev.utils.FileConfig;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class GameManager {


    private HungerGames plugin;
    private final Set<Game> games;
    private Map<Integer, ItemStack[]> loot;

    public GameManager(HungerGames plugin) {
        this.plugin = plugin;

        this.games = new HashSet<>();
        this.loot = new HashMap<>();
    }

    public void loadAll() {
        FileConfig fileConfig = HungerGames.getInstance().getArenasConfig();
        fileConfig.getConfig().getKeys(false).forEach(this::load);

        Debug.debug(games.size()  + " were loaded from arenas.yml file.");

        this.loadLoot();
    }

    public void load(String id) {
        FileConfig fileConfig = HungerGames.getInstance().getArenasConfig();
        ConfigCursor configCursor = new ConfigCursor(fileConfig, id);

        if (!fileConfig.getConfig().contains(id)) {
            Debug.debug("There's no game in config with 'id' " + id);
            return;
        }

        Game game = new Game(id);

        loadSpawn(game, configCursor);
        loadSpawnPoints(game, configCursor);
        loadLoot(game, configCursor);
        loadWorld(game, configCursor);

        game.setRequired(configCursor.getInt("required"));
        game.setMax(configCursor.getInt("max", 12));

        new GameRegisterEvent(game).call();
        games.add(game);
    }

    private void loadLoot(Game game, ConfigCursor configCursor) {
        if(!configCursor.contains("loot")) return;

        String[] serialized = configCursor.getString("loot").split("//");

        Location location = LocationAdapter.deserialize(serialized[0]);
        int tier = Integer.parseInt(serialized[1]);

        game.getLoot().add(new HungerChest(game, tier, location.getBlock()));
    }

    private void loadWorld(Game game, ConfigCursor configCursor) {
        if(!configCursor.contains("world")) return;

        World world = new WorldCreator(configCursor.getString("world"))
                .environment(World.Environment.NORMAL)
                .generateStructures(false)
                .createWorld();

        game.setWorld(world);
        Debug.debug("World of game " + game.getId() + " was loaded, (created if didn't exist)");
    }

    private void loadSpawn(Game game, ConfigCursor configCursor) {
        if (!configCursor.contains("spawn")) return;
        game.setSpawn(LocationAdapter.deserialize(configCursor.getString("spawn")));
    }

    private void loadSpawnPoints(Game game, ConfigCursor configCursor) {
        if (!configCursor.contains("spawnLocations")) return;
        String[] semiColonSplit = configCursor.getString("spawnLocations")
                .split(";");

        for (String s : semiColonSplit)
            game.getSpawnLocations().add(LocationAdapter.deserialize(s));
    }

    public void loadLoot() {
        FileConfig fileConfig = HungerGames.getInstance().getLootConfig();
        ConfigCursor configCursor = new ConfigCursor(fileConfig,"");
        for(int i = 1; i < 3; i ++) loot.putIfAbsent(i, new ItemStack[128]);

        configCursor.getStringList("tierI").forEach(serialized -> loot.get(1)[loot.get(1).length] = ItemStackAdapter.deserializeItemStack(serialized));
        configCursor.getStringList("tierII").forEach(serialized -> loot.get(2)[loot.get(2).length] = ItemStackAdapter.deserializeItemStack(serialized));
        configCursor.getStringList("tierIII").forEach(serialized -> loot.get(3)[loot.get(3).length] = ItemStackAdapter.deserializeItemStack(serialized));

        for(int i = 1; i < 3; i ++)
            Debug.debug("Now tier " + i + " chest has " + Arrays.stream(loot.get(i)).filter(Objects::nonNull).filter(item -> item.getType() != Material.AIR).count());
    }

    public void fillChest(int tier, HungerChest chest) {
        if(!this.loot.containsKey(tier)) throw new IllegalArgumentException("There are no chests tier " + tier);

        ItemStack[] items = loot.get(tier);

        // Probably adding a message later..
        if(items.length == 0) return;

        Random random = ThreadLocalRandom.current();

        int next = random.nextInt(items.length - 1);
        ItemStack randomItem = items[next];

        int randomSlot = random.nextInt(26);
        chest.setItem(randomSlot, randomItem);

        // So later on when I test it's easier to see if it's working
        Debug.debug("Filled a chest at " + chest.getBlock().getLocation());
    }

    public void rollback(Game game) {

    }

}
