package com.android.stitch.model;

public class MDetail extends MRating {
    private String id;
    private String penjahit;
    private String nama;
    private String price;
    private String material;
    private String stock;
    private String description;
    private String estimasi;
    private String kategori;
    private String createdAt;
    private String updatedAt;
    private String rating;
    private String foto;

    public MDetail() {}

    public MDetail(String id,String penjahit, String nama, String price, String material, String stock,String description,String estimasi, String kategori, String createdAt, String updatedAt,String rating, String foto) {
        this.id = id;
        this.penjahit = penjahit;
        this.nama = nama;
        this.price = price;
        this.material = material;
        this.stock = stock;
        this.description = description;
        this.estimasi = estimasi;
        this.kategori = kategori;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rating = rating;
        this.foto = foto;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPenjahit() {
        return penjahit;
    }

    public void setPenjahit(String penjahit) {
        this.penjahit = penjahit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.createdAt = updatedAt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
