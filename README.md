Employee Payroll Management System

A Java-based Employee Payroll Management System developed using Java Swing, JDBC, and MySQL. The application simplifies employee record management and automates salary calculations through an interactive desktop interface.

Project Overview

The Employee Payroll Management System is designed to manage employee information, calculate salaries, and maintain payroll records efficiently. It provides a graphical user interface that allows users to perform payroll-related operations with ease.

The system uses Java for application development and MySQL as the database to store employee information persistently.

Objectives

- Automate employee salary calculations.
- Manage employee records efficiently.
- Reduce manual payroll calculation errors.
- Store and retrieve employee information using MySQL.
- Provide a user-friendly graphical interface.
- Generate salary-related information for employees.

Technologies Used

Technology| Purpose
Java 22| Core application development
Java Swing| Graphical User Interface
JDBC| Database connectivity
MySQL| Database management
JFreeChart| Data visualization and charts
VS Code| Development environment
Git & GitHub| Version control and project hosting

Key Features

- Employee Management: Add, view, update, and manage employee records.
- Payroll Calculation: Automatically calculate allowances, deductions, gross salary, and net salary.
- Employee Categories: Supports Permanent and Contract employees.
- Database Integration: Stores employee records in a MySQL database.
- Data Persistence: Employee records remain available even after restarting the application.
- Interactive Dashboard: Provides a graphical dashboard with navigation options.
- Data Visualization: Displays payroll-related information using charts.

Salary Calculation Logic

The system uses different allowance and deduction rates for different employee categories.

Employee Type| Allowance| Deduction
Permanent| 35%| 8%
Contract| 15%| 5%

Formulas:

- Allowance = Basic Salary × Allowance Rate
- Gross Salary = Basic Salary + Allowance
- Deduction = Basic Salary × Deduction Rate
- Net Salary = Gross Salary − Deduction

System Requirements

- Java Development Kit (JDK) 22 or compatible version
- MySQL Server
- MySQL Connector/J
- JFreeChart library
- Visual Studio Code or another Java IDE

Installation and Setup

1. Clone the repository

git clone https://github.com/kritiguptaa0914/EmployeePayrollSystem.git

2. Open the project folder

cd EmployeePayrollSystem

3. Configure the database

- Install and start MySQL Server.
- Create the database named "employee_payroll".
- Configure the database connection details in "DatabaseConnection.java".
- Ensure the required employee table exists with the columns expected by the application.

4. Add the required libraries

Ensure the MySQL Connector/J and JFreeChart JAR files are available in the project's "lib" folder.

5. Compile the project

Run the following command from the project root directory in PowerShell:

javac -cp "src;lib\*" -d src src\*.java DatabaseConnection.java

6. Run the application

java -cp "src;lib\*" PayrollGUI


1. Dashboard


2. Employee Management


3. Payroll Management

4. Data Visualization


Future Enhancements

- Employee login and authentication.
- PDF salary slip generation.
- Advanced payroll reports.
- Attendance and leave management.
- Improved security and database configuration.
- Deployment as a downloadable desktop application.

Learning Outcomes

Through this project, we gained practical experience in:

- Object-Oriented Programming using Java.
- Java Swing GUI development.
- Database connectivity using JDBC.
- MySQL database management.
- CRUD operations.
- Payroll calculation logic.
- Software development and version control using Git and GitHub.

Contributor

Name| University Roll Number
Kriti Gupta| 27600124129


Project Information

Project Title: Employee Payroll Management System

Department: Computer Science and Engineering

Institution: Budge Budge Institute of Technology (BBIT)

Project Type: Academic Mini Project

---

Developed with Java, MySQL, and dedication.
