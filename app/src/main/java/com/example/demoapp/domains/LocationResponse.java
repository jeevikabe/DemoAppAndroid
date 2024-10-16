package com.example.demoapp.domains;

import java.io.Serializable;

public class LocationResponse implements Serializable {
    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return 0;

    }

    public double getLongitude() {
        return 0;
    }
}
