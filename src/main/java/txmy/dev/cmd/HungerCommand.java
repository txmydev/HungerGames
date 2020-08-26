package txmy.dev.cmd;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import txmy.dev.cmd.impl.JoinCommand;
import txmy.dev.utils.Common;
import txmy.dev.utils.command.Command;

import java.util.*;

public class HungerCommand implements CommandExecutor {

    /*

    Command Manager for Hunger Games plugin,
    all commands will be handled here o.O

     */

    /*
     All commands:

     - /hungergames join <game-id> | Join's a game
     - /hungergames leave | Leave's the current game
     - /hungergames forceleave <player> <game-id> | Forces a player to leave.
     - /hungergames settier <game-id> <level> | Set's a chest tier (must be looking to the chest).
     - /hungergames reload | Reload's the config
     - /hungergames create <game-id> | Creates a game.
     - /hungergames backup <game-id> | Will create a backup world for that game.
     - /hungergames recognizearea <game-id> | The server will look for all chest within loaded chunks in the world.
     - /hungergames cosmetics | Cosmetic's menu
     - /hungergames chestfinder <game-id> | Will display a gui that have's all the chest positions (useful for setting the tier)
     - /hungergames delete <game-id> | Delete's a game.
     - /hungergames addloot <this, all> | "this" is equal to your item in hand, "all" is all your inventory.
     - /hungergames maintenance <on, off> | If <on, off> is specified, it will toggle the maintenance mode, while active, only player's with permission "hungergames.admin" will be able to join.
     */

    private final Set<Command> sub = new HashSet<>();

    public HungerCommand() {
        sub.add(new JoinCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if(args.length == 0) return false;

        sub.forEach(sub -> {
            if(args[0].equalsIgnoreCase(sub.getName())) {

                if(sub.getPermission() != null && !sender.hasPermission(sub.getPermission())) {
                    sender.sendMessage(Common.colorize("&cI'm sorry, but you don't have permission to perform this action. If you believe this is an error you may contact server administrators."));
                    return;
                }

                List<String> list = new ArrayList<>(Arrays.asList(args));
                list.remove(0);

                sub.execute(sender, list.toArray(new String[0]));
            }
        });
        return false;
    }
}
