package ru.yandex.practicum.exceptions;

public class WordNotFoundInDictionaryException extends WordleException {
    public WordNotFoundInDictionaryException(String word) {
        super("Слово отсутствует в словаре: " + word);
    }
}
