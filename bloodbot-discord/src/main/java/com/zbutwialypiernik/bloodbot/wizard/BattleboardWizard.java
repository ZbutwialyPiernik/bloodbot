package com.zbutwialypiernik.bloodbot.wizard;

import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;
import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.wizard.step.IntegerParser;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class BattleboardWizard extends WebhookWizard {

    public BattleboardWizard(User user, DiscordUser discordUser, DiscordUserRepository userRepository, AlbionRepository albionRepository) {
        super(user, discordUser, userRepository, albionRepository, new BattleboardFilter());

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Lowest number of players",
                "Enter the lowest number of players needed, in range of 4 to 100",
                new IntegerParser()) {
            @Override
            protected void onValid(Integer input) {
                if (input > 100) {
                    CommandUtils.createErrorMessage("Provided value is too big");
                    return;
                } else if (input < 4) {
                    CommandUtils.createErrorMessage("Provided value is too small");
                    return;
                }

                this.markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You typed it wrong name! try again.");
            }
        });
    }

    @Override
    protected void onComplete() {

    }
}
