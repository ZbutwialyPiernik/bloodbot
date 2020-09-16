package com.zbutwialypiernik.bloodbot.command.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {

    public abstract String getKeyword();

    public abstract String getDescription();

    public abstract String getUsage();

    public abstract Pattern getPattern();

    public abstract void onInvoke(CommandContext context, Matcher args);

    public boolean isOwnerOnly() {
        return false;
    }
}
