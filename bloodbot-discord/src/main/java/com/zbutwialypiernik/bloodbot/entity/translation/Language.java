package com.zbutwialypiernik.bloodbot.entity.translation;

import lombok.Getter;

@Getter
public class Language {

    /* COMMANDS */

    // *** HELP ***

    public static final String COMMAND_HELP_KEYWORD = "help";
    public static final String COMMAND_HELP_DESCRIPTION = "Shows list of available commands";

    // *** KILLBOARD ***

    public static final String COMMAND_KILLBOARD_KEYWORD = "killboard";
    public static final String COMMAND_KILLBOARD_DESCRIPTION = "Sends a notification when a player, guild member or alliance member, participates in kill";
    public static final String COMMAND_KILLBOARD_USAGE = "";

    // *** BATTLEBOARD ***

    public static final String COMMAND_BATTLEBOARD_KEYWORD = "battleboard";
    public static final String COMMAND_BATTLEBOARD_DESCRIPTION = "Sends a notification when a guild member or alliance member, participates in battle";
    public static final String COMMAND_BATTLEBOARD_USAGE = "";

    // *** STOP ***

    public static final String COMMAND_STOP_KEYWORD = "stop";
    public static final String COMMAND_STOP_DESCRIPTION = "Removes webhook";
    public static final String COMMAND_STOP_USAGE = "[Killboard | Battleboard]";

    public static final String COMMAND_STOP_WEBHOOK_REMOVED = "Webhook successfully removed.";

    // *** WEBHOOKS ***

    public static final String COMMAND_WEBHOOKS_KEYWORD = "webhooks";
    public static final String COMMAND_WEBHOOKS_DESCRIPTION = "Shows list of webhooks in current channel";
    public static final String COMMAND_WEBHOOKS_NO_WEBHOOKS = "This channel has no webhooks";
    public static final String COMMAND_WEBHOOKS_LIST_OF = "List of your webhooks";

    // KILLBOARD WEBHOOK

    public static final String SHOW_KILLS = "Show kills";
    public static final String SHOW_DEATHS = "Show deaths";
    public static final String MINIMUM_FAME = "Minimum Fame";

    public static final String ENTER_MINIMUM_FAME = "Enter minimum amount of fame, maximum limit is 1,000,000";
    public static final String ERROR_HIGHER_THAN_LIMIT = "Parameter minimum fame cannot be higher than 1,000,000";
    public static final String ERROR_NEGATIVE_INPUT = "Parameter minimum fame cannot be negative";
    public static final String ERROR_WRONG_INPUT = "Provided input is not number or is out of range";

    // EXCEPTION

    public static final String EXCEPTION_NOT_MEMBER_OF_ALLIANCE = "Player %s is not a member of any alliance!";
    public static final String EXCEPTION_PLAYER_NOT_FOUND = "Player not found";

    public static final String EXCEPTION_API_PROBLEM = "Problem with Albion API server, try again later";

    public static final String EXCEPTION_ENTER_PLAYER_NAME = "You must enter a player name";
    public static final String EXCEPTION_ENTER_GUILD_NAME = "You must enter a guild name";
    public static final String EXCEPTION_ENTER_ALLIANCE_NAME = "You must enter a alliance name";
    public static final String EXCEPTION_WEBHOOK_LIMIT_EXCEEDED = "Filter limit exceeded, current limit for your server is ";

    public static final String EXCEPTION_UNKNOWN_LANGUAGE = "Unknown language, list of available languages:";

    public static final String EXCEPTION_UNKNOWN_COMMAND = "Unknown Command";
    public static final String EXCEPTION_UNKNOWN_ERROR = "Unknown Error";

    public static final String EXCEPTION_WRONG_ARGUMENTS = "Wrong arguments! Examples of usage:";

    // SHARED

    public static final String HINT_TYPE_FOR_HELP = "Type %s for help";

    public static final String YES = "Yes";
    public static final String NO = "No";

    public static final String PLAYERS = "Players";
    public static final String GUILDS = "Guilds";
    public static final String BATTLES_OF_GUILDS = "Battles of guilds";
    public static final String BATTLES_OF_ALLIANCES = "Battles of alliances";

    public static final String INFORMATION = "Information";
    public static final String ERROR = "Error";

    public static final String AUTHOR = "Blood Bot";
    public static final String AUTHOR_AVATAR_URL = "https://media.discordapp.net/attachments/610215183230566429/611159359048646656/69156192_2083446631960038_3560085669961269248_n.png";

}
