import java.util.*;

class Course {
    String code, title, description, schedule;
    int capacity;
    List<Student> registeredStudents = new ArrayList<>();

    Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    boolean hasSlot() {
        return registeredStudents.size() < capacity;
    }

    void registerStudent(Student s) {
        if (hasSlot()) {
            registeredStudents.add(s);
        }
    }

    void removeStudent(Student s) {
        registeredStudents.remove(s);
    }

    int availableSlots() {
        return capacity - registeredStudents.size();
    }

    public String toString() {
        return code + ": " + title + " (" + availableSlots() + "/" + capacity + " slots available)";
    }
}

class Student {
    String id, name;
    List<Course> registeredCourses = new ArrayList<>();

    Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    void registerCourse(Course c) {
        if (!registeredCourses.contains(c) && c.hasSlot()) {
            registeredCourses.add(c);
            c.registerStudent(this);
            System.out.println("Registered for course: " + c.title);
        } else {
            System.out.println("Cannot register: Course full or already registered.");
        }
    }

    void dropCourse(Course c) {
        if (registeredCourses.contains(c)) {
            registeredCourses.remove(c);
            c.removeStudent(this);
            System.out.println("Dropped course: " + c.title);
        } else {
            System.out.println("Not registered in course.");
        }
    }

    void listCourses() {
        System.out.println("Courses registered by " + name + ":");
        for (Course c : registeredCourses) {
            System.out.println("- " + c.title);
        }
    }
}

public class CourseRegistrationSystem {
    static List<Course> courses = new ArrayList<>();
    static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        // Sample Data
        courses.add(new Course("CS101", "Intro to CS", "Basics of programming", 2, "Mon 10AM"));
        courses.add(new Course("MAT201", "Calculus", "Math fundamentals", 3, "Tue 2PM"));

        students.add(new Student("S001", "Alice"));
        students.add(new Student("S002", "Bob"));

        // Simulate interaction
        Student alice = students.get(0);
        Course cs101 = courses.get(0);

        // List courses
        System.out.println("Available Courses:");
        for (Course c : courses) {
            System.out.println(c);
        }

        // Register course
        alice.registerCourse(cs101);
        alice.registerCourse(cs101); // Duplicate try

        // Drop course
        alice.dropCourse(cs101);

        // Final listing
        alice.listCourses();
    }
}
