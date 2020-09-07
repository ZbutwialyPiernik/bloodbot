package entity.albion;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.HashMap;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionBattle {

    @SerializedName("id")
    private long id;

    @SerializedName("startTime")
    private Instant startTime;

    @SerializedName("endTime")
    private Instant endTime;

    @SerializedName("timeout")
    private Instant timeout;

    @SerializedName("totalFame")
    private long totalFame;

    @SerializedName("totalKills")
    private long totalKills;

    @SerializedName("clusterName")
    private String clusterName;

    @SerializedName("players")
    private HashMap<String, Player> players;

    @SerializedName("guilds")
    private HashMap<String, Guild> guilds;

    @SerializedName("alliances")
    private HashMap<String, Alliance> alliances;

    @Value
    public static class Player {

        @SerializedName("id")
        String id;
        @SerializedName("name")
        String name;
        @SerializedName("kills")
        long kills;
        @SerializedName("deaths")
        long deaths;
        @SerializedName("killFame")
        long killFame;
        @SerializedName("guildName")
        String guildName;
        @SerializedName("guildId")
        String guildId;
        @SerializedName("allianceName")
        String allianceTag;
        @SerializedName("id")
        String allianceId;

    }

    @Value
    public static class Guild {

        @SerializedName("id")
        String id;
        @SerializedName("name")
        String name;
        @SerializedName("kills")
        long kills;
        @SerializedName("deaths")
        long deaths;
        @SerializedName("killFame")
        long killFame;
        @SerializedName("alliance")
        String allianceTag;
        @SerializedName("allianceId")
        String allianceId;
    }

    @Value
    public static class Alliance {

        @SerializedName("id")
        String id;
        @SerializedName("name")
        String tag;
        @SerializedName("kills")
        long kills;
        @SerializedName("deaths")
        long deaths;
        @SerializedName("killFame")
        long killFame;
    }

}
