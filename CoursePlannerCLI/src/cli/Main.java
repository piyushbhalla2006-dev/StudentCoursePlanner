package cli;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import model.Course;
import model.StudentSchedule;
import model.TimeSlot;
import service.CoursePlannerService;
import storage.ScheduleStorage;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || isHelp(args[0])) { printHelp(); return; }
        ScheduleStorage storage = new ScheduleStorage(Path.of("data", "courses.txt"));
        StudentSchedule schedule;
        try { schedule = storage.load(); }
        catch (IOException e) {
            System.err.println("Could not load schedule: " + e.getMessage());
            System.err.println("Fix the data file before continuing.");
            return;
        }
        CoursePlannerService service = new CoursePlannerService();
        try {
            switch (args[0].toLowerCase()) {
                case "add" -> {
                    if (args.length != 7) { printAddUsage(); return; }
                    Course course = parseCourse(args);
                    service.addCourse(schedule, course);
                    storage.save(schedule);
                    System.out.println("Added " + course.getCode() + " — " + course.getName() + ".");
                }
                case "list" -> {
                    if (args.length != 1) { System.err.println("Usage: java -cp out cli.Main list"); return; }
                    listCourses(schedule, service);
                }
                case "remove" -> {
                    if (args.length != 2) { System.err.println("Usage: java -cp out cli.Main remove <course-code>"); return; }
                    if (!service.removeCourse(schedule, args[1])) {
                        System.out.println("No course found with code " + args[1].toUpperCase() + ".");
                        return;
                    }
                    storage.save(schedule);
                    System.out.println("Removed " + args[1].toUpperCase() + ".");
                }
                case "credits" -> {
                    if (args.length != 1) { System.err.println("Usage: java -cp out cli.Main credits"); return; }
                    System.out.println("Total credits: " + schedule.getTotalCredits() + "/18");
                }
                default -> { System.err.println("Unknown command: " + args[0]); printHelp(); }
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Could not save schedule: " + e.getMessage());
        }
    }
    private static Course parseCourse(String[] args) {
        String code = args[1], name = args[2];
        int credits;
        try { credits = Integer.parseInt(args[3]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Credits must be a whole number."); }
        DayOfWeek day;
        try { day = DayOfWeek.valueOf(args[4].toUpperCase()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("Use a full day name, such as MONDAY."); }
        try {
            LocalTime start = LocalTime.parse(args[5]), end = LocalTime.parse(args[6]);
            return new Course(code, name, credits, new TimeSlot(day, start, end));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Times must use HH:MM format, such as 09:30.");
        }
    }
    private static void listCourses(StudentSchedule schedule, CoursePlannerService service) {
        if (schedule.getCourses().isEmpty()) { System.out.println("No courses in your schedule yet."); return; }
        System.out.printf("%-12s %-24s %-8s %-12s %s%n", "CODE", "COURSE", "CREDITS", "DAY", "TIME");
        System.out.println("-----------------------------------------------------------------------");
        for (Course c : service.getSortedCourses(schedule)) {
            TimeSlot t = c.getTimeSlot();
            System.out.printf("%-12s %-24s %-8d %-12s %s-%s%n", c.getCode(), c.getName(),
                c.getCredits(), t.getDay(), t.getStartTime(), t.getEndTime());
        }
        System.out.println("\nTotal credits: " + schedule.getTotalCredits() + "/18");
    }
    private static boolean isHelp(String s) { return s.equalsIgnoreCase("help") || s.equals("-h") || s.equals("--help"); }
    private static void printAddUsage() {
        System.err.println("Usage: java -cp out cli.Main add <code> <name-in-quotes> <credits> <day> <start> <end>");
        System.err.println("Example: java -cp out cli.Main add EECS2030 \"Software Tools\" 3 MONDAY 10:00 11:30");
    }
    private static void printHelp() {
        System.out.println("""
            Course Planner CLI
            Commands:
              help
                Show this help message.
              add <code> <name-in-quotes> <credits> <day> <start> <end>
                Add a course after checking duplicates, credit limits, and time conflicts.
                Example: java -cp out cli.Main add EECS2030 "Software Tools" 3 MONDAY 10:00 11:30
              list
                List all courses, sorted by day and start time.
              remove <course-code>
                Remove a course by its code.
              credits
                Show total credits.
            Notes:
              - Use 24-hour HH:MM times and full day names (MONDAY, TUESDAY, etc.).
              - Schedule data is saved to data/courses.txt.
            """);
    }
}
