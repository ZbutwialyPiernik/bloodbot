package com.zbutwialypiernik.bloodbot.repository.language;

import com.google.gson.Gson;
import com.zbutwialypiernik.bloodbot.exception.FileException;
import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import com.zbutwialypiernik.bloodbot.util.Constants;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class JsonLanguageRepository implements LanguageRepository {

    private final String LANGUAGES_PATH = "translations\\";

    private final Gson gson;

    private final HashMap<String, Language> languages = new HashMap<>();

    public JsonLanguageRepository(Gson gson) {
        this.gson = gson;
        this.languages.put(Constants.DEFAULT_LANGUAGE, new Language());

        try {
            Files.write(Paths.get(LANGUAGES_PATH + Constants.DEFAULT_LANGUAGE + ".json"), gson.toJson(new Language()).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> walk = Files.walk(Paths.get(LANGUAGES_PATH))) {

            List<String> results = walk.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            for (String result : results) {
                if (result.endsWith(".json")) {
                    String languageCode = result.replace(LANGUAGES_PATH, "").replace(".json", "");

                    if (!languageCode.equals(Constants.DEFAULT_LANGUAGE)) {
                        loadLanguage(languageCode);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLanguage(String languageCode) {
        Path languagePath = Paths.get(LANGUAGES_PATH + languageCode + ".json");

        try {
            String file = new String(Files.readAllBytes(languagePath));

            Language language = gson.fromJson(file, Language.class);
            findNotTranslatedPhrases(language);
            languages.put(language.getIsoCode(), language);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("Could not load language: " + languageCode);
        }
    }

    public void findNotTranslatedPhrases(Language language) {
        Language defaultLanguage = languages.get(Constants.DEFAULT_LANGUAGE);
        for (Field field : language.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getType().isAssignableFrom(String.class)) {
                continue;
            }

            try {
                String newLanguageValue = (String) field.get(language);
                String defaultLanguageValue = (String) field.get(defaultLanguage);

                if (newLanguageValue == null || newLanguageValue.trim().isEmpty() || (newLanguageValue.equals(defaultLanguageValue) && !field.getName().contains("Keyword") )) {
                    log.warn("Translation for key: " + field.getName() + " is missing in language " + language.getIsoCode());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

    @Override
    public Language getLanguage(String languageCode) {
        return languages.get(languageCode);
    }

    @Override
    public Collection<Language> getAvailableLanguages() {
        return languages.values();
    }

}
