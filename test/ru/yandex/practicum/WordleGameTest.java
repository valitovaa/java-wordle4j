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
    void testCorrectGuess() {
        boolean result = game.makeGuess(game.getAnswer());
        assertTrue(result); // Победа
        assertEquals(0, game.getAttemptsLeft());
    }


    @Test
    void testRegularGuess() {
        game.makeGuess("кошка");
        assertEquals(5, game.getAttemptsLeft());
    }


    @Test
    void testLoseGame() {
        for (int i = 0; i < 6; i++) {
            game.makeGuess("репка");
        }
        assertFalse(game.makeGuess("банан"));
        assertEquals(0, game.getAttemptsLeft());
    }


    @Test
    void testGenerateHint() {
        String hint = game.generateHint("репка");
        assertNotEquals("+++++", hint);
        assertEquals("-----", hint);
    }

    @Test
    void testPartialMatchHint() {
        String answer = game.getAnswer();
        String guess = "река";
        String expectedHint = (answer.charAt(0) == 'р' ? '+' : '-') +
                (answer.charAt(1) == 'е' ? '+' : '-') + "---";
        String actualHint = game.generateHint(guess);
        assertEquals(expectedHint, actualHint);
    }


    @Test
    void testGenerateHintWord() {
        try {
            String hintWord = game.generateHintWord();
            assertTrue(dictionary.contains(hintWord));
            assertEquals(5, hintWord.length());
        } catch (IllegalStateException e) {
            fail("Не удалось сгенерировать слово‑подсказку: " + e.getMessage());
        }
    }


    @Test
    void testMakeGuessWithInvalidWord() {
        assertThrows(IllegalArgumentException.class,
                () -> game.makeGuess("яблокк"));
        assertThrows(IllegalArgumentException.class,
                () -> game.makeGuess("стол"));
    }

    @Test
    void testEmptyDictionaryError() {
        WordleDictionary emptyDictionary = new WordleDictionary();
        emptyDictionary.loadWords(new ArrayList<>());

        assertThrows(RuntimeException.class,
                () -> new WordleGame(emptyDictionary, null));
    }

    // --- Тест 8: проверка истории ходов ---
    @Test
    void testGuessHistory() {
        game.makeGuess("репка");
        game.makeGuess("банан");

        assertEquals(2, game.guessHistory.size()); // Два хода в истории
        assertEquals("репка", game.guessHistory.get(0).first);
    }


    @Test
    void testHintWordAfterSeveralGuesses() {
        game.makeGuess("репка");
        game.makeGuess("банан");

        String hintWord = game.generateHintWord();
        assertTrue(dictionary.contains(hintWord));
        assertNotNull(hintWord);
    }
}
