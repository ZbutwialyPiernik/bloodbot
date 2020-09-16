package com.zbutwialypiernik.bloodbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "killboard_filter")
public class KillboardFilter extends WebhookFilter {

    @Column(name = "display_image")
    private boolean displayImage = true;

    @Column(name = "display_kills")
    private boolean displayKills = true;

    @Column(name = "display_deaths ")
    private boolean displayDeaths = true;
}
