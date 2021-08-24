package com.android.stitch.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.stitch.R
import com.android.stitch.activity.RatingActivity
import com.android.stitch.helper.Helper
import com.android.stitch.model.DetailTransaksi
import com.squareup.picasso.Picasso
import java.util.*

class AdapterModelTransaksi(var data: ArrayList<DetailTransaksi>) : RecyclerView.Adapter<AdapterModelTransaksi.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvTotalHarga = view.findViewById<TextView>(R.id.tv_totalHarga)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val bRating = view.findViewById<Button>(R.id.tv_rating)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk_transaksi, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.model.name
        val m = a.model
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().gantiRupiah(m.price)
        holder.tvTotalHarga.text = Helper().gantiRupiah(a.total_harga)
        holder.tvJumlah.text = a.total_item.toString() + " Items"
        holder.bRating.setOnClickListener { v ->
            val intent = Intent(v.context, RatingActivity::class.java)
            val bundle = Bundle();
            bundle.putString("id", a.model.id.toString())
            intent.putExtras(bundle);
            v.context.startActivity(intent)
        }
        val image = m.image
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .into(holder.imgProduk)
    }

    interface Listeners {
        fun onClicked(data: DetailTransaksi)
    }

}
