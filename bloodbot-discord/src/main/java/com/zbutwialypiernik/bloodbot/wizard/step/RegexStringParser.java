package com.zbutwialypiernik.bloodbot.wizard.step;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStringParser implements Parser<String> {

    private final Pattern pattern;

    public RegexStringParser(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public RegexStringParser(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String parse(String input) {
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            return null;
        }

        return input;
    }
}
