package com.android.stitch.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.stitch.R
import com.android.stitch.adapter.AdapterModel
import com.android.stitch.adapter.AdapterSlider
import com.android.stitch.app.ApiConfig
import com.android.stitch.model.Model
import com.android.stitch.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
//    lateinit var vpSlider: ViewPager
    lateinit var rvProduk:RecyclerView
    lateinit var rvProdukTerlaris: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getModel()

        return view
    }

    fun displayModel(){
//        val arrSlider = ArrayList<Int>()
//        arrSlider.add(R.drawable.slider1)
//        arrSlider.add(R.drawable.slider2)
//        arrSlider.add(R.drawable.slider3)
//
//        val adapterSlider = AdapterSlider(arrSlider, activity)
//        vpSlider.adapter = adapterSlider
        val layoutManager = GridLayoutManager(activity,2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        val layoutManager2 = GridLayoutManager(activity, 2)
//        layoutManager2.orientation = LinearLayoutManager.VERTICAL

        rvProduk.adapter = AdapterModel(requireActivity(), listModel)
        rvProduk.layoutManager = layoutManager

//        rvProdukTerlaris.adapter = AdapterModel(requireActivity(), listModel)
//        rvProdukTerlaris.layoutManager = layoutManager2
    }

    private var listModel:ArrayList<Model> = ArrayList()

    fun getModel(){
        ApiConfig.instanceRetrofit.getModel().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Log.d("RESPONS","ERROR: "+ t.message)
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if(res.success == 1){
                    listModel = res.models
                    displayModel()
                    Log.d("RESPONS", "displayData: "+res.models.size)
                }
            }

        })
    }


    fun init(view: View){
//        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_model)
//        rvProdukTerlaris = view.findViewById(R.id.rv_modelTerlaris)
    }

//    val arrProduk: ArrayList<Model>get(){
//        val arr = ArrayList<Model>()
//        val p1 = Model()
//        p1.nama = "Jas Pria"
//        p1.harga = "Rp 150.000"
//        p1.gambar = R.drawable.jas1
//
//        val p2 = Model()
//        p2.nama = "Kemeja Asn"
//        p2.harga = "Rp 200.000"
//        p2.gambar = R.drawable.kemejaasn
//
//
//        val p3 = Model()
//        p3.nama = "Kemeja"
//        p3.harga = "Rp 100.000"
//        p3.gambar = R.drawable.kemeja
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }

}