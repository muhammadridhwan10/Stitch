package com.android.stitch.model;

public class MPenjahit {
    private String id;
    private String nama;
    private String telepon;
    private String email;
    private String alamat;
    private String latitude;
    private String longitude;
    private String foto;


    public MPenjahit() {}

    public MPenjahit(String id, String nama, String telepon, String email, String alamat, String latitude, String longitude, String foto) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.email = email;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.foto = foto;

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
