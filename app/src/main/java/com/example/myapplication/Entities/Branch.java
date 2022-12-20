package com.example.myapplication.Entities;

import java.util.UUID;

public class Branch {
    private String id;
    private String name;
    private String phone;
    private String image;
    private String latitude;
    private String longitude;

    public Branch(String id, String name, String phone, String image, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Branch(String name, String phone, String image, String latitude, String longitude) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
