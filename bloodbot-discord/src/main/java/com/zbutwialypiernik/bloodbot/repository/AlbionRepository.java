package com.zbutwialypiernik.bloodbot.repository;

import com.zbutwialypiernik.bloodbot.api.AlbionApi;
import com.zbutwialypiernik.bloodbot.api.response.AllianceResponse;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionBattle;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionKill;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import com.zbutwialypiernik.bloodbot.exception.ApiException;
import com.zbutwialypiernik.bloodbot.exception.InvalidArgumentException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AlbionRepository {

    private final AlbionApi api;

    public AlbionRepository(AlbionApi api) {
        this.api = api;
    }

    public AlbionPlayer findPlayer(String query) {
        try {
            List<AlbionPlayer> players = api.searchPlayer(query).execute().body().getPlayers();

            return players.stream()
                    .filter(player -> player.getName().equalsIgnoreCase(query))
                    .findFirst().orElse(null);
        } catch (IOException e) {
            throw new ApiException(Language.EXCEPTION_API_PROBLEM);
        }
    }

    public AlbionGuild findGuild(String query) {
        try {
            List<AlbionGuild> guilds = api.searchGuild(query).execute().body().getGuilds();

            return guilds.stream()
                    .filter(guild -> guild.getName().equalsIgnoreCase(query))
                    .findFirst().orElse(null);
        } catch (IOException e) {
            throw new ApiException(Language.EXCEPTION_API_PROBLEM);
        }
    }

}
