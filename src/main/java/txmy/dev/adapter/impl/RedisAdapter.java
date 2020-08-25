package txmy.dev.adapter.impl;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import txmy.dev.adapter.Adapter;
import txmy.dev.database.DatabaseConfiguration;

public class RedisAdapter implements Adapter<JedisPool> {

    private DatabaseConfiguration configuration;
    private JedisPool pool;
    private String caughtException;

    public RedisAdapter(DatabaseConfiguration config){
        this.configuration = config;
    }

    public boolean establishConnection() {
        if(configuration == null) return false;

        try{
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(5000);

            pool = new JedisPool(jedisPoolConfig, configuration.getHost(),
                    configuration.getPort(), 5000);

            if(configuration.isAuth()) pool.getResource().auth(configuration.getPassword());
            return true;
        }catch(Exception ex) {
            pool = null;
            caughtException = ex.getLocalizedMessage();
            return false;
        }
    }

    public boolean closeConnection() {
        if(!isConnected()) return false;

        pool.close();
        return true;
    }

    public boolean isConnected() {
        return pool != null;
    }

    public DatabaseConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String getCaughtException() {
        return caughtException;
    }

    public JedisPool getConnection() {
        return pool;
    }
}
