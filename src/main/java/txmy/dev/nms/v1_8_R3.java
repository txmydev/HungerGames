package txmy.dev.nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;
import txmy.dev.utils.Common;

import java.util.HashMap;
import java.util.Map;

public class v1_8_R3 implements NMS {

    private final Map<Player, Integer> ids = new HashMap<>();

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

    @Override
    public void mount(Player player) {
        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) player.getWorld()).getHandle();
        EntityBat bat = new EntityBat(world);

        PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity(0, ((CraftPlayer) player).getHandle(), bat);
        sendPacket(player, attach);

        ids.put(player, bat.getId());
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void unMount(Player player) {
        if(!ids.containsKey(player)) return;

        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(ids.get(player));
        sendPacket(player, destroy);
    }
}
