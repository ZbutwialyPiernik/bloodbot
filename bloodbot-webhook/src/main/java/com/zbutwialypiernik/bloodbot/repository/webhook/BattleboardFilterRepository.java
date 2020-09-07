package com.zbutwialypiernik.bloodbot.repository.webhook;

import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;

import javax.persistence.EntityManager;
import java.util.List;

public class BattleboardFilterRepository extends WebhookFilterRepository<BattleboardFilter> {

    public BattleboardFilterRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<BattleboardFilter> getAll() {
        return entityManager.createQuery("from BattleboardFilter", BattleboardFilter.class).getResultList();
    }

}