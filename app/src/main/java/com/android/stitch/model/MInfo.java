package com.android.stitch.model;

public class MInfo {
    private String id;
    private String nama;
    private String telepon;
    private String email;
    private String alamat;
    private String latitude;
    private String longitude;
    private String rating;


    public MInfo() {}

    public MInfo(String id, String nama, String telepon, String email, String alamat, String latitude, String longitude, String rating) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.email = email;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
