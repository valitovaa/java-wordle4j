package ru.yandex.practicum;

import java.io.Serial;

public class DictionaryIsEmptyException extends Exception {
    @Serial
    private static final long serialVersionUID = 2991063017281053464L;

    public DictionaryIsEmptyException(String message) {
        super(message);
    }
}
