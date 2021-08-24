package com.android.stitch.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart")
public class Model implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    public int idTb;

    public int id;
    public int id_penjahit;
    public String nama;
    public String name;
    public String price;
    public String description;
    public String material;
    public String stock;
    public int category_id;
    public String estimasi_selesai;
    public String image;
    public String rating;
    public String created_at;
    public String updated_at;

    public int jumlah = 1;
    public boolean selected = true;
    
}
