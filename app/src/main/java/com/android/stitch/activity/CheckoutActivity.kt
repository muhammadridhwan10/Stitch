package com.android.stitch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.stitch.R
import com.android.stitch.app.ApiConfig
import com.android.stitch.helper.Helper
import com.android.stitch.helper.SharedPref
import com.android.stitch.model.*
import com.android.stitch.room.MyDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        Helper().setToolbar(this, toolbar, "Checkout")
        myDb = MyDatabase.getInstance(this)!!
        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
        tv_total.text = Helper().gantiRupiah(totalHarga)
//        tv_biayaAdmin.text = Helper().gantiRupiah(biayaAdmin)
//        setTotal()
        mainButton()
    }

    private fun setTotal() {
//        tv_biayaAdmin.text = Helper().gantiRupiah(biayaAdmin)
        tv_total.text = Helper().gantiRupiah(totalHarga)
    }

    fun checkUkuran() {

        if (myDb.daoUkuran().getByStatus(true) != null) {
            div_ukuran.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE

            val u = myDb.daoUkuran().getByStatus(true)!!
            tv_ukuran.text = u.lingkarbadan + ", " + u.lingkarpinggang + ",  " + u.lingkarbahu + ", " + u.panjanglengan + ", " + u.panjangbaju +", "
//            tv_lingkarbadan1.text = u.lingkarbadan
//            tv_lingkarpinggang1.text = u.lingkarpinggang
//            tv_lingkarpinggul1.text = u.lingkarpinggul
//            tv_lingkarbahu1.text = u.lingkarbahu
//            tv_panjangtangan1.text = u.panjangtangan
//            tv_panjangbaju1.text = u.panjangbaju
            btn_tambahUkuran.text = "Tambah Ukuran"

        } else {
            div_ukuran.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btn_tambahUkuran.text = "Tambah Ukuran"
        }
    }


    private fun mainButton(){

        btn_pesan.setOnClickListener {
            pesan()
        }
        btn_tambahUkuran.setOnClickListener {
            startActivity(Intent(this, ListUkuranActivity::class.java))
        }
    }

    private fun pesan(){
        val user = SharedPref(this).getUser()!!
        val listModel = myDb.daoCart().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        var selesai = ""
        val models = ArrayList<Checkout.Item>()
        for (m in listModel){
            if(m.selected){
                totalItem += m.jumlah
                totalHarga += (m.jumlah * Integer.valueOf(m.price))
                selesai = m.estimasi_selesai
                val model = Checkout.Item()
                model.id = ""+ m.id
                model.total_item = ""+ m.jumlah
                model.total_harga = ""+ (m.jumlah * Integer.valueOf(m.price))
                model.catatan = "catatan"
                models.add(model)
            }
        }
        val checkout = Checkout()
        checkout.user_id = ""+ user.id
        checkout.total_item = ""+ totalItem
        checkout.total_harga = ""+ totalHarga
        checkout.name = user.name
        checkout.estimasi = ""+ selesai
        checkout.detail_ukuran = tv_ukuran.text.toString()
        checkout.phone = user.phone
        checkout.total_transfer = ""+ totalHarga
        checkout.models = models

        val json = Gson().toJson(checkout, Checkout::class.java)
        Log.d("Respon:", "json:" + json)
        val intent = Intent(this, PembayaranActivity::class.java)
        intent.putExtra("extra", json)
        startActivity(intent)


//        ApiConfig.instanceRetrofit.checkout(checkout).enqueue(object : Callback<ResponModel> {
//            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                Toast.makeText(this@CheckoutActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
//            }
//            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                if (!response.isSuccessful) {
//                    Log.d("Respons", "Erorrnya:" + response.message())
//                    return
//                }
//                val respon = response.body()!!
//                if(respon.success == 1){
//
//                    val jsTransaksi = Gson().toJson(respon.transaksi, Transaksi::class.java)
//                    val jsCheckout = Gson().toJson(checkout, Checkout::class.java)
//
//                    val intent = Intent(this@CheckoutActivity, AntrianActivity::class.java )
//                    Toast.makeText(this@CheckoutActivity, "Pesan:"+respon.message, Toast.LENGTH_SHORT).show()
//                    intent.putExtra("transaksi", jsTransaksi)
//                    intent.putExtra("checkout", jsCheckout)
//                    startActivity(intent)
//                }else{
//                    Toast.makeText(this@CheckoutActivity, "Error:"+respon.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        })




    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        checkUkuran()
        super.onResume()
    }

//    fun setTotal(biayaAdmin: String) {
//        tv_biayaAdmin.text = Helper().gantiRupiah(biayaAdmin)
//        tv_total.text = Helper().gantiRupiah(Integer.valueOf(biayaAdmin) + totalHarga)
//    }


}