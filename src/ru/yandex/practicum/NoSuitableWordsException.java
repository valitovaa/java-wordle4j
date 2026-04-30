package ru.yandex.practicum;

import java.io.Serial;

public class NoSuitableWordsException extends Exception {
    @Serial
    private static final long serialVersionUID = -3115381378625514828L;

    public NoSuitableWordsException(String message) {
        super(message);
    }
}
