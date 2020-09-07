package com.zbutwialypiernik.bloodbot.repository.webhook;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class WebhookFilterRepository<T> {

    protected final EntityManager entityManager;

    protected WebhookFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract List<T> getAll();

}
