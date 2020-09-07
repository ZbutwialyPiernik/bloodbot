package com.zbutwialypiernik.bloodbot.config;

import com.zbutwialypiernik.bloodbot.service.killboard.KillImageGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@ToString
public class Config {

    private DiscordConfig discordConfig = new DiscordConfig();

    private BattleServiceConfig battleServiceConfig = new BattleServiceConfig();

    private KillServiceConfig killServiceConfig = new KillServiceConfig();

    private KillboardImageGeneratorConfig imageGeneratorConfig = new KillboardImageGeneratorConfig();

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiscordConfig {

        @Size(min = 5, max = 32, message = "Prefix length should be between 5-32")
        private String name;

        private String iconUrl;

        private String siteUrl;

    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BattleServiceConfig {

        @Min(0)
        private long initDelay = 30;

        @Min(1)
        private long intervalDelay = 90;

        @Min(20)
        @Max(50)
        private int pageSize = 50;

        @Min(1)
        @Max(10)
        private int pageScanCount = 2;

        @Min(1)
        @Max(10)
        private float pageIntervalDelay = 1;

    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KillServiceConfig {

        @Min(0)
        private long initDelay = 0;

        @Min(1)
        private long intervalDelay = 45;

        @Min(1)
        @Max(10)
        private int pageScanCount = 4;

        @Min(20)
        @Max(50)
        private int pageSize = 50;

        @Min(1)
        @Max(10)
        private float pageIntervalDelay = 1;

    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KillboardImageGeneratorConfig {

        private int iconSize = 120;

        private final KillImageGenerator.Vector2 bagPosition = new KillImageGenerator.Vector2(30, 30);

        private final KillImageGenerator.Vector2 headPosition = new KillImageGenerator.Vector2(180, 35);

        private final KillImageGenerator.Vector2 capePosition = new KillImageGenerator.Vector2(330, 30);

        private final KillImageGenerator.Vector2 mainHandPosition = new KillImageGenerator.Vector2(55, 155);

        private final KillImageGenerator.Vector2 armorPosition = new KillImageGenerator.Vector2(180, 155);

        private final KillImageGenerator.Vector2 offhandPosition = new KillImageGenerator.Vector2(305, 155);

        private final KillImageGenerator.Vector2 foodPosition = new KillImageGenerator.Vector2(30,280);

        private final KillImageGenerator.Vector2 shoesPosition = new KillImageGenerator.Vector2(180,265);

        private final KillImageGenerator.Vector2 potionPosition = new KillImageGenerator.Vector2(330,280);

        private final KillImageGenerator.Vector2 mountPosition = new KillImageGenerator.Vector2(180,380);

        private String backgroundPath = "assets/background.png";

    }

}
