package org.dmitry.moviesearcher.dto;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class LastNameDatesRequestDtoTest {

    @Test
    void patternForLastName_Test() {
        Pattern pattern = Pattern.compile("^[a-zA-z-']*$");

        Matcher letters = pattern.matcher("SomeLastName");
        Matcher lettersWithSeparator = pattern.matcher("Mac'Surname-Double");
        Matcher containInt = pattern.matcher("Henry1");
        Matcher containSymbol = pattern.matcher("Double name");

        assertTrue(letters.matches());
        assertTrue(lettersWithSeparator.matches());

        assertFalse(containInt.matches());
        assertFalse(containSymbol.matches());
    }
}