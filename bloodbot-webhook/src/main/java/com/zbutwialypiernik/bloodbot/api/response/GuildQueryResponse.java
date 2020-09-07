package com.zbutwialypiernik.bloodbot.api.response;

import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import lombok.Data;

import java.util.List;

@Data
public class GuildQueryResponse {

    private List<AlbionGuild> guilds;

}
