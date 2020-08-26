package txmy.dev.nms;

import org.bukkit.entity.Player;

public interface NMS {

    void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut);

    void sendActionBar(Player player, String message);

}
