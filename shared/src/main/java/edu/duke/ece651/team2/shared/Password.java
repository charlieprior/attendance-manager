package edu.duke.ece651.team2.shared;

public class Password {
    Integer studentId;
    String password;

    public Password(){}

    public Password(Integer studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
