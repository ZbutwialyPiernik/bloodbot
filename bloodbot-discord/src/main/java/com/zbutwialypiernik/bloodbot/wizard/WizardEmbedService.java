package com.zbutwialypiernik.bloodbot.wizard;

import com.zbutwialypiernik.bloodbot.service.ScheduledService;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Log4j2
public class WizardEmbedService extends ScheduledService implements EventListener {

    private ConcurrentHashMap<String, EmbedWizard> messages = new ConcurrentHashMap<>();

    public WizardEmbedService() {
        super(15, 0);
    }

    public void sendMessage(EmbedWizard wizard) {
        this.messages.put(wizard.user.getId(), wizard);
        wizard.send();

        log.debug("Started new wizard of type: " + wizard.getClass().getSimpleName() + " for user " +  wizard.user.getName() + "#" + wizard.user.getId());
    }

    private void onMessageReceived(PrivateMessageReceivedEvent event) {
        EmbedWizard message = messages.get(event.getAuthor().getId());

        if (message == null) {
            return;
        }

        if (!message.isMarkedToDelete()) {
            message.onMessageReceived(event.getMessage());
            log.debug(message.user.getName() + "#" + message.user.getId() + "sent message to wizard" + event.getMessage().getContentRaw());
        }

        if (message.isMarkedToDelete()) {
            messages.remove(event.getAuthor().getId());
            log.debug("Removed wizard of type: " + message.getClass().getSimpleName() + " for user " +  message.user.getName() + "#" + message.user.getId());
        }
    }

    private void onReactionAdded(PrivateMessageReactionAddEvent event) {
        EmbedWizard message = messages.get(event.getUser().getId());

        if (message == null) {
            return;
        }

        if (!message.isMarkedToDelete() &&
            message.getSteps().stream().anyMatch(step -> step.getMessage().getId().equals(event.getMessageId()))) {
            message.onReactionAdd(event.getReactionEmote().getName());
            log.debug(message.user.getName() + "#" + message.user.getId() + "added reaction to wizard" + event.getReaction());
        }

        if (message.isMarkedToDelete()) {
            messages.remove(event.getUser().getId());
            log.debug("Removed wizard of type: " + message.getClass().getSimpleName() + " for user " +  message.user.getName() + "#" + message.user.getId());
        }
    }

    @Override
    protected Runnable onTick() {
        return () -> messages.entrySet()
                .removeIf(pair -> {
                    if (pair.getValue().isMarkedToDelete()) {
                        log.debug("Removed expired wizard of type: " + pair.getValue().getClass().getSimpleName() + " for user " +  pair.getValue().user.getName() + "#" + pair.getValue().user.getId());
                        return true;
                    }

                    return false;
                });
    }

    public boolean isConfiguring(User user) {
        return messages.containsKey(user.getId());
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PrivateMessageReceivedEvent) {
            onMessageReceived((PrivateMessageReceivedEvent) event);
        } else if (event instanceof PrivateMessageReactionAddEvent) {
            onReactionAdded((PrivateMessageReactionAddEvent) event);
        }
    }

}