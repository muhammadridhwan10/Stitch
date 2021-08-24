package com.android.stitch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ukuran")
class Ukuran {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    var idTb = 0

    var id = 0
    var lingkarbadan = ""
    var lingkarpinggang = ""
    var lingkarbahu = ""
    var panjanglengan = ""
    var panjangbaju = ""

    var isSelected = false
}