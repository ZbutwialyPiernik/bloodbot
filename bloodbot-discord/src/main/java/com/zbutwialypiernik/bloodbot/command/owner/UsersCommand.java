package com.zbutwialypiernik.bloodbot.command.owner;

import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.base.OwnerCommand;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersCommand extends OwnerCommand {

    @Override
    public String getKeyword() {
        return "guilds";
    }

    @Override
    public String getDescription() {
        return "Pokazuje statystyki i listę użytkowników";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("");
    }

    @Override
    public void onInvoke(CommandContext context, Matcher args) {
        Long premiumUsers = context.getUserRepository().getPremiumUserCount();
        Long users = context.getUserRepository().getUserCount();

        EmbedBuilder statisticsBuilder = new EmbedBuilder();
        statisticsBuilder.addField("Ilość użytkowników", users.toString(), true);
        statisticsBuilder.addField("Ilość użytkowników premium", premiumUsers.toString(), true);

        context.sendPrivateMessage(statisticsBuilder.build());
    }

}
