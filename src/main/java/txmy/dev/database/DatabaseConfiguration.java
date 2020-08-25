package txmy.dev.database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DatabaseConfiguration {

    private String username;
    private String password;
    private boolean auth;
    private String host;
    private int port;
    private String database;

}
