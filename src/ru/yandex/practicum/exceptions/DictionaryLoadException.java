package ru.yandex.practicum.exceptions;

public class DictionaryLoadException extends Exception {
    public DictionaryLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
