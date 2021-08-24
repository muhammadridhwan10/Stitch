package com.android.stitch.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.stitch.R
import com.android.stitch.adapter.AdapterBank
import com.android.stitch.app.ApiConfig
import com.android.stitch.helper.Helper
import com.android.stitch.model.Bank
import com.android.stitch.model.Checkout
import com.android.stitch.model.ResponModel
import com.android.stitch.model.Transaksi
import com.google.gson.Gson
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_pembayaran.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        Helper().setToolbar(this, toolbar, "Pembayaran")

        displayBank()
    }

    fun displayBank() {
        val arrBank = ArrayList<Bank>()
        arrBank.add(Bank("Gopay", "081532833449", "Stitch", R.drawable.gopay))
        arrBank.add(Bank("Ovo", "081532833449", "Stitch", R.drawable.ovo))
        arrBank.add(Bank("Mandiri", "02394870329", "Stitch", R.drawable.logo_madiri))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_data.layoutManager = layoutManager
        rv_data.adapter = AdapterBank(arrBank, object : AdapterBank.Listeners {
            override fun onClicked(data: Bank, index: Int) {
                bayar(data)
            }
        })
    }

    fun bayar(bank: Bank) {
        val json = intent.getStringExtra("extra")!!.toString()
        val checkout = Gson().fromJson(json, Checkout::class.java)
        checkout.bank = bank.nama

        val loading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loading.setTitleText("Loading...").show()

        ApiConfig.instanceRetrofit.checkout(checkout).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                loading.dismiss()
                error(t.message.toString())
//                Toast.makeText(this@PembayaranActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                loading.dismiss()
                if (!response.isSuccessful) {
                    error(response.message())
                    return
                }
                val respon = response.body()!!
                if(respon.success == 1){

                    val jsBank = Gson().toJson(bank, Bank::class.java)
                    val jsTransaksi = Gson().toJson(respon.transaksi, Transaksi::class.java)
                    val jsCheckout = Gson().toJson(checkout, Checkout::class.java)

                    val intent = Intent(this@PembayaranActivity, SuccessActivity::class.java )
//                    Toast.makeText(this@PembayaranActivity, "Pesan:"+respon.message, Toast.LENGTH_SHORT).show()
                    intent.putExtra("bank", jsBank)
                    intent.putExtra("transaksi", jsTransaksi)
                    intent.putExtra("checkout", jsCheckout)
                    startActivity(intent)
                }else{
                    error(respon.message)
                    Toast.makeText(this@PembayaranActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    fun error(pesan: String) {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(pesan)
                .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



}