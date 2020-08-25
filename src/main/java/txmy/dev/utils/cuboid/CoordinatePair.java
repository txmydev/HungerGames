package txmy.dev.utils.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class CoordinatePair {
    private String worldName;
    private int x;
    private int z;

    public CoordinatePair(Block block) {
        this(block.getWorld(), block.getX(), block.getZ());
    }

    public CoordinatePair(World world, int x, int z) {
        this.worldName = world.getName();
        this.x = x;
        this.z = z;
    }

    public World getWorld() {
        return Bukkit.getWorld((String)this.worldName);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoordinatePair)) {
            return false;
        }
        CoordinatePair that = (CoordinatePair)o;
        if (this.x != that.x) {
            return false;
        }
        if (this.z != that.z) {
            return false;
        }
        if (this.worldName != null ? !this.worldName.equals(that.worldName) : that.worldName != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = this.worldName != null ? this.worldName.hashCode() : 0;
        result = 31 * result + this.x;
        result = 31 * result + this.z;
        return result;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }
}

