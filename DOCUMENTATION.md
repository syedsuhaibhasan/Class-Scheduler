# 📖 Class Scheduler Documentation

## Table of Contents
- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Modules Overview](#modules-overview)
- [Setup & Installation](#setup--installation)
- [User Roles & Features](#user-roles--features)
- [Database Schema](#database-schema)
- [Dependencies](#dependencies)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

---

## Introduction
Class Scheduler is a Java-based application for managing academic schedules, users, and notifications in educational institutions. It features a modern GUI, robust backend, and MySQL integration.

## Project Structure
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

## Modules Overview
- **ClassScheduler.java**: Main launcher and application logic.
- **dao/**: Handles database connections, notifications, and table management.
- **forms/**: Contains all GUI forms for user interaction and schedule management.
- **images/**: Icons and images for the UI.
- **Lib/**: External libraries (MySQL connector, Gson, Log4j, etc.).
- **utility/**: Utility classes and additional resources.

## Setup & Installation
1. Install Java SE 8+ and MySQL Server.
2. Clone the repository and import libraries from `Lib/`.
3. Configure MySQL and update `dao/connectionprovider.java`.
4. Build and run `ClassScheduler.java`.

## User Roles & Features
- **Administrator**: Manage faculty, students, timetables, notifications, and reports.
- **Faculty**: View classes, manage schedules, send notifications, participate in polls.
- **Student**: View schedules, receive notifications, access course info, give feedback.

## Database Schema
- Tables for users, schedules, notifications, polls, etc. (See `dao/Tables.java` for details.)

## Dependencies
- All required JARs are in the `Lib/` folder.
- Main dependencies: MySQL Connector, Gson, Log4j, Calendar components.

## Troubleshooting
- Ensure all JARs are present in `Lib/`.
- Verify MySQL configuration and credentials.
- Regularly backup your database.

## Contributing
Contributions are welcome! Submit pull requests or issues for improvements.

---
Made with ❤️ for educational institutions.
