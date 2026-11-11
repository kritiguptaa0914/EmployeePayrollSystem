
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PayrollGUI extends JFrame {

    // Dark theme colours
    private static final Color BACKGROUND = new Color(11, 17, 32);
    private static final Color PANEL = new Color(21, 31, 50);
    private static final Color BLUE = new Color(22, 119, 232);
    private static final Color BLUE_HOVER = new Color(39, 143, 255);
    private static final Color TEXT = new Color(241, 245, 249);
    private static final Color MUTED = new Color(148, 163, 184);
    private static final Color BORDER = new Color(44, 66, 99);

    
public PayrollGUI() {
    setTitle("Employee Payroll System");
    setSize(1050, 780);
    setMinimumSize(new Dimension(900, 700));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    getContentPane().setBackground(BACKGROUND);
    setLayout(new BorderLayout(0, 0));

    // ===== HEADER =====
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(BACKGROUND);
    header.setBorder(BorderFactory.createEmptyBorder(24, 32, 18, 32));

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
    titlePanel.setBackground(BACKGROUND);

    JLabel heading = new JLabel("Employee Payroll System");
    heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
    heading.setForeground(TEXT);

    JLabel subtitle = new JLabel(
        "EMPLOYEE MANAGEMENT  /  PAYROLL DASHBOARD"
    );
    subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    subtitle.setForeground(MUTED);
    subtitle.setBorder(BorderFactory.createEmptyBorder(6, 2, 0, 0));

    titlePanel.add(heading);
    titlePanel.add(subtitle);

    JLabel status = new JLabel("● SYSTEM READY");
    status.setFont(new Font("Segoe UI", Font.BOLD, 11));
    status.setForeground(new Color(74, 222, 128));

    header.add(titlePanel, BorderLayout.WEST);
    header.add(status, BorderLayout.EAST);
    add(header, BorderLayout.NORTH);

    // ===== MAIN CONTENT =====
    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
    main.setBackground(BACKGROUND);
    main.setBorder(BorderFactory.createEmptyBorder(0, 32, 20, 32));

    // ===== STATISTICS =====
    java.util.List<Employee> employees =
        new PayrollManager().getEmployees();

    double totalPayroll = 0;
    Employee highestPaid = null;

    for (Employee employee : employees) {
        double net = employee.calculateNetSalary();
        totalPayroll += net;

        if (highestPaid == null ||
            net > highestPaid.calculateNetSalary()) {
            highestPaid = employee;
        }
    }

    JPanel stats = new JPanel(new GridLayout(1, 3, 16, 0));
    stats.setBackground(BACKGROUND);
    stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));
    stats.setAlignmentX(Component.LEFT_ALIGNMENT);

    stats.add(createStatCard(
        "TOTAL EMPLOYEES",
        String.valueOf(employees.size()),
        "Registered employee records"
    ));

    stats.add(createStatCard(
        "TOTAL NET PAYROLL",
        String.format("₹%,.2f", totalPayroll),
        "Combined net salary"
    ));

    stats.add(createStatCard(
        "HIGHEST NET SALARY",
        highestPaid == null
            ? "₹0.00"
            : String.format(
                "₹%,.2f", highestPaid.calculateNetSalary()
            ),
        highestPaid == null
            ? "No employees registered"
            : highestPaid.getName()
    ));

    main.add(stats);
    main.add(Box.createVerticalStrut(28));

    // ===== QUICK ACTIONS =====
    JLabel actionsTitle = new JLabel("Quick Actions");
    actionsTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
    actionsTitle.setForeground(TEXT);
    actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel actionsSubtitle = new JLabel(
        "Manage employee records and payroll operations"
    );
    actionsSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    actionsSubtitle.setForeground(MUTED);
    actionsSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

    main.add(actionsTitle);
    main.add(Box.createVerticalStrut(5));
    main.add(actionsSubtitle);
    main.add(Box.createVerticalStrut(16));

    JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 14, 14));
    buttonPanel.setBackground(BACKGROUND);
    buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    String[] buttonNames = {
        " Add Employee",
        " View Employees",
        " Search Employee",
        " Update Employee",
        " Delete Employee",
        " Generate Salary Slip",
        " Payroll Summary"
    };

    for (int i = 0; i < buttonNames.length; i++) {
        final String action = buttonNames[i].trim();
        JButton button = new JButton(buttonNames[i]);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(TEXT);
        button.setBackground(Color.BLACK);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(17, 20, 17, 12)
        ));

        button.getModel().addChangeListener(event -> {
            ButtonModel model = button.getModel();

            if (model.isPressed() && model.isArmed()) {
                button.setBackground(BLUE_HOVER);
            } else if (model.isRollover()) {
                button.setBackground(BLUE);
            } else {
                button.setBackground(Color.BLACK);
            }
        });

        button.addActionListener(event -> {
            
            switch (action) {
                case "Add Employee":
                    addEmployeeGUI();
                    break;
                case "View Employees":
                    showEmployees();
                    break;
                case "Search Employee":
                    searchEmployeeGUI();
                    break;
                case "Update Employee":
                    updateEmployeeGUI();
                    break;
                case "Delete Employee":
                    deleteEmployeeGUI();
                    break;
                case "Generate Salary Slip":
                    generateSalarySlipGUI();
                    break;
                case "Payroll Summary":
                    showPayrollSummaryGUI();
                    break;
            }
        });
        
   


        buttonPanel.add(button);
    }

    main.add(buttonPanel);
    main.add(Box.createVerticalStrut(24));

