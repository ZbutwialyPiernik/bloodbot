package com.zbutwialypiernik.bloodbot.command;

import com.zbutwialypiernik.bloodbot.command.base.Command;
import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import com.zbutwialypiernik.bloodbot.exception.InvalidArgumentException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StopCommand extends Command {

    @Override
    public String getKeyword() {
        return Language.COMMAND_STOP_KEYWORD;
    }

    @Override
    public String getDescription() {
        return Language.COMMAND_STOP_DESCRIPTION;
    }

    @Override
    public String getUsage() {
        return Language.COMMAND_STOP_USAGE;
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("(?i)(KILLBOARD|BATTLEBOARD)");
    }

    @Override
    public void onInvoke(CommandContext context, Matcher args) {
        String type = args.group(1).toUpperCase();

        switch (type) {
            case "KILLBOARD":
                context.getDiscordUser().setKillboardWebhook(null);
                context.getUserRepository().save(context.getDiscordUser());

                CommandUtils.sendInfoMessage(context.getUser(),
                        Language.COMMAND_STOP_WEBHOOK_REMOVED);
                break;
            case "BATTLEBOARD":
                context.getDiscordUser().setBattleboardWebhook(null);
                context.getUserRepository().save(context.getDiscordUser());

                CommandUtils.sendInfoMessage(context.getUser(),
                        Language.COMMAND_STOP_WEBHOOK_REMOVED);
                break;
        }

        context.getUserRepository().save(context.getDiscordUser());
    }

}
