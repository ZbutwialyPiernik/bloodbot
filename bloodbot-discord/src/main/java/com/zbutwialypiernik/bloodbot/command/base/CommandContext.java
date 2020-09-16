package com.zbutwialypiernik.bloodbot.command.base;

import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.wizard.EmbedWizard;
import com.zbutwialypiernik.bloodbot.wizard.WizardEmbedService;
import lombok.Getter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

@Getter
public class CommandContext {

    private final User user;

    private final DiscordUser discordUser;

    private final DiscordUserRepository userRepository;

    private final WizardEmbedService wizardService;

    public CommandContext(User user, DiscordUser discordUser, DiscordUserRepository userRepository, WizardEmbedService wizardService) {
        this.user = user;
        this.discordUser = discordUser;
        this.userRepository = userRepository;
        this.wizardService = wizardService;
    }


    public void sendPrivateMessage(MessageEmbed message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }

    public void sendPrivateMessage(EmbedWizard wizard) {
        wizardService.sendMessage(wizard);
    }

    public JDA getJda() {
        return user.getJDA();
    }

}
