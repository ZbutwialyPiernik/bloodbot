package com.zbutwialypiernik.bloodbot.repository;

import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;
import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.entity.KillboardFilter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private volatile static SessionFactory INSTANCE = null;

    public static SessionFactory getSessionFactory() {
        if (INSTANCE == null) {
            createSessionFactory();
        }
        return INSTANCE;
    }

    private synchronized static void createSessionFactory() {
        if (INSTANCE != null) {
            return;
        }

        INSTANCE = new Configuration()
                .configure()
                .addAnnotatedClass(KillboardFilter.class)
                .addAnnotatedClass(BattleboardFilter.class)
                .addAnnotatedClass(DiscordUser.class)
                .buildSessionFactory();
    }

}
