package com.android.stitch.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.stitch.R
import com.android.stitch.adapter.AdapterModelTransaksi
import com.android.stitch.app.ApiConfig
import com.android.stitch.helper.Helper
import com.android.stitch.model.DetailTransaksi
import com.android.stitch.model.ResponModel
import com.android.stitch.model.Transaksi
import com.google.gson.Gson
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTransaksiActivity : AppCompatActivity() {

    var transaksi = Transaksi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, Transaksi::class.java)

        setData(transaksi)
        displayModel(transaksi.details)
        mainButton()
    }

    private fun mainButton() {
        btn_batal.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Apakah anda yakin?")
                .setContentText("Transaksi akan di batalkan dan tidak bisa di kembalikan!")
                .setConfirmText("Yes, Batalkan")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                    batalTransaksi()
                }
                .setCancelText("Tutup")
                .setCancelClickListener {
                    it.dismissWithAnimation()
                }.show()
        }
    }

    fun batalTransaksi() {
        val loading = SweetAlertDialog(this@DetailTransaksiActivity, SweetAlertDialog.PROGRESS_TYPE)
        loading.setTitleText("Loading...").show()
        ApiConfig.instanceRetrofit.batalcheckout(transaksi.id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                loading.dismiss()
                error(t.message.toString())
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                loading.dismiss()
                val res = response.body()!!
                if(res.success == 1){
                    SweetAlertDialog(this@DetailTransaksiActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success...")
                        .setContentText("Transaksi berhasil dibatalakan")
                        .setConfirmClickListener {
                            it.dismissWithAnimation()
                            onBackPressed()
                        }
                        .show()

//                    Toast.makeText(this@DetailTransaksiActivity, "Transaksi berhasil di batalkan", Toast.LENGTH_SHORT).show()
//                    onBackPressed()
//                    displayRiwayat(res.transaksis)
                }else {
                    error(res.message)
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

    fun setData(t: Transaksi) {
        tv_status.text = t.status
        tv_tgl.text = t.created_at
        tv_name.text = t.name + " - " + t.phone
        tv_ukuran.text = " Lingkar Badan, Lingkar Pinggang, Lingkar Bahu, Panjang Lengan, Panjang Baju = " + t.detail_ukuran
        tv_kodeunik.text = Helper().gantiRupiah(t.kode_unik)
        tv_totalBelanja.text = Helper().gantiRupiah(t.total_harga)

        if (t.status != "MENUNGGU") div_footer.visibility = View.GONE

        var color = getColor(R.color.menungu)
        if (t.status == "SELESAI") color = getColor(R.color.selesai)
        else if (t.status == "BATAL") color = getColor(R.color.batal)

        tv_status.setTextColor(color)
    }

    fun displayModel(transaksis: ArrayList<DetailTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterModelTransaksi(transaksis)
        rv_produk.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}