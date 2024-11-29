import java.io.*;
import java.util.*;

public class Wallet implements Serializable {

    private final List<Transaction> transactions;
    private final Map<String, Double> budgets;

    public Wallet() {
        this.transactions = new ArrayList<>();
        this.budgets = new HashMap<>();
    }

    public void addTransaction(ConsoleHelper console) {
        String type = console.getInput("Выберите тип транзакции (1 - Доходы, 2 - Расходы): ");

        String category = console.getInput("Введите категорию: ");


        double amount = Double.parseDouble(console.getInput("Введите сумму: "));
        transactions.add(new Transaction(type.equals("1") ? "Доходы" : "Расходы", category, amount));
        System.out.println("Транзакция успешно добавлена.");
    }

    public void setBudget(ConsoleHelper console) {

        String category = console.getInput("Введите категорию: ");

        double budget = Double.parseDouble(console.getInput("Введите бюджет: "));
        budgets.put(category, budget);
        System.out.println("Бюджет успешно установлен.");
    }

    public void showSummary() {
        double totalIncome = 0, totalExpense = 0;
        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Доходы")) {
                totalIncome += t.getAmount();
                incomeByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
            } else {
                totalExpense += t.getAmount();
                expenseByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
            }
        }

        System.out.println("Общий доход: " + totalIncome);
        System.out.println("Доходы по категориям:");
        incomeByCategory.forEach((category, amount) ->
                System.out.println(category + ": " + amount)
        );

        System.out.println("\nОбщие расходы: " + totalExpense);
        System.out.println("Бюджет по категориям:");

        budgets.forEach((category, budget) -> {
            double spent = expenseByCategory.getOrDefault(category, 0.0);
            System.out.println(category + ": " + budget + ". Оставшийся бюджет: " + (budget - spent));
            if (spent > budget) {
                System.out.println("Превышен лимит бюджета по категории: " + category);
            }
        });

        expenseByCategory.forEach((category, spent) -> {
            if (!budgets.containsKey(category)) {
                System.out.println(category + ": Расходы = " + spent + ", Бюджет не установлен.");
            }
        });
    }
    public void saveWallet(String username) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(username + "_wallet.dat"))) {
            oos.writeObject(this);
            System.out.println("Кошелек успешно сохранен.");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения кошелька: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Wallet loadWallet(String username) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username + "_wallet.dat"))) {
            return (Wallet) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки кошелька. Создан новый кошелек.");
            return new Wallet();
        }
    }
}

