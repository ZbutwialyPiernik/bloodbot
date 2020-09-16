package com.zbutwialypiernik.bloodbot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "discord_user")
public class DiscordUser {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "premium_start_time")
    private LocalDateTime premiumStartTime;

    @Column(name = "premium_end_time")
    private LocalDateTime premiumEndTime;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private KillboardFilter killboardWebhook;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BattleboardFilter battleboardWebhook;

    public DiscordUser(String id) {
        this.id = id;
    }

    public void addPremiumTime(long days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Parameter days cannot be zero or negative");
        }

        if (hasPremium()) {
            premiumEndTime = premiumEndTime.plus(days, ChronoUnit.DAYS);
        } else {
            premiumStartTime = LocalDateTime.now();
            premiumEndTime = premiumStartTime.plus(days, ChronoUnit.DAYS);
        }
    }

    public boolean hasPremium() {
        if (premiumStartTime == null || premiumEndTime == null) {
            return false;
        }

        return premiumStartTime.isBefore(premiumEndTime);
    }

    public boolean isEmpty() {
        return getKillboardWebhook() == null &&
                getBattleboardWebhook() == null;
    }

}
