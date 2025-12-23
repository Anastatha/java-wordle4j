package ru.yandex.practicum.exceptions;

public class InvalidWordFormatException extends WordleException {
    public InvalidWordFormatException(String word) {
        super("Некорректный формат слова: " + word);
    }
}
