package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private WordleDictionary dictionary;
    private WordleGame game;
    private PrintWriter log;

    @BeforeEach
    void setUp() {
        log = new PrintWriter(System.out, true);

        List<String> words = Arrays.asList("герой", "гонец", "мотор", "город", "рыбак");
        dictionary = new WordleDictionary(words);

        game = new WordleGame(dictionary, log) {
            @Override
            public String getAnswer() {
                return "герой";
            }
        };
    }

    @Test
    void testCorrectMoveHint() throws WordleException {
        String hint = game.makeMove("гонец");
        assertEquals(5, hint.length());
        for (char c : hint.toCharArray()) {
            assertTrue(c == '+' || c == '-' || c == '^');
        }
    }

    @Test
    void testCorrectMoveHintProperties() throws WordleException {
        String hint = game.makeMove("гонец");
        assertEquals(5, hint.length());
        for (char c : hint.toCharArray()) {
            assertTrue(c == '+' || c == '-' || c == '^');
        }
    }

    @Test
    void testEmptyInputReturnsSuggestion() throws WordleException {
        String suggestion = game.makeMove("");
        assertNotNull(suggestion);
        assertTrue(dictionary.contains(suggestion));
        assertEquals(5, suggestion.length());
    }

    @Test
    void testStepsDecreaseOnValidMove() throws WordleException {
        int initialSteps = game.getSteps();
        game.makeMove("гонец");
        assertEquals(initialSteps - 1, game.getSteps());
    }

    @Test
    void testStepsNotDecreaseOnInvalidMove() {
        int initialSteps = game.getSteps();
        assertThrows(WordNotFoundInDictionaryException.class, () -> game.makeMove("птица"));
        assertEquals(initialSteps, game.getSteps());
    }

    @Test
    void testFilterByHints() throws WordleException {
        game.makeMove("гонец");
        List<String> guessesCopy = new ArrayList<>(game.getGuesses());
        List<String> hintsCopy = new ArrayList<>(game.getHints());

        List<String> candidates = dictionary.filterByHints(
                dictionary.allWords(),
                guessesCopy,
                hintsCopy
        );

        assertNotNull(candidates);
        assertFalse(candidates.isEmpty());
        for (String candidate : candidates) {
            assertEquals(5, candidate.length());
            assertTrue(dictionary.contains(candidate));
        }
    }

    @Test
    void testGameFinishAfterSixMoves() {
        String[] moves = {"мотор", "гонец", "город", "рыбак", "мотор", "гонец"};
        for (String move : moves) {
            try {
                game.makeMove(move);
            } catch (WordleException e) {
            }
        }
        assertTrue(game.isFinished());
    }


    @Test
    void testWordNotInDictionary() {
        assertThrows(WordNotFoundInDictionaryException.class, () -> game.makeMove("птица"));
    }

    @Test
    void testInvalidWordFormat() {
        assertThrows(InvalidWordFormatException.class, () -> game.makeMove("abc"));
        assertThrows(InvalidWordFormatException.class, () -> game.makeMove("12345"));
        assertThrows(InvalidWordFormatException.class, () -> game.makeMove("a1b2c"));
    }
}
