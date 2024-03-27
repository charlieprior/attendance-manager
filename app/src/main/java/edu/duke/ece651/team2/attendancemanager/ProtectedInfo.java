package edu.duke.ece651.team2.attendancemanager;

import java.util.HashMap;

public class ProtectedInfo {
    private HashMap<String,String> idToPassword = new HashMap<>();

    public void storeProtectedInfo(String id,String passwordString){
        idToPassword.put(id,passwordString);
    }

    public boolean match(String id,String passString){
        if(idToPassword.containsKey(id) && idToPassword.get(id).equals(passString)){
            return true;
        }
        return false;
    }
}
