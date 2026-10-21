# Student Course Planner CLI

A Java command-line application for planning a weekly course schedule. It prevents overlapping classes and duplicate course codes, enforces an 18-credit maximum, sorts courses by weekday and start time, and persists the schedule between commands.

## Features
- Add and remove courses
- List courses in weekday/time order
- Check total credits
- Reject duplicate course codes
- Detect overlapping time slots
- Enforce an 18-credit limit
- Save and reload data from `data/courses.txt`
- Helpful input and storage error messages

## Requirements
Java 17 or newer.

## Compile (run from the project root)
```bash
mkdir -p out
javac -d out src/model/*.java src/service/*.java src/storage/*.java src/cli/*.java
java -cp out cli.Main help
```

## Examples
```bash
java -cp out cli.Main add EECS2030 "Software Tools" 3 MONDAY 10:00 11:30
java -cp out cli.Main add MATH1010 "Calculus I" 3 TUESDAY 13:00 14:30
java -cp out cli.Main list
java -cp out cli.Main credits
java -cp out cli.Main remove EECS2030
```

The schedule is saved in `data/courses.txt`, so it remains available after the Java process exits. Multi-word course names must be enclosed in quotes.

## Project structure
```text
CoursePlannerCLI/
├── src/
│   ├── cli/Main.java
│   ├── model/
│   │   ├── Course.java
│   │   ├── StudentSchedule.java
│   │   └── TimeSlot.java
│   ├── service/CoursePlannerService.java
│   └── storage/ScheduleStorage.java
├── data/
└── README.md
```

## Concepts demonstrated
Object-oriented design, Java collections and streams, `DayOfWeek`/`LocalTime`, command-line argument parsing, file I/O, validation, exception handling, and separation of concerns.

## Future improvements
- Add automated tests with JUnit
- Support editing a course
- Add weekday filters
- Replace the simple text storage format with JSON or CSV
- Add Maven or Gradle