JLabel chartTitle = new JLabel("Salary Analytics");
chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
chartTitle.setForeground(TEXT);
chartTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

main.add(chartTitle);
main.add(Box.createVerticalStrut(12));
main.add(createSalaryChart());
main.add(Box.createVerticalStrut(20));

JLabel typeChartTitle = new JLabel("Employee Type Distribution");
typeChartTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
typeChartTitle.setForeground(TEXT);
typeChartTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

main.add(typeChartTitle);
main.add(Box.createVerticalStrut(12));
main.add(createEmployeeTypePieChart());
    main.add(Box.createVerticalGlue());

    JLabel footer = new JLabel(
        "JAVA SWING     •     MYSQL DATABASE"
    );
    footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    footer.setForeground(MUTED);
    footer.setAlignmentX(Component.LEFT_ALIGNMENT);
    footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

    main.add(footer);

    JScrollPane dashboardScroll = new JScrollPane(main);

dashboardScroll.setBorder(null);
dashboardScroll.setBackground(BACKGROUND);
dashboardScroll.getViewport().setBackground(BACKGROUND);

dashboardScroll.setVerticalScrollBarPolicy(
    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
);

dashboardScroll.setHorizontalScrollBarPolicy(
    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
);

dashboardScroll.getVerticalScrollBar().setUnitIncrement(20);

add(dashboardScroll, BorderLayout.CENTER);
}

