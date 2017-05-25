package com.ebabu.engineerbabu.beans;

/**
 * Created by hp on 12/05/2017.
 */
public class DataHandler {
    private static DataHandler dataHandler = null;

    public static DataHandler getInstance() {
        if (dataHandler == null) {
            dataHandler = new DataHandler();
        }
        return dataHandler;
    }

    private DataHandler() {
    }

    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }



    public static void reset() {
        dataHandler = null;
    }

}
