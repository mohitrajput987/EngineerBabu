package com.ebabu.engineerbabu.beans;

import java.util.List;

/**
 * Created by hp on 19/05/2017.
 */
public class CachedData {
    private static CachedData ourInstance = new CachedData();

    public static CachedData getInstance() {
        return ourInstance;
    }

    private CachedData() {
    }

    private List<Platform> listPlatforms;

    public List<Platform> getListPlatforms() {
        return listPlatforms;
    }

    public void setListPlatforms(List<Platform> listPlatforms) {
        this.listPlatforms = listPlatforms;
    }
}
