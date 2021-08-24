package com.android.stitch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.stitch.MainActivity
import com.android.stitch.R
import com.android.stitch.helper.Helper
import com.android.stitch.model.Checkout
import com.android.stitch.model.Transaksi
import com.android.stitch.room.MyDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_antrian.*
import kotlinx.android.synthetic.main.fragment_akun.*

class AntrianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antrian)

        setValues()
        mainButton()
    }


    fun setValues(){
        val jsTransaksi2 = intent.getStringExtra("transaksi")
        val jsCheckout2 = intent.getStringExtra("checkout")

        val transaksi = Gson().fromJson(jsTransaksi2, Transaksi::class.java)
        val checkout = Gson().fromJson(jsCheckout2, Checkout::class.java)

        val myDb = MyDatabase.getInstance(this)!!

        for(model in checkout.models){
            myDb.daoCart().deleteById(model.id)
        }

        tv_totalHarga.text = Helper().gantiRupiah(transaksi.total_harga)
        tv_antrian.text = transaksi.no_antrian.toString()
    }

//        fun setValues(){
//        val antrian = intent.getStringExtra("antrian")
//        tv_antrian.text = antrian
//    }



    fun mainButton(){
        btn_cek.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}