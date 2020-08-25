package txmy.dev.profile;

import lombok.Getter;
import lombok.Setter;
import txmy.dev.game.Game;

import java.util.UUID;

@Getter @Setter
public class Profile {

    private final UUID id;
    private String name;

    private Game game;
    private State state = State.LOBBY;

    private boolean alive;

    private int wins, kills, deaths, killStreak, killStreakRecord, winStreak;

    public Profile(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isLobby() {
        return state == State.LOBBY;
    }

    public boolean isPlaying() {
        return state == State.IN_GAME;
    }


    public enum State {
        LOBBY, IN_GAME;
    }
}
