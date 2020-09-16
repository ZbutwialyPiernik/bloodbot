package com.zbutwialypiernik.bloodbot.command.base;

public abstract class OwnerCommand extends Command {

    @Override
    public final boolean isOwnerOnly() {
        return true;
    }

}
