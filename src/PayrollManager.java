import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    private ArrayList<Employee> employees;

    public PayrollManager() {
        employees = new ArrayList<>();
        loadEmployeesFromDatabase();
    }

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    private void loadEmployeesFromDatabase() {
        String sql = "SELECT id, name, designation, basic_salary, "
                   + "employee_type FROM employees";

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String designation = rs.getString("designation");
                double salary = rs.getDouble("basic_salary");
                String type = rs.getString("employee_type");

                Employee e;
                if ("Permanent".equalsIgnoreCase(type)) {
                    e = new PermanentEmployee(
                            id, name, designation, salary);
                } else {
                    e = new ContractEmployee(
                            id, name, designation, salary);
                }
                employees.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Could not load employees from MySQL: "
                    + e.getMessage());
        }
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public Employee findEmployee(int id) {
        for (Employee e : employees) {
            if (e.getEmployeeId() == id) return e;
        }
        return null;
    }

    public boolean addEmployee(Employee employee) {
        if (findEmployee(employee.getEmployeeId()) != null) {
            return false;
        }

        String sql = "INSERT INTO employees "
                + "(id, name, designation, employee_type, basic_salary, "
                + "hra, allowances, deductions, net_salary) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employee.getEmployeeId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getDesignation());
            ps.setString(4, employee.getEmployeeType());
            ps.setDouble(5, employee.getBasicSalary());
            ps.setDouble(6, 0);
            ps.setDouble(7, employee.calculateAllowances());
            ps.setDouble(8, employee.calculateDeductions());
            ps.setDouble(9, employee.calculateNetSalary());

            ps.executeUpdate();
            employees.add(employee);
            return true;

        } catch (SQLException e) {
            System.out.println("MySQL insert failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        Employee e = findEmployee(id);
        if (e == null) return false;

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM employees WHERE id = ?")) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) return false;
            employees.remove(e);
            return true;

        } catch (SQLException ex) {
            System.out.println("MySQL delete failed: "
                    + ex.getMessage());
            return false;
        }
    }

    private boolean updateField(int id, String column, Object value) {
        String sql = "UPDATE employees SET " + column + " = ? WHERE id = ?";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, value);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("MySQL update failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateName(int id, String name) {
        Employee e = findEmployee(id);
        if (e == null || !updateField(id, "name", name)) return false;
        e.setName(name);
        return true;
    }

    public boolean updateDesignation(int id, String designation) {
        Employee e = findEmployee(id);
        if (e == null ||
                !updateField(id, "designation", designation)) {
            return false;
        }
        e.setDesignation(designation);
        return true;
    }

    
public boolean updateSalary(int id, double salary) {
    Employee e = findEmployee(id);

    if (e == null || salary < 0 || !Double.isFinite(salary)) {
        return false;
    }

    String sql = "UPDATE employees SET basic_salary=?, allowances=?, "
               + "deductions=?, net_salary=? WHERE id=?";

    try (Connection con = connect()) {
        con.setAutoCommit(false);

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            double allowances = salary;
            double deductions = salary;

            // Calculate using the employee's existing salary rules.
            double oldSalary = e.getBasicSalary();
            e.setBasicSalary(salary);

            allowances = e.calculateAllowances();
            deductions = e.calculateDeductions();
            double netSalary = e.calculateNetSalary();

            // Restore the in-memory value until the database commits.
            e.setBasicSalary(oldSalary);

            ps.setDouble(1, salary);
            ps.setDouble(2, allowances);
            ps.setDouble(3, deductions);
            ps.setDouble(4, netSalary);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();

            if (rows != 1) {
                con.rollback();
                return false;
            }

            con.commit();

            // Update memory only after the database succeeds.
            e.setBasicSalary(salary);
            return true;

        } catch (SQLException | RuntimeException ex) {
            con.rollback();
            System.out.println("Salary update failed: " + ex.getMessage());
            return false;
        } finally {
            con.setAutoCommit(true);
        }

    } catch (SQLException ex) {
        System.out.println("Database error during salary update: "
                + ex.getMessage());
        return false;
        
    }
}


    public double getTotalPayroll() {
        double total = 0;
        for (Employee e : employees) {
            total += e.calculateNetSalary();
        }
        return total;
    }

    public Employee getHighestPaidEmployee() {
        if (employees.isEmpty()) return null;

        Employee highest = employees.get(0);
        for (Employee e : employees) {
            if (e.calculateNetSalary() >
                    highest.calculateNetSalary()) {
                highest = e;
            }
        }
        return highest;
    }
}
