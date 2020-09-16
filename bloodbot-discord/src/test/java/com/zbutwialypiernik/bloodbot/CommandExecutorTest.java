package com.zbutwialypiernik.bloodbot;

import com.zbutwialypiernik.bloodbot.entity.DiscordUser;
import com.zbutwialypiernik.bloodbot.repository.AlbionRepository;
import com.zbutwialypiernik.bloodbot.repository.DiscordUserRepository;
import net.dv8tion.jda.core.JDA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandExecutorTest {

    @Mock
    AlbionRepository albionRepository;

    @Mock
    DiscordUserRepository userRepository;

    @Mock
    JDA jda;

    String prefix = "!testPrefix";

    String regex;

    CommandExecutor commandExecutor;

    @BeforeAll
    public void prepareClient() throws InterruptedException {
        MockitoAnnotations.initMocks(this);

        commandExecutor = new CommandExecutor(Collections.emptyList(), null, null);
        regex = commandExecutor.createSplitRegex(prefix);
    }

    @Test
    public void regexTest() {
        Pattern pattern = Pattern.compile(regex);

        assertMatches(pattern, "!testPrefix command args", "command", "args");
        assertMatches(pattern, "!testPrefix command-command args aaaa", "command-command", "args aaaa");
        assertMatches(pattern, "!testPrefix command", "command", "");
        assertMatches(pattern, "!testPrefix", "", "");

        //Wrong Prefix
        assertNotMatches(pattern, "!wrongPrefix");

        //Illegal Characters
        assertNotMatches(pattern, "!testPrefix %S%S");
        assertNotMatches(pattern, "!testPrefix %S%S %S%S");
    }

    public void assertMatches(Pattern pattern, String wholeString, String commandName, String args) {
        Matcher matcher = pattern.matcher(wholeString);

        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals(matcher.groupCount(), 2);
        Assertions.assertEquals(matcher.group(1), commandName);
        Assertions.assertEquals(matcher.group(2), args);
    }

    public void assertNotMatches(Pattern pattern, String wholeString) {
        Matcher matcher = pattern.matcher(wholeString);

        Assertions.assertFalse(matcher.matches());
    }

}
