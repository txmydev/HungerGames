package txmy.dev.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import txmy.dev.HungerGames;

import java.io.File;

@Getter
public class FileConfig {

    private final File file;
    private final FileConfiguration config;

    public FileConfig(String name) {
        this.file = new File(HungerGames.getInstance().getDataFolder(), name);

        if(!HungerGames.getInstance().getDataFolder().exists()) HungerGames.getInstance().getDataFolder().mkdir();
        if(!file.exists()) HungerGames.getInstance().saveResource(name, false);

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows
    public void save(){
        this.config.save(file);
    }


    public void set(String path, Object value) {
        this.config.set(path, value);
    }
}
