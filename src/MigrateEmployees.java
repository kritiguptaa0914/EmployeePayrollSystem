
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class MigrateEmployees {

    public static void main(String[] args) {
        ArrayList<Employee> oldEmployees = new ArrayList<>();

        // Read old employee records
        try (ObjectInputStream input =
                new ObjectInputStream(
                    new FileInputStream("employees.dat"))) {

            oldEmployees =
                (ArrayList<Employee>) input.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("employees.dat was not found.");
            return;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not read old records: "
                    + e.getMessage());
            return;
        }

        int migrated = 0;
        int skipped = 0;

        String sql = "INSERT INTO employees "
            + "(id, name, designation, employee_type, basic_salary, "
            + "hra, allowances, deductions, net_salary) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE id = id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Employee e : oldEmployees) {
                ps.setInt(1, e.getEmployeeId());
                ps.setString(2, e.getName());
                ps.setString(3, e.getDesignation());
                ps.setString(4, e.getEmployeeType());
                ps.setDouble(5, e.getBasicSalary());
                ps.setDouble(6, 0);
                ps.setDouble(7, e.calculateAllowances());
                ps.setDouble(8, e.calculateDeductions());
                ps.setDouble(9, e.calculateNetSalary());

                int result = ps.executeUpdate();

                if (result == 1) {
                    migrated++;
                } else {
                    skipped++;
                }
            }

            System.out.println("Migration finished.");
            System.out.println("Records inserted: " + migrated);
            System.out.println("Records skipped: " + skipped);

        } catch (SQLException e) {
            System.out.println("Database migration failed: "
                    + e.getMessage());
        }
    }
}
