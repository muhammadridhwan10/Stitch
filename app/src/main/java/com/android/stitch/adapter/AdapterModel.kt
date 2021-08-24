package com.android.stitch.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.stitch.R
import com.android.stitch.activity.DetailModelActivity
import com.android.stitch.helper.Helper
import com.android.stitch.helper.SharedPref
import com.android.stitch.model.MPenjahit
import com.android.stitch.model.Model
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterModel(var activity: Activity, var data:ArrayList<Model>) : RecyclerView.Adapter<AdapterModel.Holder>() {
    class Holder(view : View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
//        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvPenjahit = view.findViewById<TextView>(R.id.tv_namapenjahit)
        val tvRating = view.findViewById<TextView>(R.id.tv_rating)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterModel.Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return AdapterModel.Holder(view)
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AdapterModel.Holder, position: Int) {
        holder.tvNama.text = data[position].name
//        holder.tvHarga.text = Helper().gantiRupiah(data[position].price)
        holder.tvPenjahit.text = data[position].nama
        holder.tvRating.text = data[position].rating
//        holder.imgProduk.setImageResource(data[position].image)

        val image = data[position].image
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .into(holder.imgProduk)

        holder.layout.setOnClickListener {
            val models = Intent(activity, DetailModelActivity::class.java)
            val str = Gson().toJson(data[position], Model::class.java)

            models.putExtra("extra",str)
            activity.startActivity(models)
        }
    }
}
