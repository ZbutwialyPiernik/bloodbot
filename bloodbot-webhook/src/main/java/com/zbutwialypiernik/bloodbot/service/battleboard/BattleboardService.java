package com.zbutwialypiernik.bloodbot.service.battleboard;

import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionBattle;
import com.zbutwialypiernik.bloodbot.event.BattleEvent;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.webhook.KillboardFilterRepository;
import com.zbutwialypiernik.bloodbot.service.ScheduledEventService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.List;
import java.util.Queue;

@Log4j2
public class BattleboardService extends ScheduledEventService<BattleEvent> {

    private final Config.BattleServiceConfig config;

    private final KillboardFilterRepository repository;

    public BattleboardService(Config.BattleServiceConfig config, KillboardFilterRepository repository) {
        super(config.getIntervalDelay(), config.getInitDelay());
        this.config = config;
        this.repository = repository;
    }

    @Override
    protected Runnable onTick() {
        return () -> {
            repository.
        };
    }

}
