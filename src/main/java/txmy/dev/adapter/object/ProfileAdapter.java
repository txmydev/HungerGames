package txmy.dev.adapter.object;

import org.bson.Document;
import txmy.dev.profile.Profile;

import java.util.UUID;

public class ProfileAdapter {

    public static Document serialize(Profile profile) {
        return new Document("uuid", profile.getId().toString())
                .append("name", profile.getName())
                .append("wins", profile.getWins())
                .append("kills", profile.getKills())
                .append("deaths", profile.getDeaths())
                .append("killStreak", profile.getKillStreakRecord())
                .append("winStreak", profile.getWinStreak());
    }

    public static Profile deserialize(Document document) {
        UUID id = UUID.fromString(document.getString("uuid"));
        String name = document.getString("name");

        return deserializeExisting(document, new Profile(id, name));
    }

    public static Profile deserializeExisting(Document document, Profile profile) {
        int wins = document.getInteger("wins");
        int kills = document.getInteger("kills");
        int deaths = document.getInteger("deaths");
        int killStreak = document.getInteger("killStreak");
        int winStreak = document.getInteger("winStreak");


        profile.setWins(wins);
        profile.setKills(kills);
        profile.setDeaths(deaths);
        profile.setKillStreakRecord(killStreak);
        profile.setWinStreak(winStreak);

        return profile;
    }

    public static void load(Document document, Profile profile){
        deserializeExisting(document, profile);
    }


}
