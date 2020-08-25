package txmy.dev.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;
import txmy.dev.HungerGames;
import txmy.dev.adapter.object.ProfileAdapter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class ProfileHandler {

    private final HungerGames plugin;
    private final ConcurrentMap<UUID, Profile> profiles;

    public ProfileHandler(HungerGames plugin) {
        this.plugin = plugin;
        this.profiles = new ConcurrentHashMap<>();
    }

    public void load(UUID id, String name) {
        profiles.putIfAbsent(id, new Profile(id, name));

        MongoCollection<Document> collection = plugin.getDatabase().getConnection().getCollection("hungergames_stats");
        Document document = collection.find(Filters.eq("uuid", id.toString())).first();

        Profile profile = profiles.get(id);

        if(document != null) {
            ProfileAdapter.load(document, profile);
            if(!profile.getName().equals(name)) profile.setName(name);
        }
    }

    public void save(Profile profile) {
        Document document = ProfileAdapter.serialize(profile);
        MongoCollection<Document> collection = plugin.getDatabase().getConnection().getCollection("hungergames_stats");

        if(collection.find(Filters.eq("uuid", profile.getId().toString())).first() != null) collection.replaceOne(Filters.eq("uuid", profile.getId().toString()), document);
        else collection.insertOne(document);
    }

    public Profile getProfile(UUID id) {
        return profiles.get(id);
    }

    public void remove(UUID id) {
        profiles.remove(id);
    }

}
