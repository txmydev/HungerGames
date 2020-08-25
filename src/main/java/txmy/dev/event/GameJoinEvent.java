package txmy.dev.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import txmy.dev.game.Game;

@Getter
public class GameJoinEvent extends BaseEvent{

    private Player player;
    private Game game;

}
