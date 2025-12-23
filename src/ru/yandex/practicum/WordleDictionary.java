package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */


public class WordleDictionary {
    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = List.copyOf(words);
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String randomWord(Random random) {
        return words.get(random.nextInt(words.size()));
    }

    public List<String> allWords() {
        return words;
    }

    public static String normalize(String word) {
        return word.toLowerCase(Locale.ROOT).replace('ё', 'е');
    }

    public static String analyze(String guess, String answer) {
        char[] result = new char[5];
        boolean[] used = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (result[i] == '+') continue;
            char c = guess.charAt(i);
            boolean found = false;
            for (int j = 0; j < 5; j++) {
                if (!used[j] && answer.charAt(j) == c) {
                    found = true;
                    used[j] = true;
                    break;
                }
            }
            result[i] = found ? '^' : '-';
        }

        return new String(result);
    }

    public List<String> filterByHints(
            List<String> candidates,
            List<String> guesses,
            List<String> hints
    ) {
        List<String> result = new ArrayList<>();

        for (String word : candidates) {
            if (matchesAll(word, guesses, hints)) {
                result.add(word);
            }
        }

        return result;
    }

    private boolean matchesAll(String word, List<String> guesses, List<String> hints) {
        for (int i = 0; i < guesses.size(); i++) {
            if (!analyze(guesses.get(i), word).equals(hints.get(i))) {
                return false;
            }
        }
        return true;
    }
}
