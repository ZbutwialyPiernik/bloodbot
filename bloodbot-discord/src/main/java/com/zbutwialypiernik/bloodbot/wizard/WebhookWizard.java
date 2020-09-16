package com.zbutwialypiernik.bloodbot.wizard;

import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.entity.WebhookFilter;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.util.Regex;
import com.zbutwialypiernik.bloodbot.util.StringUtils;
import com.zbutwialypiernik.bloodbot.wizard.step.IntegerParser;
import com.zbutwialypiernik.bloodbot.wizard.step.RegexStringParser;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.HttpException;
import net.dv8tion.jda.webhook.WebhookClient;
import net.dv8tion.jda.webhook.WebhookClientBuilder;

import java.awt.*;
import java.util.concurrent.ExecutionException;

@Log4j2
public abstract class WebhookWizard extends EmbedWizard {

    @Getter
    private final DiscordUserRepository userRepository;

    @Getter
    private final AlbionRepository albionRepository;

    @Getter
    private final DiscordUser discordUser;

    @Getter
    private final WebhookFilter filter;

    protected WebhookWizard(User user, DiscordUser discordUser, DiscordUserRepository userRepository, AlbionRepository albionRepository, WebhookFilter filter) {
        super(user);
        this.discordUser = discordUser;
        this.userRepository = userRepository;
        this.albionRepository = albionRepository;
        this.filter = filter;

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Let's get started!","Paste here webhook you want to use. If you don't know what webhook is, then read this. https://support.discordapp.com/hc/en-us/articles/228383668-Intro-to-Webhooks",
                new RegexStringParser(Regex.WEBHOOK_REGEX)) {

            @Override
            protected void onValid(String input) {
                try (WebhookClient webhookClient = new WebhookClientBuilder(input).build()){
                    MessageEmbed embed = new EmbedBuilder()
                            .setAuthor("Blood Bot")
                            .setThumbnail("https://cdn.discordapp.com/attachments/617107111544881273/658091323613511733/80902290_1287686648091751_6586926080400031744_n.jpg")
                            .setTitle("Howdy")
                            .setDescription("This is an awesome webhook test!")
                            .setColor(Color.GREEN)
                            .build();

                        webhookClient.send(embed).get();
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof HttpException) {
                        CommandUtils.sendErrorMessage(user, "Invalid webhook.");
                    } else {
                        log.error(e);
                        CommandUtils.sendErrorMessage(user, "Unknown error.");
                    }

                    return;
                } catch (Exception e) {
                    log.error(e);
                    CommandUtils.sendErrorMessage(user, "Unknown error.");
                    return;
                }

                filter.setWebhook(input);

                CommandUtils.sendSimpleMessage(user,
                        "Completed",
                        "Sent test message to webhook.",
                        Color.GREEN);

                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "Enter valid webhook!");
            }
        });

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Choose type",
                "Decide if you want to watch Guild or Alliance. Reminder: Alliance is only for premium users",
                new RegexStringParser("(?i)(ALLIANCE|GUILD)")) {

            @Override
            protected void onValid(String input) {
                filter.setFilterType(WebhookFilter.FilterType.valueOf(input.toUpperCase()));

                this.markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You typed it wrong! Try again.");
            }
        });

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Enter Name",
                "Enter the name of object you want to observe, if you chosen guild, enter the name of guild. In case of alliance, provide name of any member of alliance.",
                new RegexStringParser(Regex.ALBION_NAME_REGEX)) {

            @Override
            protected void onValid(String input) {
                switch (filter.getFilterType()) {
                    case GUILD:
                        AlbionGuild guild = albionRepository.findGuild(input);

                        if (guild == null) {
                            CommandUtils.sendErrorMessage(user, ("Guild " + MessageFormatting.bold(input) + " not found"));
                            return;
                        }

                        filter.setObjectId(guild.getAllianceId());
                        filter.setObjectName(guild.getName());

                        break;
                    case ALLIANCE:
                        AlbionPlayer player = albionRepository.findPlayer(input);

                        if (player == null) {
                            CommandUtils.sendErrorMessage(user, ("Player " + MessageFormatting.bold(input) + " not found"));
                            return;
                        }

                        if (StringUtils.nullOrEmpty(player.getAllianceId())) {
                            CommandUtils.sendErrorMessage(user, ("Player " + MessageFormatting.bold(input) + " is not a member of alliance."));
                            return;
                        }

                        filter.setObjectId(player.getAllianceId());
                        filter.setObjectName(player.getAllianceName());

                        break;
                }

                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You provided wrong name! try again.");
            }
        });

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Select Language",
                "Enter the code of language you want to use, available languages: PL, EN.",
                new RegexStringParser("(?i)(PL|EN)")) {
            @Override
            protected void onValid(String input) {
                WebhookFilter.Language.valueOf(input.toUpperCase());

                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You provided wrong name! try again.");
            }
        });

        addStep(new EmbedWizardStep<>(getSteps().size() + 1 + ". Minimum fame",
                "Enter minimum fame needed to display kills, in range of 1 to 1.000.000.",
                new IntegerParser()) {
            @Override
            protected void onValid(Integer input) {
                if (input > 1000000) {
                    CommandUtils.createErrorMessage("Provided value is too big");
                    return;
                } else if (input < 0) {
                    CommandUtils.createErrorMessage("Provided value is negative");
                    return;
                }

                markAsCompleted();
            }

            @Override
            protected void onInvalid() {
                CommandUtils.sendErrorMessage(user, "You typed it wrong name! try again.");
            }
        });
    }

    @Override
    protected void onCancel() {
        user.openPrivateChannel()
                .queue(channel -> channel.sendMessage(CommandUtils.createSimpleMessage("Canceled", "Configuration has been canceled.", Color.ORANGE)).queue());
    }

}
