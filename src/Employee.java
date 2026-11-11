
import java.io.Serializable;

public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int employeeId;
    private String name;
    private String designation;
    private double basicSalary;

    public Employee(int employeeId, String name,
                    String designation, double basicSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
        this.basicSalary = basicSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public abstract double calculateAllowances();

    public abstract double calculateDeductions();

    public double calculateGrossSalary() {
        return basicSalary + calculateAllowances();
    }

    public double calculateNetSalary() {
        return calculateGrossSalary() - calculateDeductions();
    }

    public abstract String getEmployeeType();

    public void displayDetails() {
        System.out.println("\n--------------------------------");
        System.out.println("       EMPLOYEE PAYSLIP");
        System.out.println("--------------------------------");
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Name        : " + name);
        System.out.println("Designation : " + designation);
        System.out.println("Category    : " + getEmployeeType());
        System.out.printf("Basic Salary: Rs. %.2f%n", basicSalary);
        System.out.printf("Allowances  : Rs. %.2f%n",
                          calculateAllowances());
        System.out.printf("Gross Salary: Rs. %.2f%n",
                          calculateGrossSalary());
        System.out.printf("Deductions  : Rs. %.2f%n",
                          calculateDeductions());
        System.out.printf("NET SALARY  : Rs. %.2f%n",
                          calculateNetSalary());
        System.out.println("--------------------------------");
    }
}
