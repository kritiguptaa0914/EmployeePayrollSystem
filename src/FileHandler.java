import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final String fileName = "employees.dat";

    public void saveEmployees(List<Employee> employees) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            out.writeObject(new ArrayList<>(employees));
        } catch (IOException e) {
            System.out.println("Error saving employees: "
                               + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Employee> loadEmployees() {
        File file = new File(fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {
            return (ArrayList<Employee>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load employee records.");
            return new ArrayList<>();
        }
    }
}

