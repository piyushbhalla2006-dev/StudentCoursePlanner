# Student Course Planner CLI

A Java command-line application for managing a student's course schedule. The application validates course selections, detects scheduling conflicts, enforces a maximum credit limit, and saves course information between runs.

## Features

- **Add courses:** Register courses with a course code, name, credit value, and class time.
- **Conflict detection:** Prevent courses from being scheduled at overlapping times on the same day.
- **Credit limit validation:** Enforce a maximum of 18 credits.
- **Duplicate prevention:** Prevent duplicate course codes.
- **Schedule management:** List scheduled courses, remove courses, and view total credits.
- **Persistent storage:** Save and reload the schedule from a local file.
- **Input validation:** Handle invalid commands and course information.
- **Sorted schedule:** Display courses in day and start-time order.

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Java Collections
- File I/O
- Input Validation

## Requirements

- Java Development Kit (JDK) 17 or later

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/piyushbhalla2006-dev/StudentCoursePlanner.git
cd StudentCoursePlanner
```

### 2. Compile the application

From the project root, run:

```bash
mkdir -p out
javac -d out src/model/*.java src/service/*.java src/storage/*.java src/cli/*.java
```

### 3. View available commands

```bash
java -cp out cli.Main help
```

## Usage

### Add a course

```bash
java -cp out cli.Main add EECS2030 "Software Tools" 3 MONDAY 10:00 11:30
```

### List scheduled courses

```bash
java -cp out cli.Main list
```

### View total credits

```bash
java -cp out cli.Main credits
```

### Remove a course

```bash
java -cp out cli.Main remove EECS2030
```

The application stores the schedule in `data/courses.txt`. Run commands from the project root so the data file is accessed consistently.

## What I Learned

This project strengthened my understanding of object-oriented design, collections, file persistence, input validation, and implementing scheduling rules. It also provided practical experience translating requirements into application logic.

## Future Improvements

- Add automated unit tests.
- Support editing existing courses.
- Export schedules to CSV.
- Build a graphical user interface.

## Author

Piyush Bhalla

[GitHub Repository](https://github.com/piyushbhalla2006-dev/StudentCoursePlanner)
