package com.zbutwialypiernik.bloodbot.entity.albion;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionGuild {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("AllianceId")
    private String allianceId;

    @SerializedName("AllianceTag")
    private String allianceTag;

    @SerializedName("AllianceName")
    private String allianceName;

    @SerializedName("FounderName")
    private String founderName;

    @SerializedName("FounderId")
    private String founderId;

    @SerializedName("Founded")
    private Instant foundedTime;

    @SerializedName("killFame")
    private long killFame;

    @SerializedName("DeathFame")
    private long deathFame;

    @SerializedName("AttacksWon")
    private long attacksWon;

    @SerializedName("DefensesWon")
    private long defensesWon;

    @SerializedName("MemberCount")
    private long memberCount;

}
