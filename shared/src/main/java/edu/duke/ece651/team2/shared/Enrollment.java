package edu.duke.ece651.team2.shared;

public class Enrollment {
    private final Integer sectionId;
    private final Integer studentId;

    public Enrollment(Integer sectionId, Integer studentId) {
        this.sectionId = sectionId;
        this.studentId = studentId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public Integer getStudentId() {
        return studentId;
    }
}
