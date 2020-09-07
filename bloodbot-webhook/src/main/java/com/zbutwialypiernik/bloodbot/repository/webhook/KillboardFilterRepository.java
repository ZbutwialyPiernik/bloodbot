package com.zbutwialypiernik.bloodbot.repository.webhook;

import com.zbutwialypiernik.bloodbot.entity.KillboardFilter;

import javax.persistence.EntityManager;
import java.util.List;

public class KillboardFilterRepository extends WebhookFilterRepository<KillboardFilter> {

    public KillboardFilterRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<KillboardFilter> getAll() {
        return entityManager.createQuery("from KillboardFilter", KillboardFilter.class).getResultList();
    }

}