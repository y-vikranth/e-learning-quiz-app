package model;

/**
 * Represents a student user in the quiz system.
 * Inherits common user attributes and behavior from the abstract User class.
 */
public class Student extends User {

    public Student() {
        // Default constructor for JSON deserialization
        super();
    }

    public Student(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
