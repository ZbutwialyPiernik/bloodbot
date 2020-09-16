package com.zbutwialypiernik.bloodbot.util;

public class Regex {

    public static final String WEBHOOK_REGEX = "^https:\\/\\/discordapp\\.com\\/api\\/webhooks\\/\\d{18}\\/[\\w\\-\\_]{68}$";

    public static final String WEBHOOK_REGEX_CATCH_SERVER_ID = "^https:\\/\\/discordapp\\.com\\/api\\/webhooks\\/(\\d{18})\\/[\\w\\-\\_]{68}$";

    public static final String ALBION_NAME_REGEX = "^[\\w\\-\\_ ]{3,16}$";

}
