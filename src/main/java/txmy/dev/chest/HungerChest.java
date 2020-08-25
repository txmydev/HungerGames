package txmy.dev.chest;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import txmy.dev.game.Game;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HungerChest {

    private Game game;
    private int tier;
    private Map<Integer, ItemStack> inventory;
    private Block block;

    public HungerChest(Game game, int tier, Block block) {
        this.game = game;
        this.tier = tier;
        this.block = block;
        this.inventory = new HashMap<>();
    }

    public void setItem(int slot, ItemStack item) {
        if(block == null) throw new IllegalStateException("Supposed chest is still null");
        inventory.putIfAbsent(slot, item);

        Chest chest = (Chest) block.getState();
        chest.getInventory().setItem(slot, item);

        chest.update();
    }



}