// Reusable dashboard statistics card
private JPanel createStatCard(
    String title, String value, String description
) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(PANEL);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER, 1),
        BorderFactory.createEmptyBorder(14, 16, 14, 16)
    ));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
    titleLabel.setForeground(MUTED);

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 21));
    valueLabel.setForeground(TEXT);

    JLabel descriptionLabel = new JLabel(description);
    descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    descriptionLabel.setForeground(BLUE_HOVER);

    card.add(titleLabel);
    card.add(Box.createVerticalStrut(8));
    card.add(valueLabel);
    card.add(Box.createVerticalStrut(5));
    card.add(descriptionLabel);

    return card;
}


    // View Employees
    private void showEmployees() {
        PayrollManager payroll = new PayrollManager();

        String[] columns = {
            "ID", "Name", "Designation",
            "Type", "Basic Salary", "Net Salary"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(
                    int row, int column
                ) {
                    return false;
                }
            };

        for (Employee employee : payroll.getEmployees()) {
            model.addRow(new Object[] {
                employee.getEmployeeId(),
                employee.getName(),
                employee.getDesignation(),
                employee.getEmployeeType(),
                String.format(
                    "₹%,.2f", employee.getBasicSalary()
                ),
                String.format(
                    "₹%,.2f", employee.calculateNetSalary()
                )
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );
        table.setBackground(PANEL);
        table.setForeground(TEXT);
        table.setGridColor(BORDER);
        table.setSelectionBackground(BLUE);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(BLUE);
        table.getTableHeader().setForeground(Color.WHITE);

        // Search and employee-type filter
        JTextField searchField = new JTextField(18);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        String[] types = {
            "All Types", "Permanent", "Contract"
        };

        JComboBox<String> typeFilter = new JComboBox<>(types);
        typeFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        TableRowSorter<DefaultTableModel> sorter =
            new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        Runnable applyFilters = () -> {
            java.util.List<RowFilter<Object, Object>> filters =
                new java.util.ArrayList<>();

            String search = searchField.getText().trim();

            if (!search.isEmpty()) {
                filters.add(RowFilter.regexFilter(
                    "(?i)" + java.util.regex.Pattern.quote(search),
                    0, 1, 2
                ));
            }

            String selectedType =
                (String) typeFilter.getSelectedItem();

            if (selectedType != null &&
                !selectedType.equals("All Types")) {
                filters.add(RowFilter.regexFilter(
                    "^" + java.util.regex.Pattern.quote(selectedType) + "$",
                    3
                ));
            }

            sorter.setRowFilter(
                filters.isEmpty()
                    ? null
                    : RowFilter.andFilter(filters)
            );
        };

        searchField.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(
                    javax.swing.event.DocumentEvent e
                ) {
                    applyFilters.run();
                }

                @Override
                public void removeUpdate(
                    javax.swing.event.DocumentEvent e
                ) {
                    applyFilters.run();
                }

                @Override
                public void changedUpdate(
                    javax.swing.event.DocumentEvent e
                ) {
                    applyFilters.run();
                }
            }
        );

        typeFilter.addActionListener(e -> applyFilters.run());

        JPanel filterPanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 8)
        );
        filterPanel.setBackground(PANEL);
        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(searchField);
        filterPanel.add(new JLabel("Employee Type:"));
        filterPanel.add(typeFilter);

        filterPanel.getComponents();
        for (Component component : filterPanel.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(TEXT);
                component.setFont(
                    new Font("Segoe UI", Font.BOLD, 12)
                );
            }
        }

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(PANEL);
        content.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8)
        );
        content.add(filterPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(PANEL);
        content.add(scrollPane, BorderLayout.CENTER);

        content.setPreferredSize(new Dimension(850, 420));

        JOptionPane.showMessageDialog(
            this,
            content,
            "Employee Records",
            JOptionPane.PLAIN_MESSAGE
        );
    }


        // Add Employee
    private void addEmployeeGUI() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField designationField = new JTextField();
        JTextField salaryField = new JTextField();

        String[] types = {"Permanent", "Contract"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        JPanel panel = createFormPanel();

        addFormRow(panel, "Employee ID:", idField);
        addFormRow(panel, "Name:", nameField);
        addFormRow(panel, "Designation:", designationField);
        addFormRow(panel, "Basic Salary:", salaryField);
        addFormRow(panel, "Employee Type:", typeBox);

        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Add New Employee",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String designation =
                designationField.getText().trim();
            double salary =
                Double.parseDouble(salaryField.getText().trim());

            if (id <= 0 || name.isEmpty()
                    || designation.isEmpty()
                    || !Double.isFinite(salary)
                    || salary < 0) {
                showError(
                    "Enter a positive ID, non-empty name and "
                    + "designation, and a valid non-negative salary."
                );
                return;
            }

            Employee employee;

            if ("Permanent".equals(typeBox.getSelectedItem())) {
                employee = new PermanentEmployee(
                    id, name, designation, salary
                );
            } else {
                employee = new ContractEmployee(
                    id, name, designation, salary
                );
            }

            PayrollManager payroll = new PayrollManager();

            if (payroll.addEmployee(employee)) {
                showSuccess("Employee added successfully!");
            } else {
                showError(
                    "Could not add employee. The ID may already exist."
                );
            }

        } catch (NumberFormatException ex) {
            showError(
                "Employee ID must be a whole number and "
                + "salary must be numeric."
            );
        }
    }

    // Search Employee
    private void searchEmployeeGUI() {
        String input = JOptionPane.showInputDialog(
            this, "Enter Employee ID:"
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            if (id <= 0) {
                showError("Employee ID must be positive.");
                return;
            }

            PayrollManager payroll = new PayrollManager();
            Employee e = payroll.findEmployee(id);

            if (e == null) {
                showError("Employee not found.");
                return;
            }

            String details = String.format(
                "EMPLOYEE DETAILS\n\n"
                + "Employee ID: %d\n"
                + "Name: %s\n"
                + "Designation: %s\n"
                + "Employment Type: %s\n\n"
                + "Basic Salary: Rs. %.2f\n"
                + "Allowances: Rs. %.2f\n"
                + "Deductions: Rs. %.2f\n"
                + "Net Salary: Rs. %.2f",
                e.getEmployeeId(),
                e.getName(),
                e.getDesignation(),
                e.getEmployeeType(),
                e.getBasicSalary(),
                e.calculateAllowances(),
                e.calculateDeductions(),
                e.calculateNetSalary()
            );

            showInfo(details, "Employee Details");

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric employee ID.");
        }
    }

    // Update Employee
    private void updateEmployeeGUI() {
        String input = JOptionPane.showInputDialog(
            this, "Enter the Employee ID to update:"
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            if (id <= 0) {
                showError("Employee ID must be positive.");
                return;
            }

            PayrollManager payroll = new PayrollManager();
            Employee employee = payroll.findEmployee(id);

            if (employee == null) {
                showError("Employee not found.");
                return;
            }

            String[] options = {
                "Name", "Designation", "Basic Salary"
            };

            String choice = (String) JOptionPane.showInputDialog(
                this,
                "What would you like to update?",
                "Update Employee",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == null) {
                return;
            }

            String currentValue;

            switch (choice) {
                case "Name":
                    currentValue = employee.getName();
                    break;
                case "Designation":
                    currentValue = employee.getDesignation();
                    break;
                default:
                    currentValue = String.valueOf(
                        employee.getBasicSalary()
                    );
            }

            String newValue = JOptionPane.showInputDialog(
                this,
                "Enter the new " + choice + ":",
                currentValue
            );

            if (newValue == null) {
                return;
            }

            newValue = newValue.trim();

            if (newValue.isEmpty()) {
                showError("Value cannot be empty.");
                return;
            }

            boolean success;

            switch (choice) {
                case "Name":
                    success = payroll.updateName(id, newValue);
                    break;

                case "Designation":
                    success = payroll.updateDesignation(
                        id, newValue
                    );
                    break;

                default:
                    double salary = Double.parseDouble(newValue);

                    if (salary < 0 || !Double.isFinite(salary)) {
                        showError(
                            "Salary must be a valid, "
                            + "non-negative number."
                        );
                        return;
                    }

                    success = payroll.updateSalary(id, salary);
            }

            if (success) {
                showSuccess("Employee updated successfully!");
            } else {
                showError("Update failed. Please try again.");
            }

        } catch (NumberFormatException ex) {
            showError(
                "Please enter a valid numeric ID or salary."
            );
        }
    }

    // Delete Employee
    private void deleteEmployeeGUI() {
        String input = JOptionPane.showInputDialog(
            this, "Enter the Employee ID to delete:"
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            if (id <= 0) {
                showError("Employee ID must be positive.");
                return;
            }

            PayrollManager payroll = new PayrollManager();
            Employee employee = payroll.findEmployee(id);

            if (employee == null) {
                showError("Employee not found.");
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this employee?\n\n"
                    + "ID: " + employee.getEmployeeId() + "\n"
                    + "Name: " + employee.getName(),
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success = payroll.deleteEmployee(id);

            if (success) {
                showSuccess("Employee deleted successfully!");
            } else {
                showError("Unable to delete employee.");
            }

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric employee ID.");
        }
    }

    // Generate Salary Slip
    private void generateSalarySlipGUI() {
        String input = JOptionPane.showInputDialog(
            this, "Enter Employee ID to generate salary slip:"
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            if (id <= 0) {
                showError("Employee ID must be positive.");
                return;
            }

            PayrollManager payroll = new PayrollManager();
            Employee employee = payroll.findEmployee(id);

            if (employee == null) {
                showError("Employee not found.");
                return;
            }

            double basic = employee.getBasicSalary();
            double allowances = employee.calculateAllowances();
            double deductions = employee.calculateDeductions();
            double gross = employee.calculateGrossSalary();
            double net = employee.calculateNetSalary();

            String slip = String.format(
                "========================================\n"
                + "       EMPLOYEE PAYROLL SYSTEM\n"
                + "                SALARY SLIP\n"
                + "========================================\n\n"
                + "Employee ID     : %d\n"
                + "Employee Name   : %s\n"
                + "Designation     : %s\n"
                + "Employment Type : %s\n\n"
                + "----------------------------------------\n"
                + "Basic Salary    : Rs. %.2f\n"
                + "Allowances      : Rs. %.2f\n"
                + "Gross Salary    : Rs. %.2f\n"
                + "Deductions      : Rs. %.2f\n"
                + "----------------------------------------\n"
                + "NET SALARY      : Rs. %.2f\n"
                + "========================================",
                employee.getEmployeeId(),
                employee.getName(),
                employee.getDesignation(),
                employee.getEmployeeType(),
                basic,
                allowances,
                gross,
                deductions,
                net
            );

            JTextArea textArea = new JTextArea(slip);
            textArea.setEditable(false);
            textArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
            );
            textArea.setBackground(PANEL);
            textArea.setForeground(TEXT);
            textArea.setCaretPosition(0);
            textArea.setMargin(
                new Insets(15, 15, 15, 15)
            );

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.getViewport().setBackground(PANEL);

            JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Employee Salary Slip",
                JOptionPane.PLAIN_MESSAGE
            );

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric employee ID.");
        }
    }

    // Payroll Summary
    private void showPayrollSummaryGUI() {
        PayrollManager payroll = new PayrollManager();
        java.util.List<Employee> employees = payroll.getEmployees();

        if (employees.isEmpty()) {
            showInfo(
                "No employee records available.",
                "Payroll Summary"
            );
            return;
        }

        double totalPayroll = 0;
        Employee highestPaid = null;

        for (Employee employee : employees) {
            double netSalary = employee.calculateNetSalary();
            totalPayroll += netSalary;

            if (highestPaid == null
                    || netSalary
                    > highestPaid.calculateNetSalary()) {
                highestPaid = employee;
            }
        }

        String summary = String.format(
            "PAYROLL SUMMARY\n\n"
            + "Total Employees: %d\n"
            + "Total Net Payroll: Rs. %.2f\n\n"
            + "HIGHEST-PAID EMPLOYEE\n"
            + "----------------------------------------\n"
            + "Employee ID: %d\n"
            + "Name: %s\n"
            + "Designation: %s\n"
            + "Net Salary: Rs. %.2f",
            employees.size(),
            totalPayroll,
            highestPaid.getEmployeeId(),
            highestPaid.getName(),
            highestPaid.getDesignation(),
            highestPaid.calculateNetSalary()
        );

        showInfo(summary, "Payroll Summary");
    }

    // Reusable dark form panel
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 12));
        panel.setBackground(PANEL);
        panel.setBorder(
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        );
        return panel;
    }

    private void addFormRow(
        JPanel panel, String labelText, JComponent field
    ) {
        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panel.add(label);
        panel.add(field);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Employee Payroll System",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showInfo(String message, String title) {
        JTextArea area = new JTextArea(message);
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBackground(PANEL);
        area.setForeground(TEXT);
        area.setMargin(new Insets(15, 15, 15, 15));
        area.setCaretPosition(0);

        JOptionPane.showMessageDialog(
            this,
            area,
            title,
            JOptionPane.PLAIN_MESSAGE
        );
    }

    
