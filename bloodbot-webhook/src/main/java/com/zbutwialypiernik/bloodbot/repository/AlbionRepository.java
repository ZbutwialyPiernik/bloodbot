package com.zbutwialypiernik.bloodbot.repository;

import com.zbutwialypiernik.bloodbot.api.AlbionApi;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionBattle;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionKill;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import com.zbutwialypiernik.bloodbot.exception.ApiException;

import java.io.IOException;
import java.util.List;

public class AlbionRepository {

    private final AlbionApi api;

    public AlbionRepository(AlbionApi api) {
        this.api = api;
    }

    public List<AlbionBattle> getRecentBattles(int page, int limit) {
        try {
            return api.getRecentBattles(page * limit, limit).execute().body();
        } catch (IOException e) {
            throw new ApiException("API Problem");
        }
    }

    public List<AlbionKill> getRecentKills(int page, int limit) {
        try {
            return api.getKills(page, limit).execute().body();
        } catch (IOException e) {
            throw new ApiException("API Problem");
        }
    }

    public List<AlbionPlayer> getGuildMembersById(String guildId) {
        try {
            return api.getGuildMembers(guildId).execute().body();
        } catch (IOException e) {
            throw new ApiException("API Problem");
        }
    }

    /*
    public List<AlbionGuild> getGuildsByAllianceId(String id) {
        try {
            return api.getAlliance(id).execute().body().getGuilds();
        } catch (IOException e) {
            throw new ApiException("API Problem");
        }
    }

    public AlbionGuild getGuildById(String id) {

    }*/

}
