package com.zbutwialypiernik.bloodbot.command;

import com.zbutwialypiernik.bloodbot.command.base.Command;
import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpCommand extends Command {

    private static final int COMMANDS_PER_PAGE = 8;

    private List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String getKeyword() {
        return Language.COMMAND_HELP_KEYWORD;
    }

    @Override
    public String getDescription() {
        return Language.COMMAND_HELP_DESCRIPTION;
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
        builder.setTitle("List of commands");
        builder.setColor(Color.CYAN);

        for (Command command : commands) {
            if (command.isOwnerOnly()) {
                builder.addField(MessageFormatting.bold(command.getKeyword() + " " + command.getUsage()), command.getDescription(), false);
            } else {
                builder.addField(MessageFormatting.bold(command.getKeyword() + " " + command.getUsage()), command.getDescription(), false);
            }
        }

        context.sendPrivateMessage(builder.build());
    }

}
