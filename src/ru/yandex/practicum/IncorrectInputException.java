package ru.yandex.practicum;

import java.io.Serial;

public class IncorrectInputException extends Exception {
    @Serial
    private static final long serialVersionUID = -7035865454677176170L;

    public IncorrectInputException(String message) {
        super(message);
    }
}
