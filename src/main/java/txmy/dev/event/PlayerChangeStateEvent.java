package txmy.dev.event;

import lombok.Getter;
import txmy.dev.profile.Profile;

@Getter
public class PlayerChangeStateEvent extends BaseEvent {

    private Profile profile;
    private Profile.State oldState;

    public PlayerChangeStateEvent(Profile profile, Profile.State oldState) {
        this.profile = profile;
        this.oldState = oldState;
    }
}
