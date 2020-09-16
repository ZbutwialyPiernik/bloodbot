package com.zbutwialypiernik.bloodbot.api.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class AllianceResponse {

    @SerializedName("AllianceId")
    private String id;

    @SerializedName("AllianceName")
    private String name;

    @SerializedName("AllianceTag")
    private String tag;

    @SerializedName("Founded")
    private Timestamp founded;

    @SerializedName("NumPlayers")
    private Integer playerCount;

    @SerializedName("Guilds")
    private List<GuildIdentifier> guilds;

    @Data
    public static class GuildIdentifier {

        @SerializedName("Id")
        private String id;

        @SerializedName("Name")
        private String name;

    }

}