private JPanel createSalaryChart() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    PayrollManager payroll = new PayrollManager();

    for (Employee employee : payroll.getEmployees()) {
        dataset.addValue(
            employee.calculateNetSalary(),
            "Net Salary",
            employee.getName()
        );
    }

    JFreeChart chart = ChartFactory.createBarChart(
        "Employee Net Salary",
        "Employee",
        "Net Salary (₹)",
        dataset
    );

    chart.setBackgroundPaint(BACKGROUND);
    chart.getTitle().setPaint(TEXT);

    org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(PANEL);
    plot.setOutlinePaint(BORDER);
    plot.setRangeGridlinePaint(BORDER);

    plot.getDomainAxis().setLabelPaint(MUTED);
    plot.getDomainAxis().setTickLabelPaint(TEXT);
    plot.getRangeAxis().setLabelPaint(MUTED);
    plot.getRangeAxis().setTickLabelPaint(TEXT);

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setBackground(PANEL);
    chartPanel.setPreferredSize(new Dimension(850, 320));
chartPanel.setMinimumSize(new Dimension(600, 300));
chartPanel.setMaximumSize(
    new Dimension(Integer.MAX_VALUE, 320)
);

    JPanel wrapper = new JPanel(new BorderLayout());
wrapper.setBackground(PANEL);
wrapper.setBorder(BorderFactory.createLineBorder(BORDER));
wrapper.setPreferredSize(new Dimension(850, 330));
wrapper.setMinimumSize(new Dimension(600, 330));
wrapper.setMaximumSize(
    new Dimension(Integer.MAX_VALUE, 330)
);
wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
wrapper.add(chartPanel, BorderLayout.CENTER);

