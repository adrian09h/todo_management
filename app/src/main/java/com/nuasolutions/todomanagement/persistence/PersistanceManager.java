package com.nuasolutions.todomanagement.persistence;

public class PersistanceManager {
    private static PersistanceManager managerInstance;
    public static PersistanceManager sharedInstance() {
        if (managerInstance == null) {
            managerInstance = new PersistanceManager();
        }
        return managerInstance;
    }

    public void saveAuthToken(String token) {

    }

    public String readAuthToken() {
        return "";
    }
}
