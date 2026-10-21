package service;
import java.util.Comparator;
import java.util.List;
import model.Course;
import model.StudentSchedule;
public class CoursePlannerService {
    private static final int MAX_CREDITS = 18;
    public void addCourse(StudentSchedule schedule, Course course) {
        for (Course existing : schedule.getCourses()) {
            if (existing.getCode().equalsIgnoreCase(course.getCode()))
                throw new IllegalStateException("A course with code " + course.getCode() + " already exists.");
        }
        if (schedule.getTotalCredits() + course.getCredits() > MAX_CREDITS)
            throw new IllegalStateException("Adding this course would exceed the " + MAX_CREDITS + "-credit limit.");
        for (Course existing : schedule.getCourses()) {
            if (existing.getTimeSlot().conflictsWith(course.getTimeSlot()))
                throw new IllegalStateException("Time conflict with " + existing.getCode() + " (" + existing.getName() + ").");
        }
        schedule.addCourse(course);
    }
    public boolean removeCourse(StudentSchedule schedule, String code) { return schedule.removeCourse(code); }
    public List<Course> getSortedCourses(StudentSchedule schedule) {
        return schedule.getCourses().stream()
            .sorted(Comparator.comparing((Course c) -> c.getTimeSlot().getDay().getValue())
            .thenComparing(c -> c.getTimeSlot().getStartTime())).toList();
    }
}
