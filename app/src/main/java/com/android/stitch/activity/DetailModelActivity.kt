package com.android.stitch.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.stitch.R
import com.android.stitch.app.ApiConfig
import com.android.stitch.helper.Helper
import com.android.stitch.helper.SharedPref
import com.android.stitch.model.Bahan
import com.android.stitch.model.MPenjahit
import com.android.stitch.model.Model
import com.android.stitch.model.ResponModel
import com.android.stitch.room.MyDatabase
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_model.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailModelActivity : AppCompatActivity() {

    lateinit var model: Model
    lateinit var bahan: Bahan
    lateinit var myDb: MyDatabase
    lateinit var sp: SharedPref
    lateinit var p: MPenjahit
    lateinit var bhn : Spinner
    lateinit var result: TextView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_model)
        myDb = MyDatabase.getInstance(this)!! // call database
        getModel()
//        setSepiner()
        mainButton()
        checkKeranjang()
        sp = SharedPref(this)

        bhn = findViewById(R.id.spn_bahan) as Spinner
        result  = findViewById(R.id.tv_kosong) as TextView

        val bhns = arrayOf(model.material)
        bhn.adapter = ArrayAdapter<String>(this, R.layout.item_spinner, bhns)
        bhn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 result.text = bhns.get(position)
//                tv_harga.text = Helper().gantiRupiah(model.price)
            }
        }



        btn_belisekarang.setOnClickListener {
            val data = myDb.daoCart().getModel(model.id)
            if (data == null) {
                insert()
            } else {
                data.jumlah += 1
                update(data)
            }

            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    private fun mainButton (){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoCart().getModel(model.id)
            if (data == null) {
                insert()
            } else {
                data.jumlah += 1
                update(data)
            }
        }

        btn_detailtoko.setOnClickListener {
//            val json = intent.getStringExtra("id")!!.toString()
//            val intent = Intent(this@DetailModelActivity, DetailActivity::class.java)
//            val str2 = Gson().toJson(json, MPenjahit::class.java)
//            intent.putExtra("extra",str2)
//            this@DetailModelActivity.startActivity(intent)
////            val datapenjahit = penjahit.id
////            val intent = Intent(this@DetailModelActivity, DetailActivity::class.java)
////            intent.putExtra("id", datapenjahit)
////            this@DetailModelActivity.startActivity(intent)
            val data = intent.getStringExtra("extra")
            model = Gson().fromJson<Model>(data, Model::class.java)
            val id_penjahit=model.id_penjahit;
            val intent = Intent(this@DetailModelActivity, DetailActivity::class.java)
            intent.putExtra("id", id_penjahit)
            this@DetailModelActivity.startActivity(intent)
        }
//
//        btn_belisekarang.setOnClickListener {
//            val data = myDb.daoKeranjang().getModel(model.id)
//            if (data == null) {
//                insert()
//            } else {
//                data.total += 1
//                update(data)
//            }
//        }

//        btn_favorit.setOnClickListener {
//            val listNote = myDb.daoCart().getAll() // get All data
//            for(note : Model in listNote){
//                println("-----------------------")
//                println(note.name)
//                println(note.description)
//            }
//        }
        btn_toKeranjang.setOnClickListener {
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoCart().insert(model) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    checkKeranjang()
                    Log.d("respons", "data inserted")
                    Toast.makeText(this, "Berhasil Menambahkan Ke Keranjang", Toast.LENGTH_SHORT).show()
                })
    }

    private fun update(data: Model) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoCart().update(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    checkKeranjang()
                    Log.d("respons", "data inserted")
                    Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
                })
    }

    private fun checkKeranjang() {
        val dataKranjang = myDb.daoCart().getAll()

        if (dataKranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }

//    fun setSepiner() {
//        val arryString = ArrayList<String>()
//        arryString.add(model.material)
//
//
//        val adapter = ArrayAdapter<Any>(this, R.layout.item_spinner, arryString.toTypedArray())
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spn_bahan.adapter = adapter
//        spn_bahan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                if (position != 0) {
//                    getBahan(spn_bahan.selectedItem.toString())
//                }
//            }
//        }
//    }

//    private var listBahan:ArrayList<Bahan> = ArrayList()
//    private fun getBahan(id: String){
//        ApiConfig.instanceRetrofit.getBahan(id).enqueue(object : Callback<ResponModel> {
//            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {val res = response.body()!!
//                if(res.success == 1){
//                    listBahan = res.bahans
//                    Log.d("RESPONS", "displayData: "+res.bahans.size)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                Log.d("Error", "gagal memuat data:" + t.message)
//            }
//
//        })
//    }

    private fun getModel(){
        val data = intent.getStringExtra("extra")
        model = Gson().fromJson<Model>(data, Model::class.java)
        tv_nama.text = model.name
        tv_harga.text = Helper().gantiRupiah(model.price)
//        txt_bahan.text = model.material
//        txt_stok.text = model.stock
//        tv_ratingdetail.text = model.rating
        tv_deskripsi.text = model.description
        tv_toko.text = model.nama
        val img = model.image
        Picasso.get()
                .load(img)
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .resize(400,400)
                .into(image)

//        val adapter = ArrayAdapter<Any>(this@DetailModelActivity, R.layout.item_spinner)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spn_bahan.adapter = adapter
//        spn_bahan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                if (position != 0) {
//                    model = model.material
//                }
//            }
//        }
        Helper().setToolbar(this, toolbar, model.name)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}