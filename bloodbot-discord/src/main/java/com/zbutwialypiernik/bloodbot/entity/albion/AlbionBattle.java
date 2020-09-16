package com.zbutwialypiernik.bloodbot.entity.albion;

import lombok.*;

import java.time.Instant;
import java.util.HashMap;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionBattle {

    private long id;

    private Instant startTime;

    private Instant endTime;

    private Instant timeout;

    private long totalFame;

    private long totalKills;

    private String clusterName;

    private HashMap<String, AlbionBattle.Player> players;

    private HashMap<String, AlbionBattle.Guild> guilds;

    private HashMap<String, AlbionBattle.Alliance> alliances;

    @Value
    public static class Player {

        String id;
        String name;
        long kills;
        long deaths;
        long killFame;
        String guildName;
        String guildId;
        String allianceName;
        String allianceId;

    }

    @Value
    public static class Guild {

        String id;
        String name;
        long kills;
        long deaths;
        long killFame;
        String alliance;
        String allianceId;
    }

    @Value
    public static class Alliance {

        String id;
        String name;
        long kills;
        long deaths;
        long killFame;
    }

}
