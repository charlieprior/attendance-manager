# ECE 651 - Spring 24 - Team Project - Attendance Manager

## Overview
This project was created over the course of a semester for the Duke University Software Engineering course. The project is a text-based terminal application that allows professors to take attendance in their classes. The application allows professors to load a roster from a CSV file, take attendance, mark students as present, absent, or tardy, and export the attendance records to a file. The application also sends notifications to students when their attendance is changed and sends a weekly report to students with their attendance records. The application also allows students to request a name change that will be reflected in the attendance records. The application is designed to be operating system independent and to run on Windows, Mac, Linux, etc. The application is designed to be secure and to protect the sensitive information about enrollment and attendance. The application is designed to be user-friendly and to save professors time in taking attendance. The project was written in Java using JavaFX for the GUI and JUnit for testing. It uses JDBC and MySQL for database management. The project was managed using GitLab and GitLab CI/CD was used for continuous integration and deployment. The project was developed using the Agile methodology and the Scrum framework. The project was developed by a team of four students: Charles Prior, Louise Li, Qianyi Xue, and Kenan Colak.

## Demo
The following video demonstrates the functionality of the application:
[Video Demo](https://www.dropbox.com/scl/fi/b5c47rlfi0angzrbui8z3/final_project_demo.mov?rlkey=h8zwef1xpexh8r8wwrj65k30o&dl=0)

## Installation
The project uses gradle for building and running the application.

## My contributions
I was responsible for the following tasks:
- Implementing the "Course" class
- Implementing the initial email notifier system
- Implementing the UI for student status and course selection
- Implementing CI/CD pipeline
- Designing the database and implementing the database connection

## User Stories
_The following user stories were developed for the project:_

During lecture time, most professors take attendance to record which students attend sessions.
Some professors take attendance using a signing sheet which is circulated among the students to sign their names.
Others use a traditional roll call, where the professor calls out the name of each student one at a time and updates an
attendance spreadsheet.
Both are tedious processes that may take five to ten minutes in large enrollment courses or even more outside the
classroom.

Your team was assigned to design a system to ease this process.
This program should run on a text-based terminal and should be operating system independent (i.e., may run on Windows,
Mac, Linux, etc.)

Keep in mind that the information about enrollment and attendance is sensitive, so security mechanisms should be
considered.

Professors don't want to spend time outside the classroom inputting values from a paper attendance sheet more than
students want to sign it.

The professor starts the classroom by taking attendance.
For this, they would like the system to present the name of the student on the screen, and then mark the student present
or absent.
This should be dynamic, so doing this should be a matter of a keypress.

In many cases, students may arrive a few minutes late.
So, at the end of the session, those students may approach the professor and the professor may change the attendance to
either present or most probably tardy.
The system may allow options of browsing or searching to locate students that will be marked tardy.

The system should record the attendance in plain text files.
Also, an export option should be provided for other formats like `json`, `xml` or `custom`.

As classes may be large, before the first day, the professor can load the roster from a `csv` file. The format of that
file may vary, including headers or not, column order, or data.

As the semester progresses, other students may enroll late to the class as well as others may drop from the class. For
students who dropped out, the professor is interested in keeping attendance records, but the student's name should not
appear in the attendance-taking process anymore.

At the end of the class week, the system should send a report to the student with their current attendance records.

In addition, every time that the student's attendance is changed (e.g., from absent to tardy) the system should send a
notification to the student. Initially, those notifications will be over email, though SMS or other methods could be
added later on.

In many cases, students desire that a different name be displayed during the attendance-taking process.
Thus, they can reach out to the professor asking them to change their name.
Given that, some university systems don't allow name changes, both the legal name and the display name should be kept in
sync.