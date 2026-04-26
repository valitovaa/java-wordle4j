package ru.yandex.practicum;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class WordleGame {
    private final WordleDictionary dictionary;
    private final String answer;
    //private int steps;
    private int attemptsLeft;
    FileWriter logfile;
    List<Pair<String, String>> guessHistory;  // История предположений игрока


    public WordleGame(WordleDictionary dictionary, FileWriter logFile) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.attemptsLeft = 6;
        this.guessHistory = new ArrayList<>();
        this.logfile = logFile;
        // История: слово + подсказка

    }

    // Метод для обработки хода игрока
    public boolean makeGuess(String guess) {
        if (guess.length() != 5 || !dictionary.contains(guess)) {
            throw new IllegalArgumentException("Слово должно быть из пяти букв и присутствовать в словаре.");
        }

        attemptsLeft--;

        String hint = generateHint(guess);
        this.guessHistory.add(new Pair<>(guess, hint)); // Добавляем предположение в историю

        if (guess.equals(answer)) {
            return true; // Победа
        } else if (attemptsLeft == 0) {
            return false; // Поражение
        }
        return false;
    }

    // Генерация подсказки на основе предположения игрока
    public String generateHint(String guess) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            if (guessChar == answer.charAt(i)) {
                hint.append('+');
            } else if (answer.contains(String.valueOf(guessChar))) {
                hint.append('^');
            } else {
                hint.append('-');
            }
        }
        return hint.toString();
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getAnswer() {
        return answer;
    }


    public String generateHintWord() {
        List<String> filteredWords = new ArrayList<>(dictionary.getWords()); // Копируем весь словарь

        // Проходим по истории и фильтруем слова по каждой подсказке
        for (Pair<String, String> entry : guessHistory) {
            String guess = entry.first;
            String hint = entry.second;
            filteredWords.retainAll(getMatchingWords(guess, hint));
        }

        if (filteredWords.isEmpty()) {
            throw new IllegalStateException("Не удалось найти подходящие слова!");
        }

        Random random = new Random();
        return filteredWords.get(random.nextInt(filteredWords.size())); // Случайное слово из подходящих
    }

    private List<String> getMatchingWords(String guess, String hint) {
        ArrayList<String> matchingWords = new ArrayList<>();
        for (String word : dictionary.getWords()) {
            if (word.length() == guess.length()) { // Проверяем длину
                boolean matches = true;
                for (int i = 0; i < guess.length(); i++) {
                    if (hint.charAt(i) == '+' && word.charAt(i) != guess.charAt(i)) {
                        matches = false;
                        break;
                    } else if (hint.charAt(i) == '-' && word.contains(String.valueOf(guess.charAt(i)))) {
                        matches = false;
                        break;
                    } else if (hint.charAt(i) == '^') {
                        // Буква есть в слове, но не на этой позиции
                        if (!word.contains(String.valueOf(guess.charAt(i))) || word.charAt(i) == guess.charAt(i)) {
                            matches = false;
                            break;
                        }
                    }
                }
                if (matches) {
                    matchingWords.add(word);
                }
            }
        }
        return matchingWords;
    }


}

