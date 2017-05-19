package com.ebabu.engineerbabu.beans;

/**
 * Created by hp on 12/05/2017.
 */
public class Platform {
    private String id;
    private String name;
    private int resId;
    private boolean isChecked;

    public Platform(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
