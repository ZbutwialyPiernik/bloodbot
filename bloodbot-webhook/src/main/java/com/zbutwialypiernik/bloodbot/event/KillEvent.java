package com.zbutwialypiernik.bloodbot.event;

import com.zbutwialypiernik.bloodbot.entity.albion.AlbionKill;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class KillEvent {

    private final AlbionKill kill;

    public KillEvent(AlbionKill kill) {
        this.kill = kill;
    }

}
