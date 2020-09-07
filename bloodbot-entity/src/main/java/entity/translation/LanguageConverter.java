package entity.translation;

import com.google.gson.GsonBuilder;
import com.zbutwialypiernik.bloodbot.repository.language.JsonLanguageRepository;
import com.zbutwialypiernik.bloodbot.repository.language.LanguageRepository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LanguageConverter implements AttributeConverter<Language, String> {

    private final LanguageRepository languageRepository = new JsonLanguageRepository(new GsonBuilder().create());

    @Override
    public String convertToDatabaseColumn(Language attribute) {
        return attribute.getIsoCode();
    }

    @Override
    public Language convertToEntityAttribute(String databaseData) {
        return languageRepository.getLanguage(databaseData);
    }

}
