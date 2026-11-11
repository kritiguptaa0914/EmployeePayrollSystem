
public class PermanentEmployee extends Employee {

    private static final long serialVersionUID = 1L;

    public PermanentEmployee(int id, String name,
                             String designation, double salary) {
        super(id, name, designation, salary);
    }

    @Override
    public double calculateAllowances() {
        return getBasicSalary() * 0.35;
    }

    @Override
    public double calculateDeductions() {
        return getBasicSalary() * 0.08;
    }

    @Override
    public String getEmployeeType() {
        return "Permanent";
    }
}
