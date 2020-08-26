package txmy.dev.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import txmy.dev.game.Game;

@Getter
public class GameLeaveEvent extends BaseEvent{

    private Player player;
    private Game game;

    public GameLeaveEvent(Player player, Game game) {
        this.player = player;
        this.game = game;
    }
}
