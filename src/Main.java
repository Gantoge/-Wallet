public class Main {
    private final User userManager;

    public Main() {
        this.userManager = new User();
    }

    public void run() {
        ConsoleHelper console = new ConsoleHelper();
        while (true) {
            int choice = console.displayMainMenu();
            switch (choice) {
                case 1 -> userManager.login(console);
                case 2 -> userManager.register(console);
                case 3 -> {
                    userManager.saveUsers();
                    System.out.println("Выход из приложения.");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}