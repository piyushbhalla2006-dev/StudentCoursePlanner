package model;
import java.util.Objects;
public class Course {
    private final String code, name;
    private final int credits;
    private final TimeSlot timeSlot;
    public Course(String code, String name, int credits, TimeSlot timeSlot) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Course code cannot be empty.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Course name cannot be empty.");
        if (credits <= 0) throw new IllegalArgumentException("Credits must be positive.");
        this.code = code.trim().toUpperCase();
        this.name = name.trim();
        this.credits = credits;
        this.timeSlot = Objects.requireNonNull(timeSlot, "Time slot cannot be null.");
    }
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public TimeSlot getTimeSlot() { return timeSlot; }
}
