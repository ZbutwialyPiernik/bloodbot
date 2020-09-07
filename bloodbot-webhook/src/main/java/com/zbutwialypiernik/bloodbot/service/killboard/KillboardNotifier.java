package com.zbutwialypiernik.bloodbot.service.killboard;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.zbutwialypiernik.bloodbot.StringUtils;
import com.zbutwialypiernik.bloodbot.entity.KillboardFilter;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionKill;
import com.zbutwialypiernik.bloodbot.event.EventListener;
import com.zbutwialypiernik.bloodbot.event.KillEvent;
import com.zbutwialypiernik.bloodbot.repository.webhook.KillboardFilterRepository;
import com.zbutwialypiernik.bloodbot.util.Constants;
import com.zbutwialypiernik.bloodbot.util.Emotes;
import com.zbutwialypiernik.bloodbot.util.MessageFormatting;

import java.awt.*;

public class KillboardNotifier implements EventListener<KillEvent> {

    private final KillImageGenerator killImageGenerator;

    public KillboardNotifier(KillboardFilterRepository repository, KillImageGenerator killImageGenerator) {
        this.killImageGenerator = killImageGenerator;
        this.repository = repository;
    }

    public boolean isKillerObserved(KillboardFilter filter, AlbionKill event) {
        if (event.getVictim().getDeathFame() < filter.getMinFame()) {
            return false;
        }

        switch (filter.getFilterType()) {
            case ALLIANCE:
                return filter.getObjectId().equals(event.getKiller().getAllianceId());
            case GUILD:
                return filter.getObjectId().equals(event.getKiller().getGuildId());

        }

        return false;
    }

    public boolean isVictimObserved(KillboardFilter filter, AlbionKill event) {
        if (event.getVictim().getDeathFame() < filter.getMinFame()) {
            return false;
        }

        switch (filter.getFilterType()) {
            case ALLIANCE:
                return filter.getObjectId().equals(event.getVictim().getAllianceId());
            case GUILD:
                return filter.getObjectId().equals(event.getVictim().getGuildId());

        }

        return false;
    }

    @Override
    public void onEvent(KillEvent event) {
        byte[] cachedKillImage = null;
        byte[] cachedDeathImage = null;
        WebhookEmbed cachedKillMessage = null;
        WebhookEmbed cachedDeathMessage = null;

        for (KillboardFilter filter : repository.getAll()) {
            if (filter.isDisplayKills() && isKillerObserved(filter, event.getKill())) {
                if (cachedKillMessage == null) {
                     cachedKillImage = killImageGenerator.generate(event.getKill().getKiller().getEquipment(), event.getKill().getVictim().getEquipment());
                     cachedKillMessage = createKillMessage(filter, event.getKill(), true);
                }

                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .addEmbeds(cachedKillMessage);

                if (filter.isDisplayImage()) {
                    builder.addFile(event.getKill().getId() + "." + KillImageGenerator.OUTPUT_EXTENSION, cachedKillImage);
                }

                System.out.println(filter);
                System.out.println(cachedKillImage);
                System.out.println(builder.getFileAmount());
                System.out.println(cachedKillMessage.getImageUrl());

                filter.getWebhook().send(builder.build());
            } else if (filter.isDisplayDeaths() && isVictimObserved(filter, event.getKill())) {
                if (cachedDeathMessage == null) {
                    cachedDeathImage = killImageGenerator.generate(event.getKill().getKiller().getEquipment(), event.getKill().getVictim().getEquipment());
                    cachedDeathMessage = createKillMessage(filter, event.getKill(), false);
                }

                WebhookMessageBuilder builder =new WebhookMessageBuilder()
                        .addEmbeds(cachedDeathMessage);

                if (filter.isDisplayImage()) {
                    builder.addFile(event.getKill().getId() + "." + KillImageGenerator.OUTPUT_EXTENSION, cachedDeathImage);
                }

                filter.getWebhook().send(builder.build());
            }
        }
    }

    private WebhookEmbed createKillMessage(KillboardFilter filter, AlbionKill kill, boolean isKiller) {
        WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
        embedBuilder.setAuthor(new WebhookEmbed.EmbedAuthor("Name", null, null));

        if (isKiller) {
            embedBuilder.setColor(Color.GREEN.getRGB());
        } else {
            embedBuilder.setColor(Color.RED.getRGB());
        }

        if (kill.getNumberOfParticipants() > 1) {
            embedBuilder.setDescription(String.format(filter.getLanguage().getAssistedBy(), kill.getNumberOfParticipants() - 1));
        } else {
            embedBuilder.setDescription(filter.getLanguage().getSoloKill());
        }

        String killerName = kill.getKiller().getName();
        String victimName = kill.getVictim().getName();

        if (!StringUtils.nullOrEmpty(kill.getKiller().getAllianceName())) {
            killerName = String.format("[%s] %s", kill.getKiller().getAllianceName(), killerName);
        }

        if (!StringUtils.nullOrEmpty(kill.getVictim().getAllianceName())) {
            victimName = String.format("[%s] %s", kill.getVictim().getAllianceName(), victimName);
        }

        embedBuilder
                .setTitle(new WebhookEmbed.EmbedTitle(
                        String.format("%s %s %s", killerName, filter.getLanguage().getKilled(), victimName),
                        Constants.KILL_DETAILS_VIEW + "/" + kill.getId()))
                .addField(new WebhookEmbed.EmbedField(true, filter.getLanguage().getTotalFame(), kill.getVictim().getDeathFame().toString()))
                .addField(new WebhookEmbed.EmbedField(true, filter.getLanguage().getKillerFame(), kill.getKiller().getKillFame().toString()))
                .addField(new WebhookEmbed.EmbedField(true, "\u200b", "\u200b"))
                .addField(new WebhookEmbed.EmbedField(true, filter.getLanguage().getKillerGuild(), kill.getKiller().getGuildName().isEmpty() ? Emotes.X : kill.getKiller().getGuildName()))
                .addField(new WebhookEmbed.EmbedField(true, filter.getLanguage().getVictimGuild(), kill.getVictim().getGuildName().isEmpty() ? Emotes.X : kill.getVictim().getGuildName()))
                .addField(new WebhookEmbed.EmbedField(true, "\n\u200b", "\u200b"))
                .addField(new WebhookEmbed.EmbedField(true, filter.getLanguage().getKillerItemPower(), MessageFormatting.removeDecimal(kill.getKiller().getItemPower())))
                .addField(new WebhookEmbed.EmbedField(true, filter.getLanguage().getVictimItemPower(), MessageFormatting.removeDecimal(kill.getVictim().getItemPower())))
                .addField(new WebhookEmbed.EmbedField(true, "\n\n\u200b", "\u200b"))
                .setTimestamp(kill.getTimestamp());

        if (filter.isDisplayImage()) {
            embedBuilder.setImageUrl(String.format("attachment://%s.%s", kill.getId(), KillImageGenerator.OUTPUT_EXTENSION));
        }

        return embedBuilder.build();
    }

}
