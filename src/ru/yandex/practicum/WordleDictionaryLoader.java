package ru.yandex.practicum;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */

import ru.yandex.practicum.exceptions.DictionaryLoadException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String fileName) throws DictionaryLoadException {
        List<String> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName),
                        StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String word = WordleDictionary.normalize(line);
                if (word.length() == 5 && word.chars().allMatch(Character::isLetter)) {
                    result.add(word);
                }
            }
        } catch (IOException e) {
            log.println("Ошибка чтения словаря" + e.getMessage());
            throw new DictionaryLoadException("Ошибка загрузки словаря", e);
        }

        if (result.isEmpty()) {
            log.println("Словарь пуст после фильтрации");
            throw new DictionaryLoadException("Словарь пуст", null);
        }

        log.println("Словарь успешно загружен");
        return new WordleDictionary(result);
    }
}
