drop table if exists javabase.Student;
create table javabase.Student
(
    id          int NOT NULL auto_increment,
    legalName   VARCHAR(100)    NOT NULL,
    displayName VARCHAR(100)    NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

drop table if exists javabase.Instructor;
create table javabase.Instructor
(
    id   int NOT NULL auto_increment,
    name  VARCHAR(100)    NOT NULL,
    email VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

drop table if exists javabase.Course;
create table javabase.Course
(
    id          int NOT NULL auto_increment,
    name         varchar(100)    not null,
    universityId int NOT NULL,
    primary key (id)
);

drop table if exists javabase.Section;
create table javabase.Section
(
    id           int NOT NULL auto_increment,
    courseId     int NOT NULL,
    instructorId int NOT NULL,
    primary key (id)
);

drop table if exists javabase.Lecture;
create table javabase.Lecture
(
    id        int NOT NULL auto_increment,
    sectionId int NOT NULL,
    date      date            not null,
    primary key (id)
);

drop table if exists javabase.Enrollment;
create table javabase.Enrollment
(
    sectionId int NOT NULL,
    studentId int NOT NULL,
    primary key (sectionId, studentId)
);

drop table if exists javabase.Attendance;
create table javabase.Attendance
(
    lectureId int NOT NULL,
    studentId int NOT NULL,
    status    varchar(10)     not null,
    primary key (lectureId, studentId)
);

drop table if exists javabase.Passwords;
create table javabase.Passwords
(
    studentId int NOT NULL,
    password  varchar(100)    not null,
    primary key (studentId)
);

drop table if exists javabase.University;
create table javabase.University
(
    id         int NOT NULL auto_increment,
    name       varchar(100)    not null,
    changeName bool            not null,
    primary key (id)
);

drop table if exists javabase.Notification;
create table javabase.Notification
(
    sectionId  int NOT NULL,
    studentId int NOT NULL,
    notify    bool            not null,
    primary key (sectionId, studentId)
);