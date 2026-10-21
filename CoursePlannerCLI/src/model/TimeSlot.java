package model;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
public class TimeSlot {
    private final DayOfWeek day;
    private final LocalTime startTime, endTime;
    public TimeSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = Objects.requireNonNull(day, "Day cannot be null.");
        this.startTime = Objects.requireNonNull(startTime, "Start time cannot be null.");
        this.endTime = Objects.requireNonNull(endTime, "End time cannot be null.");
        if (!endTime.isAfter(startTime)) throw new IllegalArgumentException("End time must be after start time.");
    }
    public boolean conflictsWith(TimeSlot other) {
        if (day != other.day) return false;
        return startTime.isBefore(other.endTime) && other.startTime.isBefore(endTime);
    }
    public DayOfWeek getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
}
