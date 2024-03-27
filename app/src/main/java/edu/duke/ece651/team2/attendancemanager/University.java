package edu.duke.ece651.team2.attendancemanager;

public class University {
    //TODO? may add functions: This class will be asked whether it supports to change the display name
    private final String name;
    private final boolean change;

    public University(String name,boolean change){
        this.name = name;
        this.change = change;
    }

    public String getName(){
        return this.name;
    }

    public boolean getSupportChange(){
        return this.change;
    }   
}
