// Import necessary packages
import java.util.ArrayList;

// Course class
class Course {
    private String name;  // Name of the course
    private String code; // Unique code for the course
    private int unitLoad; // Number of units for the course

    // Constructor
    public Course(String name, String code, int unitLoad) {
        this.name = name; // Initialize course name
        if (unitLoad <= 0) {
            throw new IllegalArgumentException("Unit load must be greater than zero.");
        } 
        else {
                this.code = code; // Initialize course code
                this.unitLoad = unitLoad; // Initialize unit load
        }
    }

    // Getters
    public String getName() {
        return name; // Get course name
    }

    public String getCode() {
        return code; // Get course code
    }

    public int getUnitLoad() {
        return unitLoad; // Get unit load
    }

    // toString method for easy display
    @Override
    public String toString() {
        return code + " - " + name + " (" + unitLoad + " units)"; // Format course details
    }
}

// Student class
class Student {
    private String name; // Name of the student
    private String studentId; // Unique ID for the student

    // Constructor chaining
    public Student(String name) {
        this(name, "N/A"); // Default student ID if not provided
        this.studentId = name; // Use name as default ID
    }

    public Student(String name, String studentId) {
        this.name = name; // Initialize student name
        if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        else{
              this.studentId = studentId; // Initialize student ID
        }
    }

    // Getters
    public String getName() {
        return name; // Get student name
    }

    public String getStudentId() {
        return studentId; // Get student ID
    }

    // toString method for easy display
    @Override
    public String toString() {
        return "Student Name: " + name + "\nStudent ID: " + studentId; // Format student details
    }
}

// Registration class (composition: Student "has" Registration, Registration "has" Courses)
class Registration {
    private Student student; // The student associated with this registration
    private ArrayList<Course> registeredCourses; // List of courses registered by the student

    // Constructor
    public Registration(Student student) {
        this.student = student; // Initialize student
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        else{
                this.registeredCourses = new ArrayList<>(); // Initialize course list
        }
    }

    // Add a course to the registration
    public void addCourse(Course course) {
        registeredCourses.add(course); // Add course to the list
    }

    // Compute total unit load
    public int getTotalUnitLoad() {
        int total = 0; // Initialize total unit load
        for (Course c : registeredCourses) {
            total += c.getUnitLoad();
        } // Sum up the unit loads of all registered courses
        return total; // Return total unit load
    }

    // Display summary (student info, courses, total unit load)
    public void displaySummary() {
        System.out.println("=== Registration Summary ==="); // Start of summary
        System.out.println("Student Information:"); // Display student information
        System.out.println(student); 
        System.out.println("Registered Courses:"); // Display registered courses
       // If no courses are registered, display a message
        if (registeredCourses.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Course c : registeredCourses) {
                System.out.println("  " + c);
            }
        }
        System.out.println("Total Unit Load: " + getTotalUnitLoad()); // Display total unit load
        System.out.println("==========================="); // End of summary
    }
}

// Main class to demonstrate functionality
public class SchoolManagementSystem {
    public static void main(String[] args) {
        // Create some courses
        Course math = new Course("Introduction to Programming ", "CSC 235", 3);
        Course physics = new Course("Web programming", "CSC 293", 4);
        Course programming = new Course("Data Structure and Algorithm", "CSC 236", 3);

        // Create a student
        Student student = new Student("Adejumo Ezekiel Adedayo", "E042011"); // Initialize student with name and ID

        // Register student for courses
        Registration registration = new Registration(student); // Create a registration instance
        registration.addCourse(math); // Add courses to registration
        registration.addCourse(physics); // Add another course
        registration.addCourse(programming); // Add another course

        // Display registration summary
        registration.displaySummary();
    }
}