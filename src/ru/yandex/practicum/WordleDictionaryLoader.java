package ru.yandex.practicum;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    List<String> wordList;

    WordleDictionaryLoader(FileWriter logFile) {
        this.wordList = new ArrayList<>();
    }


    public void loadDictionary(String filePath, String charset) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), charset))) {
            String line;
            wordList.clear();

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    if (line.length() == 5) {
                        String processedWord = line.toLowerCase().replace('ё', 'е');
                        wordList.add(processedWord);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке словаря: " + e.getMessage());
        }
    }

    public List<String> getWordList() {
        return new ArrayList<>(wordList);
    }


}
