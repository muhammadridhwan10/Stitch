package com.android.stitch.model;

public class MRating {
    private String id;
    private String idkriteria;
    private String kriteria;
    private String rating;

    public MRating() {}

    public MRating(String id, String idkriteria,String kriteria, String rating) {
        this.id = id;
        this.idkriteria = idkriteria;
        this.kriteria = kriteria;
        this.rating = rating;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKriteria() {
        return idkriteria;
    }

    public void setIdKriteria(String idkriteria) {
        this.idkriteria = idkriteria;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}