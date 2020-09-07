package com.zbutwialypiernik.bloodbot.repository;

import com.zbutwialypiernik.bloodbot.api.AlbionApi;
import com.zbutwialypiernik.bloodbot.service.killboard.KillImageGenerator;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public class DiscIconRepository implements IconRepository {

    private static final String ICON_CACHE_PATH = "cache/icons";

    private final AlbionApi albionApi;

    public DiscIconRepository(AlbionApi albionApi) {
        this.albionApi = albionApi;

        try {
            Path cachePath = Paths.get(ICON_CACHE_PATH);

            if (!Files.exists(cachePath)) {
                Files.createDirectories(cachePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getItemIcon(String id, int size) {
        try {
            Path iconPath = Paths.get(String.format("%s/%s.%s", ICON_CACHE_PATH, id, KillImageGenerator.OUTPUT_EXTENSION));

            if (Files.exists(iconPath)) {
                return Files.readAllBytes(iconPath);
            }

            log.debug(String.format("Caching new image %s", id));

            byte[] icon = albionApi.getItemIcon(id, size).execute().body().bytes();

            Files.write(iconPath, icon);

            return icon;
        } catch (IOException e) {
            throw new RuntimeException("Problem while downloading image");
        }
    }

}
