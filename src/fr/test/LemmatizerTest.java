package test;

import org.junit.Test;

import search_engine.Lemmatizer;

import static org.junit.Assert.*;

import java.io.IOException;

public class LemmatizerTest {

    @Test
    public void testLemmatizer() throws IOException {
        Lemmatizer lemmatizer = new Lemmatizer(".\\src\\lemmatisation\\");
        
        // Test lemmatize method
        assertEquals("abaissable", lemmatizer.lemmatize("abaissable"));
        assertEquals("abaissable", lemmatizer.lemmatize("abaissables"));
        assertEquals("abaisser", lemmatizer.lemmatize("abaissai"));
        assertEquals("abaisser", lemmatizer.lemmatize("abaissaient"));
        assertEquals("abaisser", lemmatizer.lemmatize("abaissais"));
        assertEquals("abaisser", lemmatizer.lemmatize("abaissait"));
        assertEquals("word", lemmatizer.lemmatize("word")); // non-existent word

        // Test processText method
        String input = "abaissable abaissables abaisser abaissaient abaissais abaissait";
        String expectedOutput = "abaissable abaissable abaisser abaisser abaisser abaisser";
        String actualOutput = lemmatizer.processText(input);
        assertEquals(expectedOutput, actualOutput);

        input = "word nonexistentword";
        expectedOutput = "word nonexistentword";
        actualOutput = lemmatizer.processText(input);
        assertEquals(expectedOutput, actualOutput);
    }
}
