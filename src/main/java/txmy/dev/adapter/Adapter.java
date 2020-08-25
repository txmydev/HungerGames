package txmy.dev.adapter;

import txmy.dev.database.DatabaseConfiguration;

public interface Adapter<T> {

    boolean establishConnection();
    boolean closeConnection();

    boolean isConnected();
    DatabaseConfiguration getConfiguration();

    String getCaughtException();
    T getConnection();
}
