
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final PayrollManager payroll =
            new PayrollManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== EMPLOYEE PAYROLL SYSTEM ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Generate Salary Slip");
            System.out.println("7. Payroll Summary");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: displayEmployees(); break;
                    case 3: searchEmployee(); break;
                    case 4: updateEmployee(); break;
                    case 5: deleteEmployee(); break;
                    case 6: salarySlip(); break;
                    case 7: payrollSummary(); break;
                    case 8:
                        System.out.println("Thank you!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }

    
private static void addEmployee() {
    try {
        System.out.print("Employee ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (id <= 0) {
            System.out.println("Employee ID must be positive.");
            return;
        }

        if (payroll.findEmployee(id) != null) {
            System.out.println("ID already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Designation: ");
        String designation = sc.nextLine().trim();

        if (designation.isEmpty()) {
            System.out.println("Designation cannot be empty.");
            return;
        }

        System.out.print("Basic salary: ");
        double salary = Double.parseDouble(sc.nextLine().trim());

        if (!Double.isFinite(salary) || salary < 0) {
            System.out.println("Salary must be a valid, non-negative number.");
            return;
        }

        System.out.println("1. Permanent  2. Contract");
        System.out.print("Employee type: ");
        int type = Integer.parseInt(sc.nextLine().trim());

        Employee e;

        if (type == 1) {
            e = new PermanentEmployee(id, name, designation, salary);
        } else if (type == 2) {
            e = new ContractEmployee(id, name, designation, salary);
        } else {
            System.out.println("Invalid employee type. Enter 1 or 2.");
            return;
        }

        System.out.println(payroll.addEmployee(e)
                ? "Employee added successfully."
                : "Could not add employee. Check the ID or database.");

    } catch (NumberFormatException ex) {
        System.out.println("Invalid input. Enter a whole number for ID "
                + "and employee type, and a valid number for salary.");
    }
}

    private static void displayEmployees() {
        if (payroll.getEmployees().isEmpty()) {
            System.out.println("No employee records.");
            return;
        }

        for (Employee e : payroll.getEmployees()) {
            e.displayDetails();
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Employee e = payroll.findEmployee(id);

        if (e == null) {
            System.out.println("Employee not found.");
        } else {
            e.displayDetails();
        }
    }

    
private static void updateEmployee() {
    try {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (id <= 0) {
            System.out.println("Employee ID must be positive.");
            return;
        }

        if (payroll.findEmployee(id) == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("1. Name  2. Designation  3. Basic Salary");
        System.out.print("Select field: ");
        int option = Integer.parseInt(sc.nextLine().trim());

        boolean updated;

        switch (option) {
            case 1:
                System.out.print("New name: ");
                String name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                    return;
                }

                updated = payroll.updateName(id, name);
                break;

            case 2:
                System.out.print("New designation: ");
                String designation = sc.nextLine().trim();

                if (designation.isEmpty()) {
                    System.out.println("Designation cannot be empty.");
                    return;
                }

                updated = payroll.updateDesignation(id, designation);
                break;

            case 3:
                System.out.print("New salary: ");
                double salary =
                        Double.parseDouble(sc.nextLine().trim());

                if (!Double.isFinite(salary) || salary < 0) {
                    System.out.println(
                            "Salary must be a valid, non-negative number.");
                    return;
                }

                updated = payroll.updateSalary(id, salary);
                break;

            default:
                System.out.println("Invalid option. Choose 1, 2, or 3.");
                return;
        }

        System.out.println(updated
                ? "Updated successfully."
                : "Update failed. Check the database connection.");

    } catch (NumberFormatException ex) {
        System.out.println(
                "Invalid input. Enter a valid numeric ID, option, or salary.");
    }
}

    private static void deleteEmployee() {
        System.out.print("Employee ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.println(payroll.deleteEmployee(id)
                ? "Employee deleted."
                : "Employee not found.");
    }

    
private static void salarySlip() {
    try {
        System.out.print("Employee ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Employee e = payroll.findEmployee(id);

        if (e == null) {
            System.out.println("Employee not found.");
            return;
        }

        double basic = e.getBasicSalary();
        double allowances = e.calculateAllowances();
        double deductions = e.calculateDeductions();
        double gross = e.calculateGrossSalary();
        double net = e.calculateNetSalary();

        System.out.println();
        System.out.println("================================================");
        System.out.println("             EMPLOYEE PAYROLL SYSTEM");
        System.out.println("                    SALARY SLIP");
        System.out.println("================================================");
        System.out.printf("%-20s: %d%n", "Employee ID", e.getEmployeeId());
        System.out.printf("%-20s: %s%n", "Employee Name", e.getName());
        System.out.printf("%-20s: %s%n", "Designation", e.getDesignation());
        System.out.printf("%-20s: %s%n", "Employment Type", e.getEmployeeType());
        System.out.println("------------------------------------------------");
        System.out.printf("%-30s Rs. %10.2f%n", "Basic Salary", basic);
        System.out.printf("%-30s Rs. %10.2f%n", "Allowances", allowances);
        System.out.printf("%-30s Rs. %10.2f%n", "Gross Salary", gross);
        System.out.printf("%-30s Rs. %10.2f%n", "Deductions", deductions);
        System.out.println("------------------------------------------------");
        System.out.printf("%-30s Rs. %10.2f%n", "NET SALARY", net);
        System.out.println("================================================");
        System.out.println("           Thank you for your service!");
        System.out.println("================================================");

    } catch (NumberFormatException ex) {
        System.out.println("Invalid input. Enter a valid employee ID.");
    }
}


    private static void payrollSummary() {
        if (payroll.getEmployees().isEmpty()) {
            System.out.println("No employee records.");
            return;
        }

        System.out.println("Total employees: "
                + payroll.getEmployees().size());
        System.out.printf("Total net payroll: Rs. %.2f%n",
                payroll.getTotalPayroll());

        Employee highest = payroll.getHighestPaidEmployee();

        System.out.println("Highest-paid employee: "
                + highest.getName());
        System.out.printf("Net salary: Rs. %.2f%n",
                highest.calculateNetSalary());
    }
}
