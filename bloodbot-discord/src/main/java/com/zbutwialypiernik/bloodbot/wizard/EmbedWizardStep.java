package com.zbutwialypiernik.bloodbot.wizard;

import com.zbutwialypiernik.bloodbot.exception.InvalidArgumentException;
import com.zbutwialypiernik.bloodbot.wizard.step.Parser;
import lombok.AccessLevel;
import lombok.Getter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

import static com.zbutwialypiernik.bloodbot.util.Emotes.NO_ENTRY;

public abstract class EmbedWizardStep<T> {

    private final String title;
    private final String description;
    @Getter(AccessLevel.PROTECTED)
    private Message message;
    @Getter(AccessLevel.PROTECTED)
    private boolean completed = false;
    private Parser<T> parser;

    public EmbedWizardStep(String title, String description, Parser<T> parser) {
        this.title = title;
        this.description = description;
        this.parser = parser;
    }

    public void dispatch(String input) {
        T t = parser.parse(input);

        if (t == null) {
            onInvalid();
        } else {
            onValid(t);
        }
    }

    public void send(User user) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setColor(Color.blue);
        builder.setFooter("React with" + NO_ENTRY + " if you want to cancel configuration.", null);

        message = user.openPrivateChannel().complete()
                .sendMessage(builder.build())
                .complete();

        message.addReaction(NO_ENTRY)
                .queue();
    }

    public void markAsCompleted() {
        if (completed) {
            throw new InvalidArgumentException("Step is already marked as completed");
        }

        completed = true;
    }

    protected void beforeStep() {

    }

    abstract protected void onValid(T value);

    abstract protected void onInvalid();

}
