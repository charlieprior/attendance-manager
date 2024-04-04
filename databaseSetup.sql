drop table if exists javabase.Student;
create table javabase.Student
(
    id          VARCHAR(100) NOT NULL,
    legalName   VARCHAR(100)    NOT NULL,
    displayName VARCHAR(100)    NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

drop table if exists javabase.Instructor;
create table javabase.Instructor
(
    id    VARCHAR(100) NOT NULL,
    name  VARCHAR(100)    NOT NULL,
    email VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

drop table if exists javabase.Course;
create table javabase.Course
(
    id          VARCHAR(100) NOT NULL,
    name         varchar(100)    not null,
    universityId VARCHAR(100) NOT NULL,
    primary key (id)
);

drop table if exists javabase.Section;
create table javabase.Section
(
    id           VARCHAR(100) NOT NULL,
    courseId     VARCHAR(100) NOT NULL,
    instructorId VARCHAR(100) NOT NULL,
    primary key (id)
);

drop table if exists javabase.Lecture;
create table javabase.Lecture
(
    id        VARCHAR(100) NOT NULL,
    sectionId VARCHAR(100) NOT NULL,
    date      date            not null,
    primary key (id)
);

drop table if exists javabase.Enrollment;
create table javabase.Enrollment
(
    sectionId VARCHAR(100) NOT NULL,
    studentId VARCHAR(100) NOT NULL,
    primary key (sectionId, studentId)
);

drop table if exists javabase.Attendance;
create table javabase.Attendance
(
    lectureId VARCHAR(100) NOT NULL,
    studentId VARCHAR(100) NOT NULL,
    status    varchar(10)     not null,
    primary key (lectureId, studentId)
);

drop table if exists javabase.Passwords;
create table javabase.Passwords
(
    studentId VARCHAR(100) NOT NULL,
    password  varchar(100)    not null,
    primary key (studentId)
);

drop table if exists javabase.University;
create table javabase.University
(
    id         VARCHAR(100) NOT NULL,
    name       varchar(100)    not null,
    changeName bool            not null,
    primary key (id)
);

drop table if exists javabase.Notification;
create table javabase.Notification
(
    sectionId  VARCHAR(100) NOT NULL,
    studentId VARCHAR(100) NOT NULL,
    notify    bool            not null,
    primary key (sectionId, studentId)
);