return wrapper;
}


private JPanel createEmployeeTypePieChart() {
    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

    int permanentCount = 0;
    int contractCount = 0;

    PayrollManager payroll = new PayrollManager();

    for (Employee employee : payroll.getEmployees()) {
        if ("Permanent".equalsIgnoreCase(employee.getEmployeeType())) {
            permanentCount++;
        } else if ("Contract".equalsIgnoreCase(employee.getEmployeeType())) {
            contractCount++;
        }
    }

    dataset.setValue("Permanent", permanentCount);
    dataset.setValue("Contract", contractCount);

    JFreeChart chart = ChartFactory.createPieChart(
        "Employee Type Distribution",
        dataset,
        true,
        true,
        false
    );

    chart.setBackgroundPaint(BACKGROUND);
    chart.getTitle().setPaint(TEXT);

    org.jfree.chart.plot.PiePlot plot =
        (org.jfree.chart.plot.PiePlot) chart.getPlot();

    plot.setBackgroundPaint(PANEL);
    plot.setOutlinePaint(BORDER);
    plot.setSectionPaint("Permanent", BLUE);
    plot.setSectionPaint("Contract", new Color(45, 190, 170));
    plot.setLabelBackgroundPaint(PANEL);
    plot.setLabelPaint(TEXT);
    plot.setLabelOutlinePaint(BORDER);
    plot.setLabelShadowPaint(null);
    plot.setNoDataMessage("No employee records available");
    plot.setNoDataMessagePaint(TEXT);

    chart.getLegend().setBackgroundPaint(PANEL);
    chart.getLegend().setItemPaint(TEXT);

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setBackground(PANEL);
    chartPanel.setPreferredSize(new Dimension(500, 320));

    JPanel wrapper = new JPanel(new BorderLayout());
    wrapper.setBackground(PANEL);
    wrapper.setBorder(BorderFactory.createLineBorder(BORDER));
    wrapper.setPreferredSize(new Dimension(850, 340));
    wrapper.setMinimumSize(new Dimension(600, 340));
    wrapper.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 340)
    );
    wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
    wrapper.add(chartPanel, BorderLayout.CENTER);

    return wrapper;
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PayrollGUI gui = new PayrollGUI();
            gui.setVisible(true);
        });
    }
}
