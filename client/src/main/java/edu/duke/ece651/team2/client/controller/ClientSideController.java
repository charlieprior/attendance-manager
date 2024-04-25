package edu.duke.ece651.team2.client.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.duke.ece651.team2.shared.*;

public class ClientSideController {
    private ClientSideView clientSideView;

    public ClientSideController(ClientSideView clientSideView) {
        this.clientSideView = clientSideView;
    }


    public void displayPromptForStudent(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Below are all the courses you are enrolled in this semester, please select one to set your email preferences.");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Below are all the courses you are enrolled in this semester, please select one to get your attendance report.");
        }
    }

    public void displayPromptForFacultyGetSections(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Below are the courses you are teaching this semester, please select a course to record your attendance.");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Below are the courses you are teaching this semester, please select a course to update your attendance.");
        } else if (n == 3) {
            clientSideView.displayMessage(
                    "Below are the courses you are teaching this semester, please select a course to export students attendance info.");
        }
    }

    public void displayPromptForFacultyGetLectures(int n) {
        if (n == 1) {
            clientSideView.displayMessage(
                    "Below are the lectures you are teaching this semester, please select a course to record your attendance.");
        } else if (n == 2) {
            clientSideView.displayMessage(
                    "Below are the lectures you are teaching this semester, please select a course to update your attendance.");
        } else if (n == 3) {
            clientSideView.displayMessage(
                    "Below are the lectures you are teaching this semester, please select a course to export students attendance info.");
        }
    }

    // public void displayPromptForFacultyGetConfirm(int n) {
    //     if (n == 1) {
    //         clientSideView.displayMessage(
    //                 "Waiting for an record......");
    //     } else if (n == 2) {
    //         clientSideView.displayMessage(
    //                 "Waiting for an update......");
    //     }
    // }

    // public Section displayAndChooseSection(Section[] sec) {
    //     for (int i = 0; i < sec.length; i++) {
    //         clientSideView.displayMessage(i + 1 + ". "+sec[i].getName());
    //     }
    //     clientSideView.displayMessage("0 will return back.\n");
    //     int choice = -1;
    //     try {
    //         while (choice < 0 || choice > sec.length) {
    //             String ans = clientSideView.promptUser("Choose a valid Section:");
    //             choice = Integer.parseInt(ans);
    //         }
    //         if(choice==0){
    //             return null;
    //         }
    //         return sec[choice - 1];
    //     } catch (IOException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public List<AttendanceRecord> getStudentsStatus(Student[] students, Lecture l) {
    //     List<AttendanceRecord> records = new ArrayList<>();
    //     try {

    //         for (Student s : students) {
    //             String ans = clientSideView
    //                     .promptUser("What is the attendance for " + s.getDisplayName() + "? y for Yes");
    //             AttendanceStatus status;
    //             if (ans.equals("y")) {
    //                 status = AttendanceStatus.PRESENT;
    //             } else {
    //                 status = AttendanceStatus.ABSENT;
    //             }
    //             records.add(new AttendanceRecord(s.getStudentID(), status, l.getLectureID()));
    //         }

    //         return records;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public boolean isValidStringForAttendance(String input, int num) {
    //     String regex = "^[1-" + num + "][ATP]$";
    //     Pattern pattern = Pattern.compile(regex);
    //     Matcher matcher = pattern.matcher(input);
    //     return matcher.matches();
    // }

    // public boolean isValidStringForRecord(String input) {
    //     if (input == null || input.length() != 1) {
    //         return false;
    //     }
    //     char ch = input.charAt(0);
    //     return ch == 'A' || ch == 'T' || ch == 'P';
    // }

}