package txmy.dev.network.receiver;

import org.json.simple.JSONObject;

public abstract class AbstractPacketReceiver {

    public abstract void receive(JSONObject jsonObject);

}
