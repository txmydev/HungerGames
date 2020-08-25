package txmy.dev.network.receiver;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.json.simple.JSONObject;
import txmy.dev.HungerGames;

public class BungeePacketReceiver extends AbstractPacketReceiver implements PluginMessageListener {

    private final HungerGames plugin;

    public BungeePacketReceiver(HungerGames plugin){
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

    }

    @Override
    public void receive(JSONObject jsonObject) {

    }
}
