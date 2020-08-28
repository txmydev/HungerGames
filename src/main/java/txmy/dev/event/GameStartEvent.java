package txmy.dev.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import txmy.dev.game.Game;

@AllArgsConstructor @Getter
public class GameStartEvent extends BaseEvent {

    private final Game game;

}
