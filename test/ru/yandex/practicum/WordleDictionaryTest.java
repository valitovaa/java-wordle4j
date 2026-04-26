package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary();
    }

    @Test
    void testLoadWords() {
        List<String> words = Arrays.asList("яблок", "банан", "кошка", "дом", "стол", "стул");
        dictionary.loadWords(words);

        assertEquals(4, dictionary.getWords().size());
        assertTrue(dictionary.getWords().contains("яблок"));
        assertTrue(dictionary.getWords().contains("банан"));
        assertTrue(dictionary.getWords().contains("кошка"));
        assertTrue(dictionary.getWords().contains("стол"));
    }

    @Test
    void testGetRandomWord() {
        List<String> words = Arrays.asList("яблок", "банан");
        dictionary.loadWords(words);

        String randomWord = dictionary.getRandomWord();
        assertTrue(words.contains(randomWord));
    }

    @Test
    void testContains() {
        List<String> words = Arrays.asList("яблок", "банан");
        dictionary.loadWords(words);

        assertTrue(dictionary.contains("яблок"));
        assertFalse(dictionary.contains("вишня"));
    }

    @Test
    void testEmptyDictionary() {
        assertThrows(RuntimeException.class, () -> dictionary.getRandomWord());
    }
}
