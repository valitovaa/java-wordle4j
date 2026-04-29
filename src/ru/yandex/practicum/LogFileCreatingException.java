package ru.yandex.practicum;

import java.io.Serial;

public class LogFileCreatingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3257144740652568530L;

    public LogFileCreatingException(String message) {
        super(message);
    }
}
