package txmy.dev.network.sender;

import org.json.simple.JSONObject;

public abstract class AbstractPacketSender {

    public abstract void send(JSONObject message);

}
