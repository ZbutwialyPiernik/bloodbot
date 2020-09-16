package com.zbutwialypiernik.bloodbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.zbutwialypiernik.bloodbot.api.AlbionApi;
import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.config.ConfigLoader;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import com.zbutwialypiernik.bloodbot.repository.HibernateUtil;
import com.zbutwialypiernik.bloodbot.util.Constants;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import okhttp3.OkHttpClient;
import org.hibernate.Session;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws LoginException, InterruptedException, SQLException {
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

        AlbionRepository albionRepository = new AlbionRepository(api);

        JDA jda = new JDABuilder()
                .setToken(config.getDiscord().getToken())
                .build();

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            DiscordClient client = new DiscordClient(config.getDiscord(), albionRepository, new DiscordUserRepository(session.getEntityManagerFactory().createEntityManager()), jda);

            client.init();
        }
    }

}
