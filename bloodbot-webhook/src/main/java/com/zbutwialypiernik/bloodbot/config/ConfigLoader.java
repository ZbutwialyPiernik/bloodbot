package com.zbutwialypiernik.bloodbot.config;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

@Log4j2
public class ConfigLoader {

    private static final String CONFIG_PATH = "config.json";

    private final Gson gson;

    public ConfigLoader(Gson gson) {
        this.gson = gson;
    }

    public Config load() {
        Path path = Paths.get(CONFIG_PATH);

        try {
            Config config = gson.fromJson(new String(Files.readAllBytes(path)), Config.class);

            validate(config);

            return config;
        } catch (NoSuchFileException e) {
            createTemplateConfig();

            log.warn("Config file not found, created template. Terminating process");

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean validate(Config config) {
        Validator validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();

        Set<ConstraintViolation<Config>> violations  = validator.validate(config);

        for (ConstraintViolation<Config> violation : violations) {
            log.error(violation.getMessage());
        }

        if (!violations.isEmpty()) {
            return false;
        }

        return true;
    }

    private void createTemplateConfig() {
        try {
            Files.write(Paths.get(CONFIG_PATH), Collections.singleton(gson.toJson(new Config())));
        } catch (IOException e) {
            log.error(e);
        }
    }

}
