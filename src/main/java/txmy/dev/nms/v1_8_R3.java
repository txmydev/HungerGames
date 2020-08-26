package txmy.dev.nms;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;
import txmy.dev.utils.Common;

public class v1_8_R3 implements NMS {
    @Override
    public void sendTitle(Player player, String title,String subTitle, int fadeIn, int stay, int fadeOut) {
        Title titleObj = new Title(Common.colorize(title), Common.colorize(subTitle), fadeIn, stay, fadeOut);
        player.sendTitle(titleObj);
    }

    @Override
    public void sendActionBar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("\"text\":\"" + Common.colorize(message) +"\"}"), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
