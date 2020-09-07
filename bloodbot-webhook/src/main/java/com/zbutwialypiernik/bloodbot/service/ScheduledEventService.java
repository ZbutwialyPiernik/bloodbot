package com.zbutwialypiernik.bloodbot.service;

import com.zbutwialypiernik.bloodbot.event.EventListener;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public abstract class ScheduledEventService<T> extends ScheduledService {

    protected List<EventListener<T>> listeners = new ArrayList<>();

    public ScheduledEventService(long intervalDelay, long initialDelay) {
        super(intervalDelay, initialDelay);
    }

    public ScheduledEventService<T> addListener(EventListener<T> listener) {
        listeners.add(listener);

        return this;
    }

    public ScheduledEventService<T> removeListener(EventListener<T> listener) {
        listeners.remove(listener);

        return this;
    }

    protected void notifyListeners(T event) {
        listeners.forEach(listener -> {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                log.error("Unhandled exception in listener", e);
            }
        });
    }

    protected void notifyListeners(List<T> events) {
        for (T event : events) {
            listeners.forEach(listener -> {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    log.error("Unhandled exception in listener", e);
                }
            });
        }
    }

}
