package txmy.dev.network.packet;

import org.json.simple.JSONObject;

public interface Packet {

    JSONObject serialize();
    Object deserialize();

}
