package com.zbutwialypiernik.bloodbot.command;

import com.zbutwialypiernik.bloodbot.command.base.Command;
import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionGuild;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionPlayer;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import com.zbutwialypiernik.bloodbot.exception.ExceededLimitException;
import com.zbutwialypiernik.bloodbot.exception.InvalidArgumentException;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.wizard.BattleboardWizard;
import com.zbutwialypiernik.bloodbot.wizard.KillboardWizard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zbutwialypiernik.bloodbot.entity.BattleboardFilter.FilterType;

public class BattleboardCommand extends Command {

    private final AlbionRepository albionRepository;

    public BattleboardCommand(AlbionRepository albionRepository) {
        this.albionRepository = albionRepository;
    }

    @Override
    public String getKeyword() {
        return Language.COMMAND_BATTLEBOARD_KEYWORD;
    }

    @Override
    public String getDescription() {
        return Language.COMMAND_BATTLEBOARD_DESCRIPTION;
    }

    @Override
    public String getUsage() {
        return Language.COMMAND_BATTLEBOARD_USAGE;
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("");
    }

    @Override
    public void onInvoke(CommandContext context, Matcher args) {
        if (context.getDiscordUser().getKillboardWebhook() != null) {
            throw new InvalidArgumentException("Killboard webhook already exists!");
        }

        context.sendPrivateMessage(new BattleboardWizard(context.getUser(), context.getDiscordUser(), context.getUserRepository(), albionRepository));
    }

}
