package com.zbutwialypiernik.bloodbot;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookCluster;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionBattle;
import com.zbutwialypiernik.bloodbot.event.BattleEvent;
import com.zbutwialypiernik.bloodbot.event.EventListener;
import com.zbutwialypiernik.bloodbot.repository.webhook.BattleboardFilterRepository;
import com.zbutwialypiernik.bloodbot.util.Constants;
import com.zbutwialypiernik.bloodbot.util.MessageFormatting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BattleboardNotifier implements EventListener<BattleEvent> {

    private static final String OVER_4_IMAGE = "https://cdn.discordapp.com/attachments/585931222266413097/623660152217665541/4.png";

    private static final String OVER_10_IMAGE = "https://cdn.discordapp.com/attachments/585931222266413097/623660155434958879/10.png";

    private static final String OVER_20_IMAGE = "https://cdn.discordapp.com/attachments/585931222266413097/623660157595025408/20.png";

    private static final String OVER_50_IMAGE = "https://cdn.discordapp.com/attachments/585931222266413097/623660158316314624/50.png";

    private static final String OVER_100_IMAGE = "https://cdn.discordapp.com/attachments/585931222266413097/623660162284126238/100.png";

    private static final int MAX_ROW = 5;

    private final BattleboardFilterRepository repository;

    public BattleboardNotifier(BattleboardFilterRepository repository) {
        this.repository = repository;
    }

    private boolean isObserved(BattleboardFilter filter, AlbionBattle.Guild guild) {
        switch (filter.getFilterType()) {
            case FilterType.GUILD:
                return filter.getObjectName().equals(guild.getName());
            case FilterType.ALLIANCE:
                return filter.getObjectName().equals(guild.getAllianceTag());
        }

        return false;
    }

    public void onMessage(BattleEvent event) {
        WebhookEmbed cachedMessage = null;

        for (BattleboardFilter filter : repository.getAll()) {
            if (event.getBattle().getPlayers().size() < filter.getMinPlayers() || event.getBattle().getTotalFame() < filter.getMinFame()) {
                continue;
            }

            if (event.getBattle().getGuilds()
                    .values()
                    .stream()
                    .anyMatch(guild -> isObserved(filter, guild))) {

                if (cachedMessage == null) {
                    cachedMessage = createBattleMessage(event.getBattle(), filter);
                }

                filter.getWebhook().send(cachedMessage);
            }
        }
    }

    private WebhookEmbed createBattleMessage(AlbionBattle battle, BattleboardFilter filter) {
        WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
        StringBuilder titleBuilder = new StringBuilder();

        if (battle.getAlliances().size() > 1) {
            battle.getAlliances().values()
                    .forEach(alliance -> titleBuilder.append(String.format("%s(%s) vs ",
                            alliance.getTag(),
                            battle.getPlayers().values()
                                    .stream()
                                    .filter(player -> player.getAllianceTag().equals(alliance.getTag()))
                                    .count())));
        } else {
            battle.getGuilds().values()
                    .forEach(guild -> titleBuilder.append(String.format("%s(%s) vs ",
                            guild.getName(),
                            battle.getPlayers().values()
                                    .stream()
                                    .filter(player -> player.getGuildName().equals(guild.getName()))
                                    .count())));
        }

        titleBuilder.delete(titleBuilder.length() - 4, titleBuilder.length() - 1);

        writeAlliances(battle, filter, embedBuilder);
        writeGuilds(battle, filter, embedBuilder);
        writePlayers(battle, filter, embedBuilder);

        embedBuilder.setTitle(new WebhookEmbed.EmbedTitle(titleBuilder.toString(), Constants.BATTLE_DETAILS_VIEW + battle.getId()))
                .setAuthor(new WebhookEmbed.EmbedAuthor("Blood Bot", null, null))
                .setDescription(String.format(filter.getLanguage().getTotalPlayers(), battle.getPlayers().size()))
                .setTimestamp(battle.getStartTime());


        if (battle.getPlayers().size() < 10) {
            embedBuilder.setThumbnailUrl(OVER_4_IMAGE);
        } else if (battle.getPlayers().size() < 20) {
            embedBuilder.setThumbnailUrl(OVER_10_IMAGE);
        } else if (battle.getPlayers().size() < 50) {
            embedBuilder.setThumbnailUrl(OVER_20_IMAGE);
        } else if (battle.getPlayers().size() < 100) {
            embedBuilder.setThumbnailUrl(OVER_50_IMAGE);
        } else {
            embedBuilder.setThumbnailUrl(OVER_100_IMAGE);
        }

        return embedBuilder.build();
    }

    private void writePlayers(AlbionBattle battle, BattleboardFilter filter, WebhookEmbedBuilder embedBuilder) {
        List<AlbionBattle.Player> sortedPlayers = battle.getPlayers().values()
                .stream()
                .sorted(Comparator.comparingLong(AlbionBattle.Player::getKills))
                .collect(Collectors.toList());

        Collections.reverse(sortedPlayers);

        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < MAX_ROW; row++) {
            if (row >= sortedPlayers.size()) {
                break;
            }

            AlbionBattle.Player player = sortedPlayers.get(row);

            stringBuilder.append(String.format("[%s](%s/%s) | %s: %d | %s: %s\n",
                    player.getName(),
                    Constants.PLAYER_DETAILS_VIEW, player.getId(),
                    filter.getLanguage().getKills(), player.getKills(),
                    filter.getLanguage().getDeaths(), player.getDeaths()));
        }

        if (stringBuilder.length() != 0) {
            WebhookEmbed.EmbedField field = new WebhookEmbed.EmbedField(false, filter.getLanguage().getBestPlayers(), stringBuilder.toString());
            embedBuilder.addField(field);
        }
    }

    private void writeAlliances(AlbionBattle battle, BattleboardFilter filter, WebhookEmbedBuilder embedBuilder) {
        List<AlbionBattle.Alliance> sortedAlliances = battle.getAlliances().values()
                .stream()
                .sorted(Comparator.comparingLong(AlbionBattle.Alliance::getKills))
                .collect(Collectors.toList());

        Collections.reverse(sortedAlliances);

        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < MAX_ROW; row++) {
            if (row == sortedAlliances.size()) {
                break;
            }

            AlbionBattle.Alliance alliance = sortedAlliances.get(row);

            stringBuilder.append(String.format("[%s](%s/%s) | %s: %d | %s: %s | %s: %s\n",
                    alliance.getTag(),
                    Constants.ALLIANCE_DETAILS_VIEW, alliance.getId(),
                    filter.getLanguage().getKills(), alliance.getKills(),
                    filter.getLanguage().getDeaths(), alliance.getDeaths(),
                    filter.getLanguage().getFame(), MessageFormatting.formatNumber(alliance.getKillFame())));
        }

        if (stringBuilder.length() != 0) {
            embedBuilder.addField(new WebhookEmbed.EmbedField(false, filter.getLanguage().getAlliances(), stringBuilder.toString()));
        }
    }

    private void writeGuilds(AlbionBattle battle, BattleboardFilter filter, WebhookEmbedBuilder embedBuilder) {
        List<AlbionBattle.Guild> sortedGuilds = battle.getGuilds().values()
                .stream()
                .sorted(Comparator.comparingLong(AlbionBattle.Guild::getKills))
                .collect(Collectors.toList());

        Collections.reverse(sortedGuilds);

        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < MAX_ROW; row++) {
            if (row >= sortedGuilds.size()) {
                break;
            }

            AlbionBattle.Guild guild = sortedGuilds.get(row);

            stringBuilder.append(String.format("[%s](%s/%s) | %s: %d | %s: %s\n",
                    guild.getName(),
                    Constants.GUILD_DETAILS_VIEW, guild.getId(),
                    filter.getLanguage().getKills(), guild.getKills(),
                    filter.getLanguage().getDeaths(), guild.getDeaths()));
        }

        if (stringBuilder.length() != 0) {
            embedBuilder.addField(new WebhookEmbed.EmbedField(false, filter.getLanguage().getBestGuilds(), stringBuilder.toString()));
        }
    }

}
