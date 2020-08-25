package txmy.dev.network;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import txmy.dev.HungerGames;
import txmy.dev.network.annotations.IncomingPacketListener;
import txmy.dev.network.listener.PacketListener;
import txmy.dev.network.packet.Packet;
import txmy.dev.network.sender.AbstractPacketSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkHandler {

    private static final String CHANNEL = "hunger-games";

    private AbstractPacketSender packetSender;
    private final List<Class<? extends Packet>> packets;
    private final Map<String, Map<PacketListener, Set<Method>>> handlers;

    public NetworkHandler() {
        packets = new CopyOnWriteArrayList<>();
        handlers = new HashMap<>();
    }

    public void registerPacket(Class<? extends Packet> clazz) {
        packets.add(clazz);
        handlers.put(clazz.getSimpleName(), Maps.newHashMap());

        Bukkit.getLogger().info("Registered packet " + clazz.getSimpleName() + ".class");
    }

    public void registerListener(PacketListener listener) {
        for (Method m : listener.getClass().getDeclaredMethods()) {
            if (!m.isAnnotationPresent(IncomingPacketListener.class)) return;

            for (Class<?> type : m.getParameterTypes()) {
                if (!packets.contains(type)) {
                    Bukkit.getLogger().info("Attempted to register a invalid listener at class " + listener.getClass().getSimpleName() + ", packet '" + type.getSimpleName() + " isn't found, either register it.");
                    return;
                }

                handlers.get(type.getSimpleName()).putIfAbsent(listener, new HashSet<>());
                handlers.get(type.getSimpleName()).get(listener).add(m);
            }
        }
    }

    @SneakyThrows
    public void call(Packet packet) {
        if (!handlers.containsKey(packet.getClass().getSimpleName()))
            throw new IllegalArgumentException("Packet " + packet.getClass().getSimpleName() + " isn't registered");

        handlers.get(packet.getClass().getSimpleName()).forEach((packetlistener, set) -> {
            set.forEach(method -> {
                try {
                    method.invoke(packetlistener, packet);
                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void attemptSend(Packet packet) {
        JSONObject jsonObject = packet.serialize();

        if (!jsonObject.containsKey("server-id")) jsonObject.put("server-id", HungerGames.getInstance().getServerName());
        if (!jsonObject.containsKey("packet-type")) jsonObject.put("packet-type", packet.getClass().getSimpleName());

        packetSender.send(jsonObject);
    }
}
