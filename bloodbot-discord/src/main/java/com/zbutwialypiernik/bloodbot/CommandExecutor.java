package com.zbutwialypiernik.bloodbot;

import com.zbutwialypiernik.bloodbot.command.HelpCommand;
import com.zbutwialypiernik.bloodbot.command.base.Command;
import com.zbutwialypiernik.bloodbot.command.base.CommandContext;
import com.zbutwialypiernik.bloodbot.command.util.CommandUtils;
import com.zbutwialypiernik.bloodbot.command.util.MessageFormatting;
import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import com.zbutwialypiernik.bloodbot.exception.AlbionBotException;
import com.zbutwialypiernik.bloodbot.exception.InvalidArgumentException;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.repository.HibernateUtil;
import com.zbutwialypiernik.bloodbot.wizard.WizardEmbedService;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.hibernate.Session;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class CommandExecutor extends ListenerAdapter {

    private final List<Command> commands;

    private final HelpCommand helpCommand;

    private final WizardEmbedService messageService;

    private final Executor executor = Executors.newFixedThreadPool(16);

    private final Pattern defaultPrefix;

    public CommandExecutor(List<Command> commands, WizardEmbedService messageService, Config.DiscordConfig discordConfig) {
        this.commands = commands;
        this.messageService = messageService;
        this.helpCommand = new HelpCommand(commands);
        this.defaultPrefix = Pattern.compile(createSplitRegex(discordConfig.getPrefix()));
    }

    private void handleMessage(Message message, DiscordUserRepository userRepository) {
        DiscordUser discordUser = userRepository.getById(message.getAuthor().getId());

        Matcher matcher = defaultPrefix.matcher(message.getContentRaw());

        if (!matcher.matches()) {
            return;
        }

        log.info(String.format("[%s]%s invoked command: %s",
                message.getAuthor().getId(),
                message.getAuthor().getName(),
                message.getContentRaw()));

        String commandName = matcher.group(1).trim();
        String commandArgs = matcher.group(2).trim();

        CommandContext context = new CommandContext(
                message.getAuthor(),
                discordUser,
                userRepository,
                messageService);

        if (commandName.isEmpty() || commandName.equalsIgnoreCase("help")) {
            helpCommand.onInvoke(context, helpCommand.getPattern().matcher(""));
            return;
        }

        try {
            Command commandFound = commands.stream()
                    .filter(command -> command.getKeyword().equalsIgnoreCase(commandName))
                    .findFirst()
                    .orElseThrow(() -> new InvalidArgumentException(Language.EXCEPTION_UNKNOWN_COMMAND));

            Matcher argsMatcher = commandFound.getPattern().matcher(commandArgs);

            if (!argsMatcher.matches()) {
                throw new InvalidArgumentException(Language.EXCEPTION_WRONG_ARGUMENTS +
                        MessageFormatting.NEW_LINE + commandFound.getUsage());
            }

            commandFound.onInvoke(context, argsMatcher);
        } catch (AlbionBotException e) {
            CommandUtils.sendErrorMessage(message.getAuthor(), e.getMessage());
        } catch (Exception e) {
            log.warn(String.format("[%s]%s error while invoking command: %s",
                    message.getAuthor().getId(),
                    message.getAuthor().getName(),
                    message.getContentRaw()));

            e.printStackTrace();

            CommandUtils.sendErrorMessage(message.getAuthor(), Language.EXCEPTION_UNKNOWN_ERROR);
        }
    }

    String createSplitRegex(String prefix) {
        return "^\\s*" + prefix + "\\s*([\\w-]*)\\s*([\\w\\s]*)$";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        executor.execute(() -> {
            if (messageService.isConfiguring(event.getAuthor())) {
                return;
            }

            if (ChannelType.PRIVATE == event.getChannelType()) {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    DiscordUserRepository discordUserRepository = new DiscordUserRepository(session.getEntityManagerFactory().createEntityManager());

                    handleMessage(event.getMessage(), discordUserRepository);
                }
            }
        });
    }

}
