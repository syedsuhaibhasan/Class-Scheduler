
# Class Scheduler

Class Scheduler is a Java-based desktop application designed to simplify and automate classroom scheduling and academic resource management for educational institutions. The system provides secure, role-based access for administrators, faculty, and students, and features a modern graphical user interface built with Java Swing.

## Overview

Class Scheduler streamlines the process of managing class schedules, faculty assignments, student registrations, and notifications. It integrates with a MySQL database for persistent storage and supports real-time updates and communication between users.

## Features

- Three-tier access: Administrator, Faculty, Student
- Secure authentication and role-based access control
- Timetable creation, editing, and conflict detection
- Faculty and student registration management
- Notification and alert system
- Polling and feedback tools
- Real-time schedule updates

## Technical Stack

- Frontend: Java Swing
- Backend: Java Core
- Database: MySQL
- Architecture: Modular, object-oriented design
- Dependencies: MySQL Connector, Gson, Log4j, Calendar components, and other utility libraries (all in the `Lib/` directory)

## Getting Started

### Prerequisites
- Java SE Development Kit 8 or higher
- MySQL Server
- IDE (Eclipse, NetBeans, IntelliJ IDEA, etc.)

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/syedsuhaibhasan/Class-Scheduler.git
   ```
2. Import required libraries from the `Lib/` folder
3. Configure MySQL:
   - Create a new database
   - Update connection settings in `dao/connectionprovider.java`
4. Build and run `ClassScheduler.java`

## User Roles

### Administrator
- Manage faculty accounts
- Create and modify timetables
- Handle student registrations
- Send system-wide notifications
- Generate reports

### Faculty
- View assigned classes
- Manage schedule preferences
- Send notifications
- Participate in polls
- View student lists

### Student
- View class schedules
- Receive notifications
- Access course information
- Participate in feedback

## Important Notes

- Regularly backup the database
- Keep all JAR files in the `Lib/` folder
- Ensure proper MySQL configuration
- Maintain proper access control

## License

This project is released for educational purposes. You may use and modify it with proper attribution.

## Contributing

Contributions are welcome! Please submit pull requests or open issues for improvements.

## Program Structure
```
Class-Scheduler/
├── ClassScheduler.java      # Application Entry Point
├── dao/                    # Data Access Layer
│   ├── connectionprovider.java
│   ├── NotificationSender.java
│   ├── NotificationViewer.java
│   └── Tables.java
├── forms/                  # UI Components
│   ├── AdminWindow.form/.java
│   ├── FacultyDashboard.form/.java
│   ├── FacultyRegistration.form/.java
│   ├── Login.form/.java
│   ├── PollDialog.java
│   ├── StudentDashboard.form/.java
│   ├── StudentRegistration.form/.java
│   ├── Timetable.java
│   ├── TimetableDialog.java
│   └── TimetableFrameEdit.java
├── images/                 # Visual Assets
├── Lib/                    # Dependencies (JARs)
├── utility/                # Helper Classes
│   ├── BDutility.java
│   └── images/
└── README.md               # Project Overview
```
