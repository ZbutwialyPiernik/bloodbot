package com.zbutwialypiernik.bloodbot.service.killboard;

import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionKill;
import com.zbutwialypiernik.bloodbot.event.KillEvent;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.service.ScheduledEventService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.List;
import java.util.Queue;

@Log4j2
public class KillboardService extends ScheduledEventService<KillEvent> {

    private final AlbionRepository repository;

    private final Queue<Long> displayedKills;

    private final Config.KillServiceConfig config;

    public KillboardService(Config.KillServiceConfig config, AlbionRepository repository) {
        super(config.getIntervalDelay(), config.getInitDelay());
        this.config = config;
        this.repository = repository;

        this.displayedKills = new CircularFifoQueue<>(config.getPageScanCount() * config.getPageSize() * 2);
    }

    @Override
    public Runnable onTick() {
        return () -> {
            try {
                log.debug("Refreshing Killboard");

                for (int page = 0; page < config.getPageScanCount(); page++) {
                    List<AlbionKill> kills = repository.getRecentKills(page, config.getPageSize());

                    if (kills == null) {
                        break;
                    }

                    for (AlbionKill kill : kills) {
                        if (!displayedKills.contains(kill.getId())) {
                            displayedKills.add(kill.getId());

                            notifyListeners(new KillEvent(kill));                        }
                    }

                    try {
                        Thread.sleep((long) config.getPageIntervalDelay() * 1000);
                    } catch (InterruptedException e) {
                        log.error(e);
                    }
                }

                log.debug("Refreshing Killboard Finished");
            } catch (Exception e) {
                log.error("Unhandled exception", e);
            }
        };
    }

}
