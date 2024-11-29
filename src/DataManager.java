import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private final String filename;

    public DataManager(String filename) {
        this.filename = filename;
    }

    @SuppressWarnings("unchecked")
    public Map<String, User> loadUsers() {
        File file = new File(filename);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки данных: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public void saveUsers(Map<String, User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(users);
            System.out.println("Данные сохранены.");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }
}