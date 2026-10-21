package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class StudentSchedule {
    private final List<Course> courses = new ArrayList<>();
    public List<Course> getCourses() { return Collections.unmodifiableList(courses); }
    public void addCourse(Course course) { courses.add(course); }
    public boolean removeCourse(String code) {
        return courses.removeIf(c -> c.getCode().equalsIgnoreCase(code));
    }
    public int getTotalCredits() {
        int total = 0;
        for (Course course : courses) total += course.getCredits();
        return total;
    }
}
