package com.zbutwialypiernik.bloodbot.wizard;

import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.entity.KillboardFilter;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.wizard.step.YesOrNoBooleanParser;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class KillboardWizard extends WebhookWizard {

    public KillboardWizard(User user, DiscordUser discordUser, DiscordUserRepository userRepository, AlbionRepository albionRepository) {
        super(user, discordUser, userRepository, albionRepository, new KillboardFilter());

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Display Kills?",
                "Do you want to display kills, type Yes or No.",
                new YesOrNoBooleanParser()) {
            @Override
            protected void onValid(Boolean input) {
                getFilter().setDisplayKills(input);

                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You typed it wrong name! try again.");
            }
        });

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Display Deaths?",
                "Do you want to display deaths, type Yes or No.",
                new YesOrNoBooleanParser()) {

            @Override
            protected void beforeStep() {
                if (!getFilter().isDisplayKills()) {
                    CommandUtils.sendSimpleMessage(user, "Warning",
                            "You refused to display kills, has", Color.YELLOW);
                }

                markAsCompleted();
            }

            @Override
            protected void onValid(Boolean input) {
                getFilter().setDisplayDeaths(input);

                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You typed it wrong name! try again.");
            }
        });

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Display Image?",
                "Do you want to display kills, type Yes or No.",
                new YesOrNoBooleanParser()) {
            @Override
            protected void onValid(Boolean input) {
                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You typed it wrong name! try again.");
            }
        });
    }

    public KillboardFilter getFilter() {
        return (KillboardFilter) super.getFilter();
    }

    @Override
    protected void onComplete() {
        getDiscordUser().setKillboardWebhook(getFilter());
        getUserRepository().save(getDiscordUser());

        CommandUtils.sendSimpleMessage(user, "Successful configuration!", "Your webhook has been configured, enjoy!", Color.GREEN);
    }

}
