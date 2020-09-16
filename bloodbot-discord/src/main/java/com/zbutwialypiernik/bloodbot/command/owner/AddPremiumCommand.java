package com.zbutwialypiernik.bloodbot.command.owner;

import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.base.OwnerCommand;
import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddPremiumCommand extends OwnerCommand {

    @Override
    public String getKeyword() {
        return "premium-add";
    }

    @Override
    public String getDescription() {
        return "Dodaje serwer na whiteliste na określony czas w dniach";
    }

    @Override
    public String getUsage() {
        return "[User ID] [Time in minutes]";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("(\\d{18})\\s(\\d+)");
    }

    @Override
    public void onInvoke(CommandContext context, Matcher args) {
        String userId = args.group(1);
        int days = Integer.parseInt(args.group(2));

        DiscordUser discordUser = context.getUserRepository().getById(userId);

        if (discordUser == null) {
            discordUser = new DiscordUser(userId);
        }

        discordUser.addPremiumTime(days);

        context.getUserRepository().save(discordUser);

        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("Id", discordUser.getId(), true)
                .addField("Premium Time", discordUser.getPremiumStartTime().toString(), true)
                .addField("Premium End Time", discordUser.getPremiumEndTime().toString(), true);
                //.setThumbnail(guild.getIconUrl() == null ? "https://icon-library.net/images/discord-icon-transparent/discord-icon-transparent-28.jpg" : guild.getIconUrl());

        context.sendPrivateMessage(builder.build());
    }

}
