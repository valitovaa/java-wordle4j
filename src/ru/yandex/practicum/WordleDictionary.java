package ru.yandex.practicum;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */

public class WordleDictionary {

    private List<String> words;

    private final Random random = new Random();


    public void loadWords(List<String> rawWords) {
        this.words = new ArrayList<>();
        for (String word : rawWords) {

            if (isSuitableForGame(word)) {
                words.add(word);
            }
        }
    }


    private boolean isSuitableForGame(String word) {
        return word.length() == 5;
    }

    public String getRandomWord() {
        if (words.isEmpty()) {
            throw new RuntimeException("Словарь пуст");
        }
        return words.get(random.nextInt(words.size()));
    }

//    public List<String> filterWords(String knownLetters, String correctPositions, String excludedLetters) {
//        List<String> filteredWords = new ArrayList<>();
//        for (String word : words) {
//            if (matchesCriteria(word, knownLetters, correctPositions, excludedLetters)) {
//                filteredWords.add(word);
//            }
//        }
//        return filteredWords;
//    }

    public List<String> getWords() {
        return words;
    }

//    private boolean matchesCriteria(String word, String knownLetters, String correctPositions, String excludedLetters) {
//        // 1. Проверяем длину слова
//        if (word.length() != 5) {
//            return false;
//        }
//
//        // 2. Проверяем исключённые буквы
//        for (char c : excludedLetters.toCharArray()) {
//            if (word.contains(String.valueOf(c))) {
//                return false; // Если буква из excluded есть в слове — не подходит
//            }
//        }
//
//        // 3. Проверяем правильные позиции (символ +)
//        for (int i = 0; i < correctPositions.length(); i++) {
//            char hintChar = correctPositions.charAt(i);
//            if (hintChar == '+') {
//                char wordChar = word.charAt(i); // Буква в слове на этой позиции
//                // Если в подсказке +, но буква не совпадает — слово не подходит
//                if (wordChar != knownLetters.charAt(i)) {
//                    return false;
//                }
//            }
//        }
//
//        // 4. Проверяем известные буквы (которые есть в слове, но позиция не известна)
//        for (char c : knownLetters.toCharArray()) {
//            if (c != '-' && !word.contains(String.valueOf(c))) {
//                return false; // Если известная буква отсутствует в слове — не подходит
//            }
//        }
//
//        // Если все проверки пройдены — слово подходит
//        return true;
//    }

    public boolean contains(String guess) {
        return words.contains(guess);
    }
}

