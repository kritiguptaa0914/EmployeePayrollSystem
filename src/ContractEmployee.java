
public class ContractEmployee extends Employee {

    private static final long serialVersionUID = 1L;

    public ContractEmployee(int id, String name,
                            String designation, double salary) {
        super(id, name, designation, salary);
    }

    @Override
    public double calculateAllowances() {
        return getBasicSalary() * 0.15;
    }

    @Override
    public double calculateDeductions() {
        return getBasicSalary() * 0.05;
    }

    @Override
    public String getEmployeeType() {
        return "Contract";
    }
}

