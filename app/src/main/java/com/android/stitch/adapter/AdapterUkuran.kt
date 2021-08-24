package com.android.stitch.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.stitch.MainActivity
import com.android.stitch.R
import com.android.stitch.activity.DetailModelActivity
import com.android.stitch.activity.LoginActivity
import com.android.stitch.helper.Helper
import com.android.stitch.model.Model
import com.android.stitch.model.Ukuran
import com.android.stitch.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class AdapterUkuran(var data:ArrayList<Ukuran>, var listener: Listeners) : RecyclerView.Adapter<AdapterUkuran.Holder>() {
    class Holder(view : View):RecyclerView.ViewHolder(view){
        val tvLingkarBadan = view.findViewById<TextView>(R.id.tv_lingkarbadan)
        val tvLingkarPinggang = view.findViewById<TextView>(R.id.tv_lingkarpinggang)
        val tvLebarBahu = view.findViewById<TextView>(R.id.tv_lebarbahu)
        val tvPanjangLengan = view.findViewById<TextView>(R.id.tv_panjanglengan)
        val tvPanjangBaju = view.findViewById<TextView>(R.id.tv_panjangbaju)
        val layout = view.findViewById<CardView>(R.id.layout)
        val rd = view.findViewById<RadioButton>(R.id.rd_ukuran)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_ukuran, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val u = data[position]

        holder.rd.isChecked = u.isSelected
        holder.tvLingkarBadan.text = u.lingkarbadan
        holder.tvLingkarPinggang.text = u.lingkarpinggang
        holder.tvLebarBahu.text = u.lingkarbahu
        holder.tvPanjangLengan.text = u.panjanglengan
        holder.tvPanjangBaju.text = u.panjangbaju

        holder.rd.setOnClickListener {
            u.isSelected = true
            listener.onClicked(u)
        }

        holder.layout.setOnClickListener {
            u.isSelected = true
            listener.onClicked(u)
        }
    }

    interface Listeners {
        fun onClicked(data: Ukuran)
    }
}
