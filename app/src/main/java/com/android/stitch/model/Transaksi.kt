package com.android.stitch.model

import android.util.Size

class Transaksi {
    var id = 0
    var name = ""
    var phone = ""
    var total_harga = ""
    var total_item = ""
    var user_id = ""
    var kode_trx = ""
    var kode_unik = 0
    var kode_payment = ""
    var total_transfer = ""
    var bank = ""
    var detail_ukuran = ""
    var estimasi = ""
    var status = ""
    var no_antrian = ""
    var updated_at = ""
    var created_at = ""
    val details = ArrayList<DetailTransaksi>()

}