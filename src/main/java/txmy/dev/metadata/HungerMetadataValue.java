package txmy.dev.metadata;

import org.bukkit.metadata.FixedMetadataValue;
import txmy.dev.HungerGames;

public class HungerMetadataValue extends FixedMetadataValue {

    public HungerMetadataValue(Object value) {
        super(HungerGames.getInstance(), value);
    }
}
