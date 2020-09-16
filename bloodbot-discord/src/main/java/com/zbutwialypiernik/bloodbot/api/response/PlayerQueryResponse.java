package com.zbutwialypiernik.bloodbot.api.response;

import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import lombok.Data;

import java.util.List;

@Data
public class PlayerQueryResponse {

    private List<AlbionPlayer> players;

}
