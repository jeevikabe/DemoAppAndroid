package com.example.demoapp.domains;


import java.io.Serializable;

public class Person implements Serializable {

    //@SerializedName("tpId")
    private String tpId;

    //@SerializedName("address")
    private String address;

   // @SerializedName("phoneNumber")
    private String phoneNumber;

    //@SerializedName("gender")
    private String gender;

    //@SerializedName("name")
    private String name;

   // @SerializedName("photo")
    private String photo;

   // @SerializedName("id")
    private int id;

   // @SerializedName("poiId")
    private String poiId;

    //@SerializedName("category")
    private String category;

    //@SerializedName("isWatchList")
    private boolean isWatchList;

   // @SerializedName("sector")
    private String sector;

   // @SerializedName("framephoto")
    private String framePhoto;

    // Constructor
    public Person(String tpId, String address, String phoneNumber, String gender, String name,
                  String photo, int id, String poiId, String category, boolean isWatchList,
                  String sector, String framePhoto) {
        this.tpId = tpId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.name = name;
        this.photo = photo;
        this.id = id;
        this.poiId = poiId;
        this.category = category;
        this.isWatchList = isWatchList;
        this.sector = sector;
        this.framePhoto = framePhoto;
    }

    // Getters and Setters
    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isWatchList() {
        return isWatchList;
    }

    public void setWatchList(boolean watchList) {
        isWatchList = watchList;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getFramePhoto() {
        return framePhoto;
    }

    public void setFramePhoto(String framePhoto) {
        this.framePhoto = framePhoto;
    }
}
