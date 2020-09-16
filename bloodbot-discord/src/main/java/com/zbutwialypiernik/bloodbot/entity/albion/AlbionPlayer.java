package com.zbutwialypiernik.bloodbot.entity.albion;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionPlayer {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("GuildId")
    private String guildId;

    @SerializedName("GuildName")
    private String guildName;

    @SerializedName("AllianceId")
    private String allianceId;

    @SerializedName("AllianceName")
    private String allianceName;

    @SerializedName("KillFame")
    private Long killFame;

    @SerializedName("DeathFame")
    private Long deathFame;

    @SerializedName("FameRatio")
    private Float fameRatio;

    @SerializedName("Equipment")
    private AlbionEquipment equipment;

    @SerializedName("AverageItemPower")
    private Float itemPower;

}
