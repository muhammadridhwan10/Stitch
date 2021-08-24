package com.android.stitch.model

import android.util.Size

class ResponModel {
    var success = 0
    lateinit var message:String
    var user = User()
    var size = Size()
    var models:ArrayList<Model> = ArrayList()
    var bahans:ArrayList<Bahan> = ArrayList()
    var transaksis :ArrayList<Transaksi> = ArrayList()
    var transaksi = Transaksi()
    lateinit var antrian:String
}