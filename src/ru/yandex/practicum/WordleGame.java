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

import ru.yandex.practicum.exceptions.*;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    private final WordleDictionary dictionary;
    private final PrintWriter log;
    private final String answer;

    private int steps = 6;

    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.randomWord(new Random());
        log.println("Ответ: " + answer);
    }

    public boolean isFinished() {
        return steps <= 0 || isWin();
    }

    public boolean isWin() {
        return !guesses.isEmpty() && guesses.get(guesses.size() - 1).equals(answer);
    }

    public String makeMove(String input) {
        if (input.isBlank()) {
            return suggest();
        }

        String word = WordleDictionary.normalize(input);

        if (word.length() != 5 || !word.chars().allMatch(Character::isLetter)) {
            throw new InvalidWordFormatException(word);
        }

        if (!dictionary.contains(word)) {
            throw new WordNotFoundInDictionaryException(word);
        }

        String hint = WordleDictionary.analyze(word, answer);
        guesses.add(word);
        hints.add(hint);
        steps--;

        return hint;
    }

    private String suggest() {
        List<String> candidates = dictionary.filterByHints(dictionary.allWords(), guesses, hints);

        if (candidates.isEmpty()) {
            log.println("Ошибка: кандидатов нет");
            log.println("guesses=" + guesses);
            log.println("hints=" + hints);
            throw new RuntimeException("Нет возможных слов");
        }

        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public List<String> getHints() {
        return hints;
    }
}
