
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection()
            throws SQLException {

        String url =
            "jdbc:mysql://localhost:3306/employee_payroll";
        String user = "root";
        String password = "Kriti09@";

        return DriverManager.getConnection(
            url, user, password
        );
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println(
                "MySQL connected successfully!"
            );
        } catch (SQLException e) {
            System.out.println(
                "MySQL connection failed:"
            );
            System.out.println(e.getMessage());
        }
    }
}
