package com.zbutwialypiernik.bloodbot.event;

import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MembershipEvent {

    private final AlbionPlayer player;

    private final AlbionGuild guild;

    private final ActionType actionType;

    public MembershipEvent(AlbionPlayer player, AlbionGuild guild, String notificationChannel, ActionType actionType, Language language) {
        this.player = player;
        this.guild = guild;
        this.actionType = actionType;
    }

    public enum ActionType {
        LEFT, JOINED
    }

}
