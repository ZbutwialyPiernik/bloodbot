package entity;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookCluster;
import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.concurrent.Executors;

@Log4j2
@Converter
public class WebhookConverter implements AttributeConverter<WebhookClient, String> {

    private WebhookCluster webhookCluster = new WebhookCluster();

    public WebhookConverter() {
        webhookCluster.setDefaultExecutorService(Executors.newScheduledThreadPool(10));
    }

    @Override
    public String convertToDatabaseColumn(WebhookClient attribute) {
        return attribute.getUrl();
    }

    @Override
    public WebhookClient convertToEntityAttribute(String url) {
        return webhookCluster.buildWebhook(url);
    }

}
