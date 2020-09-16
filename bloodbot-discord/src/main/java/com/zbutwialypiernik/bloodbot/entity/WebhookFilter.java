package com.zbutwialypiernik.bloodbot.entity;

import com.zbutwialypiernik.bloodbot.entity.translation.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class WebhookFilter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "webhook")
    private String webhook;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "object_id", length = 32)
    private String objectId;

    @Column(name = "object_name", length = 32)
    private String objectName;

    @Enumerated(EnumType.STRING)
    @Column(name = "filter_type")
    private FilterType filterType;

    @Column(name = "min_fame", length = 7)
    private int minFame = 0;

    public WebhookFilter(String objectId, String objectName, String webhook, FilterType filterType) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.filterType = filterType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum FilterType {
        ALLIANCE, GUILD
    }

    public enum Language {
        PL, EN
    }

}
