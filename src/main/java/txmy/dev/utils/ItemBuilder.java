package txmy.dev.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;

    private final Material type;
    private final int amount;
    private final int data;

    public ItemBuilder(Material type, int amount, int data) {
        this.itemStack = new ItemStack(type, amount, (short) data);

        this.type = type;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder(Material type, int amount) {
        this(type, amount, 0);
    }

    public ItemBuilder(Material type){
        this(type, 1);
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Common.colorize(name));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Common.colorize(lore));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemStack get() {
        return itemStack;
    }



}
