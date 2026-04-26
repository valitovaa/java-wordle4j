
/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        // 1. Создаём лог‑файл — он будет передаваться во все классы
        FileWriter logFile;
        try {
            logFile = new FileWriter("wordle_game.log");
            log(logFile, "Игра Wordle запущена");
        } catch (IOException e) {
            System.err.println("Не удалось создать лог‑файл: " + e.getMessage());
            return; // Завершаем программу, если не смогли создать лог
        }

        // 2. Создаём загрузчик словарей
        WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader(logFile);

        // 3. Загружаем словарь — здесь может возникнуть исключение, если файл не найден
        WordleDictionary dictionary;
        try {
            dictionaryLoader.loadDictionary("words_ru.txt", "UTF-8");
            dictionary = new WordleDictionary();
            dictionary.loadWords(dictionaryLoader.getWordList());
            log(logFile,"словарь успешно загружен");
        } catch (Exception e) {
            log(logFile, "Ошибка при загрузке словаря: " + e.getMessage());
            System.err.println(e.getMessage());
            closeLog(logFile);
            return;
        }

        // 4. Создаём игру и передаём ей словарь
        WordleGame game = new WordleGame(dictionary, logFile);
        log(logFile,"загаданное слово: " + game.getAnswer());

        // 5. Запускаем игровой цикл
        Scanner scanner = new Scanner(System.in);
        boolean gameFinished = false;
        System.out.println("Вас приветсвует игра Wordle");

        while (!gameFinished) {
            // Опрашиваем пользователя
            System.out.print("Введите слово из 5 букв: ");
            String playerInput = scanner.nextLine();
            log(logFile, "игрок ввел слово: " + playerInput);
            if (playerInput.isEmpty()) {
                log(logFile,"пользователь ввел пустую строку, сгенерирована подсказка");
                System.out.println("Возможно подойдет слово: " + game.generateHintWord());

            } else {

                try {
                    // Передаём слово в игру и получаем результат хода
                    boolean gameResult = game.makeGuess(playerInput);


                    if (gameResult) {
                        System.out.println("Поздравляем! Вы отгадали слово!");
                        log(logFile, "Игра завершена. Игрок победил.");
                    } else if (game.getAttemptsLeft() == 0) {
                        System.out.println("Попытки закончились. Загаданное слово было: " + game.getAnswer());
                        log(logFile, "Игра завершена. Игрок проиграл. Загаданное слово: " + game.getAnswer());
                    } else {
                        String res = game.generateHint(playerInput);
                        System.out.println(res);
                        log(logFile, "игрок не угадал слово:" + res);
                    }

                    gameFinished = gameResult || game.getAttemptsLeft() == 0; // Игра завершена, если игрок выиграл или проиграл

                } catch (RuntimeException e) {
                    // Обрабатываем игровые ошибки (например, некорректный ввод)
                    System.out.println("Ошибка: " + e.getMessage());
                    log(logFile, "Ошибка ввода: " + e.getMessage());
                }
            }
        }

        // 6. Закрываем ресурсы
        scanner.close();
        closeLog(logFile);
    }

    // Вспомогательный метод для записи в лог
    static void log(FileWriter logFile, String message) {
        try {
            logFile.write(message + System.lineSeparator());
            logFile.flush(); // Сразу записываем в файл
        } catch (IOException e) {
            System.err.println("Ошибка записи в лог: " + e.getMessage());
        }
    }

    // Вспомогательный метод для корректного закрытия лог‑файла
    private static void closeLog(FileWriter logFile) {
        try {
            if (logFile != null) {
                logFile.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии лог‑файла: " + e.getMessage());
        }
    }
}
