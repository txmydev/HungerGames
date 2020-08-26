package txmy.dev.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import txmy.dev.game.Game;

@Getter
public class GamePreStartEvent extends BaseEvent {

    private Game game;

    public GamePreStartEvent(Game game) {
        this.game = game;
    }
}
