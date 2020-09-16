package com.zbutwialypiernik.bloodbot.repository.language;

import com.zbutwialypiernik.bloodbot.entity.translation.Language;

import java.util.Collection;

public interface LanguageRepository {

    Language getLanguage(String languageCode);

    Collection<Language> getAvailableLanguages();

}