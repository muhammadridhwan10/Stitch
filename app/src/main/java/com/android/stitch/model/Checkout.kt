package com.android.stitch.model

class Checkout {
    lateinit var user_id:String
    lateinit var id_penjahit:String
    lateinit var total_item:String
    lateinit var total_harga:String
    lateinit var name:String
    lateinit var detail_ukuran:String
    lateinit var phone:String
    lateinit var total_transfer:String
    lateinit var bank:String
    lateinit var estimasi:String
    var models = ArrayList<Item>()

    class Item {
        lateinit var id:String
        lateinit var total_item:String
        lateinit var total_harga:String
        lateinit var catatan:String
    }

}