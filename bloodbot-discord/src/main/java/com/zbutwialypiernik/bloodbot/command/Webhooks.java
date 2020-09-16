package com.zbutwialypiernik.bloodbot.command;

import com.zbutwialypiernik.bloodbot.command.base.Command;
import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;
import com.zbutwialypiernik.bloodbot.entity.KillboardFilter;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zbutwialypiernik.bloodbot.command.util.MessageFormatting.NEW_LINE;

public class Webhooks extends Command {

    @Override
    public String getKeyword() {
        return Language.COMMAND_WEBHOOKS_KEYWORD;
    }

    @Override
    public String getDescription() {
        return Language.COMMAND_WEBHOOKS_DESCRIPTION;
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
        EmbedBuilder builder = new EmbedBuilder();

        if (context.getDiscordUser().isEmpty()) {
            CommandUtils.sendInfoMessage(context.getUser(), "You don't have any webhooks");
            return;
        }

        builder.setTitle(Language.INFORMATION);
        builder.appendDescription(Language.COMMAND_WEBHOOKS_LIST_OF);
        builder.setColor(Color.GREEN);

        if (context.getDiscordUser().getKillboardWebhook() != null) {
            KillboardFilter webhook = context.getDiscordUser().getKillboardWebhook();

            String stringBuilder =
                    "Webhook: " + webhook.getWebhook() + NEW_LINE +
                    "Type: " + webhook.getFilterType() + NEW_LINE +
                    "Name: " + webhook.getObjectName() + NEW_LINE +
                    "Minimum Fame: " + webhook.getMinFame() + NEW_LINE +
                    "Language: " + webhook.getLanguage() + NEW_LINE;

            builder.addField("Killboard", stringBuilder, false);
        }

        if (context.getDiscordUser().getBattleboardWebhook() != null) {
            BattleboardFilter webhook = context.getDiscordUser().getBattleboardWebhook();

            String stringBuilder =
                    "Webhook: " + webhook.getWebhook() + NEW_LINE +
                    "Type: " + webhook.getFilterType() + NEW_LINE +
                    "Name: " + webhook.getObjectName() + NEW_LINE +
                    "Minimum Fame: " + webhook.getMinFame() + NEW_LINE +
                    "Minimum Players: " + webhook.getMinPlayers() + NEW_LINE +
                    "Language: " + webhook.getLanguage() + NEW_LINE;

            builder.addField("Battleboard", stringBuilder, false);
        }

        context.sendPrivateMessage(builder.build());
    }

}
