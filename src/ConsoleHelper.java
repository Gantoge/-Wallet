import java.util.Scanner;

public class ConsoleHelper {
    private final Scanner scanner = new Scanner(System.in);

    public int displayMainMenu() {
        System.out.println("1. Авторизация\n2. Регистрация\n3. Выход");
        return scanner.nextInt();
    }

    public int displayUserMenu() {
        System.out.println("1. Добавить транзакцию\n2. Установить бюджет\n3. Показать сводку\n4. Выход из аккаунта");
        return scanner.nextInt();
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}
