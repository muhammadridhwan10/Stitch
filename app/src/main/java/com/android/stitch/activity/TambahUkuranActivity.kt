package com.android.stitch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.android.stitch.R
import com.android.stitch.helper.Helper
import com.android.stitch.model.Ukuran
import com.android.stitch.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tambah_ukuran.*
import kotlinx.android.synthetic.main.toolbar.*

class TambahUkuranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_ukuran)
        Helper().setToolbar(this, toolbar, "Tambah Ukuran")

        mainButton()
    }

    private fun mainButton() {
        btn_simpan.setOnClickListener {
            simpan()
        }
        btn_panduanlb.setOnClickListener {
            startActivity(Intent(this@TambahUkuranActivity, PanduanLbActivity::class.java))
        }
        btn_panduanlp.setOnClickListener {
            startActivity(Intent(this@TambahUkuranActivity, PanduanLpActivity::class.java))
        }
        btn_panduanlbahu.setOnClickListener {
            startActivity(Intent(this@TambahUkuranActivity, PanduanLbahuActivity::class.java))
        }
        btn_panduanpl.setOnClickListener {
            startActivity(Intent(this@TambahUkuranActivity, PanduanPlActivity::class.java))
        }
        btn_panduanpb.setOnClickListener {
            startActivity(Intent(this@TambahUkuranActivity, PanduanPbActivity::class.java))
        }
    }

    private fun simpan(){
        when {
            edt_lingkarbadan.text.isEmpty() -> {
                error(edt_lingkarbadan)
                return
            }
            edt_lingkarpinggang.text.isEmpty() -> {
                error(edt_lingkarpinggang)
                return
            }
            edt_lingkarbahu.text.isEmpty() -> {
                error(edt_lingkarbahu)
                return
            }
            edt_panjanglengan.text.isEmpty() -> {
                error(edt_panjanglengan)
                return
            }
            edt_panjangbaju.text.isEmpty() -> {
                error(edt_panjangbaju)
                return
            }
        }
        val ukuran = Ukuran()
        ukuran.lingkarbadan = edt_lingkarbadan.text.toString()
        ukuran.lingkarpinggang = edt_lingkarpinggang.text.toString()
        ukuran.lingkarbahu = edt_lingkarbahu.text.toString()
        ukuran.panjanglengan = edt_panjanglengan.text.toString()
        ukuran.panjangbaju = edt_panjangbaju.text.toString()


        insert(ukuran)

    }

    fun toast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun error(editText: EditText) {
        editText.error = "Kolom tidak boleh kosong"
        editText.requestFocus()
    }


    private fun insert(data: Ukuran) {
        val myDb = MyDatabase.getInstance(this)!!
        if (myDb.daoUkuran().getByStatus(true) == null){
            data.isSelected = true
        }
        CompositeDisposable().add(Observable.fromCallable { myDb.daoUkuran().insert(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    toast("Insert data success")
                    onBackPressed()
                })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}
