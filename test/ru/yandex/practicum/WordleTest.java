package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class WordleTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
    void testLogCreationAndWriting() throws Exception {
        // Сохраняем стандартные потоки вывода
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        try {
            // Создаём временный файл для словаря
            Path tempDictFile = Files.createTempFile("test_", ".txt");
            Files.writeString(tempDictFile, "яблок\nбанан\nкошка");

            WordleDictionaryLoader loader = new WordleDictionaryLoader(null);
            loader.loadDictionary(tempDictFile.toString(), "UTF-8");

            WordleDictionary dictionary = new WordleDictionary();
            dictionary.loadWords(loader.getWordList());

            // Создаём временный лог‑файл
            Path tempLogFile = Files.createTempFile("wordle_", ".log");
            FileWriter logFile = new FileWriter(tempLogFile.toFile());

            try {
                Wordle.log(logFile, "Тест записи в лог");
                logFile.close();

                // Проверяем, что запись попала в файл
                String logContent = Files.readString(tempLogFile);
                assertTrue(logContent.contains("Тест записи в лог"));
            } finally {
                Files.deleteIfExists(tempLogFile); // Удаляем временный файл
            }
        } finally {
            // Восстанавливаем стандартные потоки вывода
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    @Test
    void testDictionaryLoading() throws Exception {
        // Создаём тестовый файл словаря
        Path testDictFile = Files.createTempFile("test_words_", ".txt");
        Files.writeString(testDictFile, "яблок\nбанан\nкошка");

        WordleDictionaryLoader loader = new WordleDictionaryLoader(null);
        loader.loadDictionary(testDictFile.toString(), "UTF-8");

        WordleDictionary dictionary = new WordleDictionary();
        dictionary.loadWords(loader.getWordList());

        assertEquals(3, dictionary.getWords().size()); // Три слова в словаре
        assertTrue(dictionary.contains("яблок"));
        assertTrue(dictionary.contains("банан"));
        assertTrue(dictionary.contains("кошка"));

        Files.deleteIfExists(testDictFile); // Удаляем временный файл
    }

    @Test
    void testGameStart() throws Exception {
        // Создаём тестовый файл словаря
        Path testDictFile = Files.createTempFile("test_words_", ".txt");
        Files.writeString(testDictFile, "яблок\nбанан\nкошка");

        WordleDictionaryLoader loader = new WordleDictionaryLoader(null);
        loader.loadDictionary(testDictFile.toString(), "UTF-8");

        WordleDictionary dictionary = new WordleDictionary();
        dictionary.loadWords(loader.getWordList());

        try (FileWriter logFile = new FileWriter("wordle_game.log")) {
            WordleGame game = new WordleGame(dictionary, logFile);
            assertNotNull(game); // Игра успешно создана
            assertTrue(dictionary.contains(game.getAnswer())); // Ответ есть в словаре
        }
    }
}
