package txmy.dev.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void prepareLobby(Player player) {
        player.setHealth(20.0);
        player.setMaxHealth(20.0);
        player.setFoodLevel(20);
        player.setExp(0.0f);
        player.setExhaustion(0.0f);
        player.setFlying(false);
        player.setLevel(0);

        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(0, new ItemBuilder(Material.WATCH).setName("&eJoin a game!").get());

        player.getInventory().setItem(8,
                new ItemBuilder(Material.ENDER_CHEST)
                    .setName("&dShop")
                    .get());
    }

    public static void prepareStart(Player player) {
        player.setHealth(20.0);
        player.setMaxHealth(20.0);
        player.setFoodLevel(20);
        player.setExp(0.0f);
        player.setExhaustion(0.0f);
        player.setFlying(false);
        player.setLevel(0);

        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        player.setGameMode(GameMode.SURVIVAL);
    }

    public static void prepareSpectator(Player player) {
        player.setHealth(20.0);
        player.setMaxHealth(20.0);
        player.setFoodLevel(20);
        player.setExp(0.0f);
        player.setExhaustion(0.0f);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setLevel(0);

        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        player.setGameMode(GameMode.SPECTATOR);

        player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS)
        .setName("&cSpectate Menu").get());
    }

}
