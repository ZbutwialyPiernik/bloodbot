package com.zbutwialypiernik.bloodbot.command.util;

import com.zbutwialypiernik.bloodbot.util.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class CommandUtils {

    public static void sendSimpleMessage(User user, String title, String message, Color color) {
        user.openPrivateChannel()
                .queue(channel -> channel.sendMessage(createSimpleMessage(title, message, color)).queue());
    }

    public static void sendSimpleMessage(User user, String title, String message) {
        sendSimpleMessage(user, title, message, new Color(24, 154, 211));
    }

    public static void sendErrorMessage(User user, String message) {
        sendSimpleMessage(user, "Error", message, Color.RED);
    }

    public static void sendInfoMessage(User user, String message) {
        sendSimpleMessage(user, "Info", message, Color.GREEN);
    }

    public static MessageEmbed createErrorMessage(String message) {
        return createSimpleMessage("Error", message, Color.RED);
    }

    public static MessageEmbed createInfoMessage(String message) {
        return createSimpleMessage("Info", message, Color.GREEN);
    }

    public static MessageEmbed createSimpleMessage(String title, String message, Color color) {
        EmbedBuilder builder = new EmbedBuilder();

        if (StringUtils.nullOrEmpty(title)) {
            throw new IllegalArgumentException("Title cannot be null");
        }

        builder.setTitle(title);
        builder.setColor(color);
        builder.setDescription(message);

        return builder.build();
    }

}
