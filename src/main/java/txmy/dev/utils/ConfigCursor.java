package txmy.dev.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigCursor {

    private FileConfiguration config;
    private FileConfig fileConfig;
    private String basePath;

    public ConfigCursor(FileConfiguration config, String path) {
        this.basePath = path;
        this.config = config;
        this.fileConfig = null;
    }

    public ConfigCursor(FileConfig config, String path){
        this.basePath = path;
        this.config = config.getConfig();
        this.fileConfig = null;
    }

    public String getString(String path){
        return config.getString(basePath +"." + path);
    }

    public int getInt(String path){
        return getInt(path, 0);
    }

    public int getInt(String path, int defaultVal) {
        return config.getInt(basePath + "." + path, defaultVal);
    }

    public double getDouble(String path) {
        return config.getDouble(basePath + "." + path);
    }

    public float getFloat(String path) {
        return config.getFloat(basePath + "." + path);
    }

    public Object get(String path){
        return config.get(basePath +"." + path);
    }

    public boolean contains(String path){
        return config.contains(basePath+"."+path);
    }

    public List<String> getStringList(String path){
        return config.getStringList(basePath +"."+path);
    }

    public List<Integer> getIntegerList(String path){
        return config.getIntegerList(basePath+"."+path);
    }

    public List<?> getList(String path){
        return config.getList(basePath +"."+path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(basePath + "." + path);
    }
}
