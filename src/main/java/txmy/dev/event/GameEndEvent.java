package txmy.dev.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import txmy.dev.game.Game;

@AllArgsConstructor @Getter
public class GameEndEvent extends BaseEvent {

    private final Game game;
    private final EndReason reason;

    public enum EndReason {
        TIME_UP, SERVER_CLOSED, PLAYER_WIN;
    }
}
