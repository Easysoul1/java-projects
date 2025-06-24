// Abstract base class representing a generic Employee
abstract class Employee {
    private String name;
    private int employeeId;

    // Constructor
    public Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for employeeId
    public int getEmployeeId() {
        return employeeId;
    }

    // Setter for employeeId (bonus: prevent negative IDs)
    public void setEmployeeId(int employeeId) {
        if (employeeId < 0) {
            throw new IllegalArgumentException("Employee ID cannot be negative.");
        }
        this.employeeId = employeeId;
    }

    // Abstract method to calculate salary (must be implemented by subclasses)
    public abstract double calculateSalary();
}

// Subclass for full-time employees
class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int employeeId, double monthlySalary) {
        super(name, employeeId);
        setMonthlySalary(monthlySalary); // Use setter for validation
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    // Setter with validation (bonus)
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary < 0) {
            throw new IllegalArgumentException("Monthly salary cannot be negative.");
        }
        this.monthlySalary = monthlySalary;
    }

    // Implementation of abstract method
    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

// Subclass for part-time employees
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int employeeId, int hoursWorked, double hourlyRate) {
        super(name, employeeId);
        setHoursWorked(hoursWorked); // Use setter for validation
        setHourlyRate(hourlyRate);   // Use setter for validation
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    // Setter with validation (bonus)
    public void setHoursWorked(int hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative.");
        }
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    // Setter with validation (bonus)
    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative.");
        }
        this.hourlyRate = hourlyRate;
    }

    // Implementation of abstract method
    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

// Main class to demonstrate the payroll system
public class EmployeePayRoll {
    public static void main(String[] args) {
        // Create an array of Employee references (demonstrates polymorphism)
        Employee[] employees = new Employee[3];
        employees[0] = new FullTimeEmployee("Oladipupo", 101, 5000.0);
        employees[1] = new PartTimeEmployee("Gomez", 102, 80, 20.0);
        employees[2] = new FullTimeEmployee("Charlie", 103, 7000.0);

        // Calculate and display salaries for all employees
        for (Employee emp : employees) {
            System.out.println("Employee: " + emp.getName() +
                               " (ID: " + emp.getEmployeeId() + ")" +
                               " - Salary: $" + emp.calculateSalary());
        }
    }
}
