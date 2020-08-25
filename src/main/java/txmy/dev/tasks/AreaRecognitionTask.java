package txmy.dev.tasks;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import txmy.dev.chest.HungerChest;
import txmy.dev.game.Game;
import txmy.dev.utils.Chat;
import txmy.dev.utils.Common;
import txmy.dev.utils.PermLevel;

public class AreaRecognitionTask extends BukkitRunnable {

    private final Game game;

    public AreaRecognitionTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Common.broadcast(PermLevel.ADMIN,
                Chat.of()
                .withManagementPrefix()
                .withSpace()
                .with("&fArea recognition will begin for game &e"
                        + game.getId()
                        + "... &fBe sure there's no one playing cause this is gonna lag af").get());

        int quantity = 0;

        // Just looping while all loaded chunks cause if it was through all the chunks it
        // will never end lol
        for(Chunk c : game.getWorld().getLoadedChunks()) {
            // To loop through all the chunk
            for(int x = 16; x > 0; --x) {
                // Same
                for(int z = 16; z > 0; --z) {
                    int y = 255;

                    // Needed y also // To make this process faster we set the limit to 25
                    // because cmon you aren't going to put a chest < 25
                    while(y > 25) {
                        Block block = c.getBlock(x, y, z);

                        if(block.getType().name().contains("CHEST")) {
                            HungerChest chest = new HungerChest(game, 1, block);
                            game.getLoot().add(chest);

                            ++quantity;
                        }

                        --y;
                    }
                }
            }
        }

        Common.broadcast(PermLevel.ADMIN,
                Chat.of()
                .withManagementPrefix()
                .withSpace()
                .with("&fArea of game &e" + game.getId() + " &fwas recognized, found " + quantity + " chests.")
                 .with("All the chest tier's are by default set to 1, you may change it with the command.")
                .get());
    }
}
