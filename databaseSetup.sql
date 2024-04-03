create table javabase.Student
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    legalName   VARCHAR(100)    NOT NULL,
    displayName VARCHAR(100)    NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

create table javabase.Instructor
(
    id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name  VARCHAR(100)    NOT NULL,
    email VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

create table javabase.Course
(
    id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name varchar(100)    not null,
    primary key (id)
);

create table javabase.Section
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    courseId     BIGINT UNSIGNED not null,
    instructorId BIGINT unsigned not null,
    primary key (id)
);

create table javabase.Lecture
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    sectionId BIGINT unsigned not null,
    date      date            not null,
    primary key (id)
);

create table javabase.Enrollment
(
    sectionId bigint unsigned not null,
    studentId bigint unsigned not null,
    primary key (sectionId, studentId)
);

create table javabase.Attendance
(
    lectureId bigint unsigned not null,
    studentId bigint unsigned not null,
    status    varchar(10)     not null,
    primary key (lectureId, studentId)
);

create table javabase.Passwords
(
    studentId bigint unsigned not null,
    password  varchar(100)    not null,
    primary key (studentId)
);

create table javabase.Notification
(
    courseId  bigint unsigned not null,
    studentId bigint unsigned not null,
    notify    bool            not null,
    primary key (courseId, studentId)
);