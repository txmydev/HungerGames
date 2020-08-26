package txmy.dev;

import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;
import txmy.dev.adapter.Adapter;
import txmy.dev.adapter.impl.MongoAdapter;
import txmy.dev.adapter.impl.RedisAdapter;
import txmy.dev.database.DatabaseConfiguration;
import txmy.dev.game.GameManager;
import txmy.dev.nms.NMS;
import txmy.dev.nms.v1_8_R3;
import txmy.dev.profile.ProfileHandler;
import txmy.dev.tasks.ActionBarTask;
import txmy.dev.utils.ConfigCursor;
import txmy.dev.utils.FileConfig;

@Getter
public class HungerGames extends JavaPlugin {

    public static String MANAGEMENT_PREFIX = "&7[&eGame Management&7] &f";

    @Getter
    private static HungerGames instance;

    private Adapter<JedisPool> redis;
    private Adapter<MongoDatabase> database;

    private FileConfig mainConfig, arenasConfig, lootConfig, scoreboardConfig, langConfig;

    private GameManager gameManager;
    private ProfileHandler profileHandler;

    private NMS nmsHandler;

    @Setter
    private boolean useRedis, debug;

    @Override
    public void onEnable() {
        instance = this;

        this.mainConfig = new FileConfig("config.yml");
        this.arenasConfig = new FileConfig("arenas.yml");
        this.lootConfig = new FileConfig("loot.yml");
        this.scoreboardConfig = new FileConfig("scoreboard.yml");
        this.langConfig =new FileConfig("lang.yml");

        this.gameManager = new GameManager(this);
        this.profileHandler = new ProfileHandler(this);

        loadRedis();
        loadMongo();
        loadNetwork();
        loadHandler();

        this.gameManager.loadAll();

        new ActionBarTask().runTaskTimerAsynchronously(this, 10L, 20L);
    }

    private void loadHandler() {
        String version = Bukkit.getServer().getClass().getName().substring(Bukkit.getServer().getClass().getName().lastIndexOf("."));

        switch(version) {
            case "v1_8_R3":
                nmsHandler = new v1_8_R3();
                break;
            default:
                Bukkit.getLogger().info("This net.minecraft.server version need's to be implemented, you may use 1.8.8 spigot.");
                Bukkit.shutdown();
                break;
        }
    }

    private void loadNetwork() {

    }

    private void loadMongo() {
        ConfigCursor configCursor = new ConfigCursor(mainConfig, "mongo");
        DatabaseConfiguration config = new DatabaseConfiguration();

        config.setHost(configCursor.getString("host"));
        config.setPort(configCursor.getInt("port"));
        config.setPassword(configCursor.getString("pass"));
        config.setUsername(configCursor.getString("user"));
        config.setAuth(configCursor.getBoolean("auth"));
        config.setDatabase(configCursor.getString("database"));

        this.database = new MongoAdapter(config);
    }

    private void loadRedis() {
        ConfigCursor redis = new ConfigCursor(mainConfig, "redis");
        if (!redis.getBoolean("enabled")) return;

        DatabaseConfiguration config = new DatabaseConfiguration();

        config.setHost(redis.getString("host"));
        config.setPort(redis.getInt("port"));
        config.setPassword(redis.getString("password"));
        config.setAuth(redis.getBoolean("auth"));

        this.redis = new RedisAdapter(config);

        if (!this.redis.establishConnection()) {
            Bukkit.getLogger().severe("Couldn't establish connection to redis due to an exception, please check your information and restart the server.");
            if (this.redis.getCaughtException() != null)
                Bukkit.getLogger().severe("Exception while attempting to connect to redis: " + this.redis.getCaughtException());
            return;
        }

        useRedis = true;
    }

    public String getServerName() {
        return mainConfig.getConfig().getString("server-id");
    }

    @Override
    public void onDisable() {
        if (useRedis) redis.closeConnection();

        instance = null;
    }

}
