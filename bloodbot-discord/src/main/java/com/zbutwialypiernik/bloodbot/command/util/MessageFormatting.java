package com.zbutwialypiernik.bloodbot.command.util;

import java.text.NumberFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;

public class MessageFormatting {

    public static String NEW_LINE = "\n";

    public static String bold(String text) {
        return "**" + text + "**";
    }

    public static String italic(String text) {
        return "*" + text + "*";
    }

    public static String underline(String text) {
        return "__" + text + "__";
    }

    public static String strike(String text) {
        return "~~" + text + "~~";
    }

    public static String codeblock(String text) {
        return "```" + text + "```";
    }

    public static String green(String text) {
        return "```css \n" + text + "\n```";
    }

    public static String red(String text) {
        return "```excel \n" + text + "\n```";
    }

    public static String quote(String text) {
        return ">" + text;
    }

    public static String spoiler(String text) {
        return "||" + text + "||";
    }

    public static String removeDecimal(float number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);

        return numberFormat.format(number);
    }

    public static String embedLink(String link, String text) {
        return "[" + text + "](" + link +  ")";
    }

    public static String embedLink(String link, String text, String hoverText) {
        return "[" + text + "](" + link + hoverText + " )";
    }

    public static String formatDate(Temporal date) {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneOffset.UTC).format(date);
    }

    public static String formatNumber(long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

}
