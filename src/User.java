import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String password;
    private Wallet wallet;
    private final Map<String, User> users;
    private final DataManager dataManager;

    public User() {
        this.wallet = null; // Загрузится при логине
        this.dataManager = new DataManager("users.dat");
        this.users = dataManager.loadUsers();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
        this.dataManager = null;
        this.users = null;
    }


    public void login(ConsoleHelper console) {
        String username = console.getInput("Введите логин: ");
        String password = console.getInput("Введите пароль: ");
        User user = users.get(username);

        if (user != null && user.checkPassword(password)) {
            user.wallet = Wallet.loadWallet(username);
            handleUserActions(user, console);
            user.wallet.saveWallet(username);
        } else {
            System.out.println("Неверный логин или пароль.");
        }
    }


    public void register(ConsoleHelper console) {
        String username = console.getInput("Введите логин: ");
        String password = console.getInput("Введите пароль: ");

        if (users.containsKey(username)) {
            System.out.println("Пользователь с таким логином уже существует.");
            return;
        }

        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUsers();
        newUser.wallet.saveWallet(username);
        System.out.println("Регистрация успешна.");
    }

    public void saveUsers() {
        dataManager.saveUsers(users);
    }

    private void handleUserActions(User user, ConsoleHelper console) {
        System.out.println("Добро пожаловать, " + user.getUsername() + "!");
        while (true) {
            int choice = console.displayUserMenu();
            switch (choice) {
                case 1 -> user.getWallet().addTransaction(console);
                case 2 -> user.getWallet().setBudget(console);
                case 3 -> user.getWallet().showSummary();
                case 4 -> {
                    System.out.println("Выход из аккаунта.");
                    return;
                }
            }
        }
    }

    public boolean checkPassword(String inputPassword) {
        return password != null && password.equals(inputPassword);
    }

    public String getUsername() {
        return username;
    }

    public Wallet getWallet() {
        return wallet;
    }
}