package com.android.stitch.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.stitch.MainActivity
import com.android.stitch.R
import com.android.stitch.helper.Helper
import com.android.stitch.model.Bank
import com.android.stitch.model.Checkout
import com.android.stitch.model.Transaksi
import com.android.stitch.room.MyDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_antrian.*
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.activity_success.tv_antrian
import kotlinx.android.synthetic.main.activity_success.tv_totalHarga
import kotlinx.android.synthetic.main.toolbar.*

class SuccessActivity : AppCompatActivity() {

    var nominal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        Helper().setToolbar(this, toolbar, "Bank Transfer")

        setValues()
        mainButton()
    }

    fun mainButton() {
        btn_copyNoRek.setOnClickListener {
            copyText(tv_nomorRekening.text.toString())
        }

        btn_copyNominal.setOnClickListener {
            copyText(nominal.toString())
        }

        btn_cekStatus.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }
    }

    fun copyText(text: String) {
        val copyManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copyText = ClipData.newPlainText("text", text)
        copyManager.setPrimaryClip(copyText)

        Toast.makeText(this, "Text berhasil di Copy", Toast.LENGTH_LONG).show()
    }

    fun setValues(){
        val jsBank = intent.getStringExtra("bank")
        val jsTransaksi = intent.getStringExtra("transaksi")
        val jsChekout = intent.getStringExtra("checkout")

        val bank = Gson().fromJson(jsBank, Bank::class.java)
        val transaksi = Gson().fromJson(jsTransaksi, Transaksi::class.java)
        val checkout = Gson().fromJson(jsChekout, Checkout::class.java)

        // hapus keranjang
        val myDb = MyDatabase.getInstance(this)!!
        for (model in checkout.models){
            myDb.daoCart().deleteById(model.id)
        }

        tv_nomorRekening.text = bank.rekening
        tv_namaPenerima.text = bank.penerima
        image_bank.setImageResource(bank.image)

        nominal = Integer.valueOf(transaksi.total_transfer) + transaksi.kode_unik
        tv_nominal.text = Helper().gantiRupiah(nominal)
        tv_totalHarga.text = checkout.estimasi
        tv_antrian.text = transaksi.no_antrian.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}