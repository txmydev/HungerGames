package txmy.dev.utils.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public abstract class Command {

    private String name;
    private String permission;

    public Command(String name, String permission){
        this.name = name;
        this.permission = permission;
    }

    public Command(String name){
        this(name, "hungergames.user");
    }

    public abstract void execute(CommandSender sender, String[] args);
}
