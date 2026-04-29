package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class WordleGameTest {
    private WordleDictionary dictionary;
    private WordleGame game;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Создаём тестовый словарь
        dictionary = new WordleDictionary();
        List<String> testWords = Arrays.asList(
                "яблок", "банан", "кошка", "столб", "книга", "река"
        );
        dictionary.loadWords(testWords);


        game = new WordleGame(dictionary, null);
    }


    @Test
    void testInitialGameState() {
        assertEquals(6, game.getAttemptsLeft());
        assertTrue(dictionary.contains(game.getAnswer()));
    }




    @Test
    void testRegularGuess() throws IncorrectInputException {

            game.makeGuess("кошка");

        assertEquals(5, game.getAttemptsLeft());
    }






    @Test
    void testGenerateHintWord() {
        try {
            String hintWord = game.generateHintWord();
            assertTrue(dictionary.contains(hintWord));
            assertEquals(5, hintWord.length());
        } catch (IllegalStateException e) {
            fail("Не удалось сгенерировать слово‑подсказку: " + e.getMessage());
        } catch (NoSuitableWordsException e) {
            throw new RuntimeException(e);
        }
    }




    @Test
    void testEmptyDictionaryError() {
        WordleDictionary emptyDictionary = new WordleDictionary();
        emptyDictionary.loadWords(new ArrayList<>());

        assertThrows(RuntimeException.class,
                () -> new WordleGame(emptyDictionary, null));
    }




}
