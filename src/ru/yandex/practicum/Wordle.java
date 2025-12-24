package ru.yandex.practicum;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */

import ru.yandex.practicum.exceptions.WordleException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("wordle.log", true),
                        StandardCharsets.UTF_8
                ),
                true
        )) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dictionary = loader.load("words_ru.txt");
            WordleGame game = new WordleGame(dictionary, log);

            Scanner scanner = new Scanner(System.in);

            while (!game.isFinished()) {
                System.out.print("> ");
                String input = scanner.nextLine();

                try {
                    String result = game.makeMove(input);
                    System.out.println(input);
                    System.out.println(result);
                } catch (WordleException e) {
                    System.out.println(e.getMessage());
                    log.println("Игровая ошибка: " + e.getMessage());
                }
            }

            System.out.println("Загаданное слово: " + game.getAnswer());
            log.println("Игра завершена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
