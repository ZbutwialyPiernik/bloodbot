package com.zbutwialypiernik.bloodbot.api;

import com.zbutwialypiernik.bloodbot.api.response.AllianceResponse;
import com.zbutwialypiernik.bloodbot.api.response.GuildQueryResponse;
import com.zbutwialypiernik.bloodbot.api.response.PlayerQueryResponse;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionBattle;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionKill;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface AlbionApi {

    @GET("search")
    Call<PlayerQueryResponse> searchPlayer(@Query("q") String query);

    @GET("search")
    Call<GuildQueryResponse> searchGuild(@Query("q") String query);

    @GET("events")
    Call<List<AlbionKill>> getKills(@Query("page") int page, @Query("limit") int limit);

    @GET("battles?sort=recent")
    Call<List<AlbionBattle>> getRecentBattles(@Query("offset") int offset, @Query("limit") int limit);

    @GET("players/{id}")
    Call<AlbionPlayer> getPlayer(@Path("id") String id);

    @GET("guilds/{id}")
    Call<AlbionGuild> getGuild(@Path("id") String id);

    @GET("alliances/{id}")
    Call<AllianceResponse> getAlliance(@Path("id") String id);

    @GET("alliances/{id}/members")
    Call<List<AlbionPlayer>> getGuildMembers(@Path("id") String id);

    @GET("items/{id}")
    Call<ResponseBody> getItemIcon(@Path("id") String id, @Query("size") int size);

}
