package txmy.dev.adapter.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import txmy.dev.adapter.Adapter;
import txmy.dev.database.DatabaseConfiguration;

@Getter
public class MongoAdapter implements Adapter<MongoDatabase> {

    private MongoClient client;
    private MongoDatabase database;
    private final DatabaseConfiguration configuration;

    public MongoAdapter(DatabaseConfiguration config) {
        this.configuration = config;
    }

    public boolean establishConnection() {
        if (configuration == null) return false;

        try {
            String uri = "mongodb://" + (configuration.isAuth() ? configuration.getUsername() + ":" + configuration.getPassword() + "@" : "")
                    + configuration.getHost() + ":" + configuration.getPassword();

            MongoClientURI mongoURI = new MongoClientURI(uri);
            client = new MongoClient(mongoURI);

            database = client.getDatabase(configuration.getDatabase());
            return true;
        } catch (Exception ex) {
            client = null;
            return false;
        }
    }

    public boolean closeConnection() {
        if(!isConnected()) return false;

        try{
            client.close();
            return true;
        }catch(Exception ex) {
            return false;
        }
    }

    public boolean isConnected() {
        return client != null;
    }

    @Override
    public String getCaughtException() {
        return "alta paja hacerlo XD";
    }

    public MongoDatabase getConnection() {
        return database;
    }
}
