package txmy.dev.sign;

import org.bukkit.block.Sign;
import txmy.dev.game.Game;
import txmy.dev.metadata.HungerMetadataValue;

public class HungerSign {

    private Game game;
    private Sign sign;

    public HungerSign(Game game, Sign sign) {
        this.game = game;
        this.sign = sign;

        sign.setMetadata("game-id", new HungerMetadataValue(game.getId()));
    }

    public void update() {

    }

}
