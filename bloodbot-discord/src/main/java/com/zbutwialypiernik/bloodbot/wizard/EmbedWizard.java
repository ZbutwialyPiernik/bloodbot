package com.zbutwialypiernik.bloodbot.wizard;

import com.zbutwialypiernik.bloodbot.util.Emotes;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class EmbedWizard {

    private final List<EmbedWizardStep> steps = new ArrayList<>();

    private int currentStep = 0;

    protected final User user;

    private boolean sent = false;

    private boolean completed = false;

    private boolean canceled = false;

    private Instant expireDate;

    private static final long EXPIRE_TIME = 180;

    protected EmbedWizard(User user) {
        this.user = user;
    }

    protected final void send() {
        if (sent) {
            throw new IllegalArgumentException("Message is already sent");
        }

        getCurrentStep().send(user);
        expireDate = Instant.now().plus(EXPIRE_TIME, ChronoUnit.SECONDS);
        sent=true;
    }

    protected abstract void onCancel();

    protected abstract void onComplete();

    protected void onReactionAdd(String reaction) {
        if (reaction.equals(Emotes.NO_ENTRY)) {
            canceled = true;
            onCancel();
        }
    }

    protected void onMessageReceived(Message message) {
        synchronized (this) {
            if (steps.size() > currentStep) {
                EmbedWizardStep step = getCurrentStep();
                step.dispatch(message.getContentRaw());

                if (step.isCompleted() && steps.size() > currentStep) {
                    nextStep();
                    EmbedWizardStep newStep = getCurrentStep();
                    newStep.beforeStep();

                    if (!newStep.isCompleted()) {
                        newStep.send(user);
                    } else {
                        nextStep();
                    }
                }
            }

            if (currentStep == steps.size()) {
                completed = true;
                onComplete();
            }
        }
    }

    protected void nextStep() {
        currentStep++;
        expireDate = Instant.now().plus(EXPIRE_TIME, ChronoUnit.SECONDS);
    }

    protected void addStep(EmbedWizardStep step) {
        steps.add(step);
    }

    public List<EmbedWizardStep> getSteps() {
        return steps;
    }

    public boolean isMarkedToDelete() {
        return canceled || completed || isExpired();
    }

    protected EmbedWizardStep getCurrentStep() {
        return steps.get(currentStep);
    }

    private boolean isExpired() {
        return expireDate != null && Instant.now().isAfter(expireDate);
    }

}
