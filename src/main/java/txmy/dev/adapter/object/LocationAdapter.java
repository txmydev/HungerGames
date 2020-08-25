package txmy.dev.adapter.object;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationAdapter {

    public static String serialize(Location location) {
        return location.getWorld() + "," + location.getX() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static String serializeMultiple(Location... locations) {
        StringBuilder builder = new StringBuilder();
        for (Location location : locations) builder.append(serialize(location)).append(";");
        return builder.toString();
    }

    public static Location deserialize(String input) {
        String[] commaSplit = input.split(",");

        double x = Double.parseDouble(commaSplit[1]);
        double y = Double.parseDouble(commaSplit[2]);
        double z = Double.parseDouble(commaSplit[3]);
        float yaw = Float.parseFloat(commaSplit[4]);
        float pitch = Float.parseFloat(commaSplit[5]);
        World world = Bukkit.getWorld(commaSplit[0]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location[] deserializeMultiple(String input) {
        String[] semiColonSplit = input.split(";");
        Location[] locations = new Location[semiColonSplit.length];

        for (int i = 0; i < semiColonSplit.length; i++) {
            try {
                locations[i] = deserialize(semiColonSplit[i]);
            } catch (Exception ex) {
                Bukkit.getLogger().severe("Failed while deserializing a location, error type: " + ex + ", input providen: " + semiColonSplit[i]);
            }
        }

        return locations;
    }

}
