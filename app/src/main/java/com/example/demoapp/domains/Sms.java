package com.example.demoapp.domains;



public class Sms {
    private String address;
    private String body;

    public Sms(String address, String body) {
        this.address = address;
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }
}

