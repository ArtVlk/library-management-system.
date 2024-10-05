package ru.ArtemVolk.NauJava;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import business.logic.UserService;
import models.User;

@Component
public class ConsoleInputHandler {
    private final UserService userService;

    @Autowired
    public ConsoleInputHandler(UserService userService) {
        this.userService = userService;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите команду. 'exit' для выхода.");
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input.trim())) {
                    System.out.println("Выход из программы...");
                    break;
                }
                processCommand(input);
            }
        }
    }

    private void processCommand(String input) {
        String[] cmd = input.split(" ");
        switch (cmd[0]) {
            case "createUser":
                if (cmd.length < 4) {
                    System.out.println("Недостаточно аргументов для команды createUser." +
                            " Используйте формат: createUser <id> <name> <email>");
                    break;
                }
                userService.createUser(Long.valueOf(cmd[1]), cmd[2], cmd[3]);
                break;
            case "findUser":
                userService.findById(Long.valueOf(cmd[1]));
                break;
            case "deleteUser":
                userService.deleteById(Long.valueOf(cmd[1]));
                break;
            case "updateUser":
                userService.updateUser(Long.valueOf(cmd[1]), cmd[2], cmd[3]);
                break;
            case "borrowBook":
                userService.borrowBook(Long.valueOf(cmd[1]), Long.valueOf(cmd[2]));
                break;
            case "returnBook":
                userService.returnBook(Long.valueOf(cmd[1]), Long.valueOf(cmd[2]));
                break;
            default:
                System.out.println("Неизвестная команда...");
                break;
        }
    }
}