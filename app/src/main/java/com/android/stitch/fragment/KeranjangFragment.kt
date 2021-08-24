package com.android.stitch.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.stitch.R
import com.android.stitch.activity.CheckoutActivity
import com.android.stitch.activity.IntroActivity
import com.android.stitch.adapter.AdapterKeranjang
import com.android.stitch.helper.Helper
import com.android.stitch.helper.SharedPref
import com.android.stitch.model.Model
import com.android.stitch.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class KeranjangFragment : Fragment() {


    lateinit var myDb: MyDatabase
    lateinit var sp: SharedPref

    // dipangil sekali ketika aktivity aktif
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDb = MyDatabase.getInstance(requireActivity())!!
        sp = SharedPref(requireActivity())
        mainButton()
        return view
    }

    lateinit var adapter: AdapterKeranjang
    var listModel = ArrayList<Model>()
    private fun displayModel() {
       listModel = myDb.daoCart().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = AdapterKeranjang(requireActivity(), listModel, object : AdapterKeranjang.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listModel.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }
        })
        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listModel = myDb.daoCart().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (model in listModel) {
            if (model.selected) {
                val harga = Integer.valueOf(model.price)
                totalHarga += (harga * model.jumlah)
            } else {
                isSelectedAll = false
            }
        }
        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton() {
        btnDelete.setOnClickListener {
            val listDelete = ArrayList<Model>()
            for (m in listModel){
                if(m.selected) listDelete.add(m)
            }

            delete(listDelete)
        }

        btnBayar.setOnClickListener {

            if(sp.getStatusLogin()){

                var isThereModel = false
                for (m in listModel){
                    if(m.selected) isThereModel = true
                }

                if(isThereModel){
                    val intent = Intent(requireActivity(), CheckoutActivity::class.java)
                    intent.putExtra("extra", "" + totalHarga)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Tidak ada model yang terpilih", Toast.LENGTH_SHORT).show()
                }

            }else {
                requireActivity().startActivity(Intent(requireActivity(), IntroActivity::class.java))
            }

        }

        cbAll.setOnClickListener {
            for (i in listModel.indices) {
                val model = listModel[i]
                model.selected = cbAll.isChecked

                listModel[i] = model
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun delete(data: ArrayList<Model>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoCart().delete(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listModel.clear()
                    listModel.addAll(myDb.daoCart().getAll() as ArrayList)
                    adapter.notifyDataSetChanged()

                })
    }



    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView
    lateinit var cbAll: CheckBox
    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_model)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }

    override fun onResume() {
        displayModel()
        hitungTotal()
        super.onResume()
    }


}