package com.zbutwialypiernik.bloodbot.event;

import com.zbutwialypiernik.bloodbot.entity.albion.AlbionBattle;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class BattleEvent {

    private final AlbionBattle battle;

    public BattleEvent(AlbionBattle battle) {
        this.battle = battle;
    }

}
