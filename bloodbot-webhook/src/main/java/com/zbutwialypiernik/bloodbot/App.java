package com.zbutwialypiernik.bloodbot;

import club.minnced.discord.webhook.WebhookCluster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.zbutwialypiernik.bloodbot.api.AlbionApi;
import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.config.ConfigLoader;
import com.zbutwialypiernik.bloodbot.entity.BattleboardFilter;
import com.zbutwialypiernik.bloodbot.entity.KillboardFilter;
import com.zbutwialypiernik.bloodbot.repository.*;
import com.zbutwialypiernik.bloodbot.repository.webhook.BattleboardFilterRepository;
import com.zbutwialypiernik.bloodbot.repository.webhook.KillboardFilterRepository;
import com.zbutwialypiernik.bloodbot.service.ScheduledService;
import com.zbutwialypiernik.bloodbot.service.battleboard.BattleboardNotifier;
import com.zbutwialypiernik.bloodbot.service.battleboard.BattleboardService;
import com.zbutwialypiernik.bloodbot.service.killboard.KillImageGenerator;
import com.zbutwialypiernik.bloodbot.service.killboard.KillboardNotifier;
import com.zbutwialypiernik.bloodbot.service.killboard.KillboardService;
import com.zbutwialypiernik.bloodbot.util.Constants;
import okhttp3.OkHttpClient;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) -> Instant.parse(json.getAsJsonPrimitive().getAsString())).create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.API_HOSTNAME)
                .build();


        AlbionApi api = retrofit.create(AlbionApi.class);

        ConfigLoader configLoader = new ConfigLoader(gson);

        Config config = configLoader.load();

        WebhookCluster cluster = new WebhookCluster(5); // create an initial 5 slots (dynamic like lists)
        cluster.setDefaultHttpClient(okHttpClient);
        cluster.setDefaultDaemon(true);

        AlbionRepository albionRepository = new AlbionRepository(api);

        Configuration configuration = new Configuration()
                .configure()
                .addAnnotatedClass(KillboardFilter.class)
                .addAnnotatedClass(BattleboardFilter.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        List<ScheduledService> service = new ArrayList<>();


        service.add(new KillboardService(config.getKillServiceConfig(), albionRepository)
                .addListener(new KillboardNotifier(new KillboardFilterRepository(sessionFactory.openSession().getEntityManagerFactory().createEntityManager()),
                            new KillImageGenerator(config.getImageGeneratorConfig(),new DiscIconRepository(api)))));

        service.add(new BattleboardService(config.getBattleServiceConfig(), albionRepository)
                .addListener(new BattleboardNotifier(new BattleboardFilterRepository(sessionFactory.openSession().getEntityManagerFactory().createEntityManager()))));

        service.forEach(ScheduledService::start);
    }

}
