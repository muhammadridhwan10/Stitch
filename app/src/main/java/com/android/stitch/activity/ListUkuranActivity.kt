package com.android.stitch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.stitch.R
import com.android.stitch.adapter.AdapterUkuran
import com.android.stitch.helper.Helper
import com.android.stitch.model.Ukuran
import com.android.stitch.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_ukuran.*
import kotlinx.android.synthetic.main.toolbar.*

class ListUkuranActivity : AppCompatActivity() {

    lateinit var myDb : MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ukuran)
        Helper().setToolbar(this, toolbar, "Pilih Ukuran")
        myDb = MyDatabase.getInstance(this)!!

        mainButton()
    }

    private fun displayUkuran() {
        val arrayList = myDb.daoUkuran().getAll() as ArrayList

        if (arrayList.isEmpty()) div_kosong.visibility = View.VISIBLE
        else div_kosong.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_ukuran.adapter = AdapterUkuran(arrayList, object : AdapterUkuran.Listeners {
            override fun onClicked(data: Ukuran) {
                if (myDb.daoUkuran().getByStatus(true) != null){
                    val alamatActive = myDb.daoUkuran().getByStatus(true)!!
                    alamatActive.isSelected = false
                    updateActive(alamatActive, data)
                }
            }
        })
        rv_ukuran.layoutManager = layoutManager
    }

    private fun updateActive(dataActive: Ukuran, dataNonActive: Ukuran) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoUkuran().update(dataActive) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateNonActive(dataNonActive)
                })
    }

    private fun updateNonActive(data: Ukuran) {
        data.isSelected = true
        CompositeDisposable().add(Observable.fromCallable { myDb.daoUkuran().update(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onBackPressed()
                })
    }

    override fun onResume() {
        displayUkuran()
        super.onResume()
    }


    private fun mainButton(){
        btn_tambahUkuran.setOnClickListener {
            startActivity(Intent(this, TambahUkuranActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}