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

    public static final int perfectLength = 5;


    public void loadWords(List<String> rawWords) {
        this.words = new ArrayList<>();
        for (String word : rawWords) {

            if (isSuitableForGame(word)) {
                words.add(word);
            }
        }
    }


    private boolean isSuitableForGame(String word) {
        return word.length() == perfectLength;
    }

    public String getRandomWord() throws DictionaryIsEmptyException {
        if (words.isEmpty()) {
            throw new DictionaryIsEmptyException("Словарь пуст");
        }
        return words.get(random.nextInt(words.size()));
    }



    public List<String> getWords() {
        return words;
    }


    public boolean contains(String guess) {
        return words.contains(guess);
    }
}

