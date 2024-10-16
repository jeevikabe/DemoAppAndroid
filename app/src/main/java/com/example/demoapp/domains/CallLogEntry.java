package com.example.demoapp.domains;



public class CallLogEntry {
    private String number;
    private String type;
    private String duration;

    public CallLogEntry(String number, String type, String duration) {
        this.number = number;
        this.type = type;
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getDuration() {
        return duration;
    }
}

