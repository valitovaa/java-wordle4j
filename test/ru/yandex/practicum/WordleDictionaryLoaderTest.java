package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private String testFilePath;

    @BeforeEach
    void setUp() throws IOException {
        // Создаём временный файл перед каждым тестом
        testFilePath = "temp_test_dictionary.txt";
    }

    @AfterEach
    void tearDown() throws IOException {
        // Удаляем временный файл после каждого теста
        deleteFile(testFilePath);
    }


    private void writeToFile(String filePath, List<String> words) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (String word : words) {
                writer.println(word);
            }
        }
    }


    private void createEmptyFile(String filePath) throws IOException {
        new File(filePath).createNewFile();
    }


    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    @Test
    void testLoadEmptyFile() throws Exception {
        createEmptyFile(testFilePath);

        WordleDictionaryLoader loader = new WordleDictionaryLoader(null);
        loader.loadDictionary(testFilePath, "UTF-8");

        List<String> result = loader.getWordList();
        assertTrue(result.isEmpty()); // Проверяем, что список слов пуст
    }


    @Test
    void testLoadDictionary_EmptyLines() throws Exception {
        List<String> wordsWithEmptyLines = Arrays.asList("яблок", "", "банан", "  ", "кошка");
        writeToFile(testFilePath, wordsWithEmptyLines);

        WordleDictionaryLoader loader = new WordleDictionaryLoader(null);
        loader.loadDictionary(testFilePath, "UTF-8");

        List<String> result = loader.getWordList();
        assertEquals(3, result.size()); // Проверяем, что пустые строки не попали в результат
        assertTrue(result.contains("яблок"));
        assertTrue(result.contains("банан"));
        assertTrue(result.contains("кошка"));
    }

    @Test
    void testLoadDictionary_DifferentCharset() throws Exception {
        // Создаём файл с другой кодировкой
        String differentCharsetFilePath = "test_different_charset.txt";
        List<String> words = Arrays.asList("яблок", "банан");
        writeToFileWithCharset(differentCharsetFilePath, words, "UTF-16"); // Используем другую кодировку при записи

        WordleDictionaryLoader loader = new WordleDictionaryLoader(null);
        loader.loadDictionary(differentCharsetFilePath, "UTF-16");

        List<String> result = loader.getWordList();
        assertEquals(2, result.size());
        assertTrue(result.contains("яблок"));
        assertTrue(result.contains("банан"));

        deleteFile(differentCharsetFilePath);
    }

    // Вспомогательный метод для записи файла с указанной кодировкой
    private void writeToFileWithCharset(String filePath, List<String> words, String charset) throws IOException {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), charset))) {
            for (String word : words) {
                writer.println(word);
            }
        }
    }

}
