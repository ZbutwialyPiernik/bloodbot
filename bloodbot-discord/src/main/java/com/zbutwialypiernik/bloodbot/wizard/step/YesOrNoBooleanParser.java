package com.zbutwialypiernik.bloodbot.wizard.step;

public class YesOrNoBooleanParser implements Parser<Boolean> {

    @Override
    public Boolean parse(String input) {
        if (input.equalsIgnoreCase("yes")) {
            return true;
        } else if (input.equalsIgnoreCase("no")) {
            return false;
        }

        return null;
    }
}
