package storage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.StudentSchedule;
import model.TimeSlot;
public class ScheduleStorage {
    private final Path filePath;
    public ScheduleStorage(Path filePath) { this.filePath = filePath; }
    public StudentSchedule load() throws IOException {
        StudentSchedule schedule = new StudentSchedule();
        if (!Files.exists(filePath)) return schedule;
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split("\\\\|", -1);
            if (parts.length != 5) throw new IOException("Invalid data on line " + (i + 1) + " in " + filePath + ".");
            try {
                int credits = Integer.parseInt(parts[2]);
                DayOfWeek day = DayOfWeek.valueOf(parts[3]);
                String[] times = parts[4].split("-", -1);
                if (times.length != 2) throw new IllegalArgumentException("Invalid time range");
                TimeSlot slot = new TimeSlot(day, LocalTime.parse(times[0]), LocalTime.parse(times[1]));
                schedule.addCourse(new Course(parts[0], parts[1], credits, slot));
            } catch (RuntimeException ex) {
                throw new IOException("Could not read line " + (i + 1) + ": " + ex.getMessage(), ex);
            }
        }
        return schedule;
    }
    public void save(StudentSchedule schedule) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("# code|name|credits|day|start-end");
        for (Course c : schedule.getCourses()) {
            if (unsupported(c.getCode()) || unsupported(c.getName()))
                throw new IOException("Course codes and names cannot contain | or line breaks.");
            TimeSlot t = c.getTimeSlot();
            lines.add(c.getCode() + "|" + c.getName() + "|" + c.getCredits() + "|" + t.getDay()
                + "|" + t.getStartTime() + "-" + t.getEndTime());
        }
        Path parent = filePath.getParent();
        if (parent != null) Files.createDirectories(parent);
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }
    private boolean unsupported(String s) { return s.contains("|") || s.contains("\\n") || s.contains("\\r"); }
}
