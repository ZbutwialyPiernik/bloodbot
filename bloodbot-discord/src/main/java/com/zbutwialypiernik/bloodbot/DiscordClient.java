package com.zbutwialypiernik.bloodbot;

import com.zbutwialypiernik.bloodbot.command.Webhooks;
import com.zbutwialypiernik.bloodbot.command.BattleboardCommand;
import com.zbutwialypiernik.bloodbot.command.KillboardCommand;
import com.zbutwialypiernik.bloodbot.command.StopCommand;
import com.zbutwialypiernik.bloodbot.command.owner.AddPremiumCommand;
import com.zbutwialypiernik.bloodbot.command.owner.UsersCommand;
import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.service.ScheduledService;
import com.zbutwialypiernik.bloodbot.util.Constants;
import com.zbutwialypiernik.bloodbot.wizard.WizardEmbedService;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.ResumedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DiscordClient extends ListenerAdapter {

    private final List<ScheduledService> services = new ArrayList<>();

    private final JDA jda;

    private final Config.DiscordConfig discordConfig;

    private final DiscordUserRepository userRepository;

    public DiscordClient(Config.DiscordConfig discordConfig, AlbionRepository albionRepository, DiscordUserRepository userRepository, JDA jda) throws InterruptedException {
        this.jda = jda;
        this.discordConfig = discordConfig;
        this.userRepository = userRepository;

        jda.awaitReady();

        WizardEmbedService messageService = new WizardEmbedService();
        CommandExecutor commandExecutor = new CommandExecutor(List.of(
                new Webhooks(),
                new KillboardCommand(albionRepository),
                new BattleboardCommand(albionRepository),
                new StopCommand(),
                new AddPremiumCommand(),
                new UsersCommand()), messageService, discordConfig);

        registerService(messageService);

        jda.addEventListener(this);
        jda.addEventListener(commandExecutor);
    }

    public void init() {
        jda.getPresence().setGame(Game.watching(discordConfig.getPrefix() + " | " + Constants.DISCORD_SERVER_INVITE));

        services.forEach(ScheduledService::start);
    }

    public void registerService(ScheduledService service) {
        services.add(service);

        if (service instanceof EventListener) {
            jda.addEventListener(service);
        }
    }


    @Override
    public void onResume(ResumedEvent event) {
        log.warn("Reconnected, starting services");
        services.forEach(ScheduledService::start);

        jda.getPresence().setGame(Game.watching(discordConfig.getPrefix() + " | " + Constants.DISCORD_SERVER_INVITE));
    }

    @Override
    public void onReconnect(ReconnectedEvent event) {
        log.warn("Reconnected, starting services");
        services.forEach(ScheduledService::start);

        jda.getPresence().setGame(Game.watching(discordConfig.getPrefix() + " | " + Constants.DISCORD_SERVER_INVITE));
    }

    @Override
    public void onDisconnect(DisconnectEvent event) {
        log.warn("Disconnected, stopping services");
        services.forEach(ScheduledService::stop);
    }

}
