package com.zbutwialypiernik.bloodbot.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ScheduledService {

    protected ScheduledExecutorService executor;

    protected final long intervalDelay;

    protected final long initialDelay;

    protected ScheduledService(long intervalDelay, long initialDelay) {
        this.intervalDelay = intervalDelay;
        this.initialDelay = initialDelay;
    }

    protected abstract Runnable onTick();

    public void start() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(onTick(), initialDelay, intervalDelay, TimeUnit.SECONDS);
    }

    public void stop() {
        executor.shutdown();
    }

}
