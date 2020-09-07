package com.zbutwialypiernik.bloodbot.event;

public interface EventListener<T> {

    void onEvent(T event);

}
