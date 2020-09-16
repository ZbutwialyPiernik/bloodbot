package com.zbutwialypiernik.bloodbot.repository;

import com.zbutwialypiernik.bloodbot.entity.DiscordUser;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DiscordUserRepository {

    private final EntityManager entityManager;

    public DiscordUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(DiscordUser user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void remove(String id) {
        try {
            entityManager.getTransaction().begin();
            DiscordUser config = entityManager.find(DiscordUser.class, id);
            entityManager.remove(config);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public DiscordUser getById(String id) {
        DiscordUser discordUser = entityManager.find(DiscordUser.class, id);

        if (discordUser == null) {
            discordUser = new DiscordUser(id);
        }

        return discordUser;
    }

    public List<DiscordUser> getPremiumUsers() {
        TypedQuery<DiscordUser> query = entityManager.createQuery("FROM DiscordGuild guild WHERE guild.premiumStartTime IS NOT NULL AND guild.premiumEndTime IS NOT NULL AND NOW() BETWEEN guild.premiumStartTime AND guild.premiumEndTime", DiscordUser.class);

        return query.getResultList();
    }

    public List<DiscordUser> getUsers() {
        TypedQuery<DiscordUser> query = entityManager.createQuery("FROM DiscordGuild", DiscordUser.class);

        return query.getResultList();
    }

    public Long getPremiumUserCount() {
        return (long) entityManager.createQuery("SELECT COUNT() FROM DiscordGuild guild WHERE guild.plan = PREMIUM").getSingleResult();
    }

    public Long getUserCount() {
        return (long) entityManager.createQuery("SELECT COUNT() FROM DiscordGuild").getSingleResult();
    }

}
