package com.android.stitch.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.stitch.R
import com.android.stitch.helper.Helper
import com.android.stitch.model.Bank
import com.android.stitch.model.Transaksi

class AdapterRiwayat(var data: ArrayList<Transaksi>, var listener: Listeners) : RecyclerView.Adapter<AdapterRiwayat.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_namariwayat)
        val tvHarga = view.findViewById<TextView>(R.id.tv_hargariwayat)
        val tvTanggal = view.findViewById<TextView>(R.id.tv_tglriwayat)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlahriwayat)
        val tvStatus = view.findViewById<TextView>(R.id.tv_statusriwayat)
        val tvAntrian = view.findViewById<TextView>(R.id.tv_antrianriwayat)
        val btnDetail = view.findViewById<TextView>(R.id.btn_detail)
        val layout = view.findViewById<CardView>(R.id.layoutriwayat)
    }

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]
        val name = a.details[0].model.name
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().gantiRupiah(a.total_transfer)
        holder.tvJumlah.text = a.total_item + " Item"
        holder.tvStatus.text = a.status
        holder.tvAntrian.text =  " No Antrian = " + a.no_antrian
        val formatBaru = "d MMM yyyy"
        holder.tvTanggal.text = Helper().convertTanggal(a.created_at, formatBaru)

        var color = context.getColor(R.color.menungu)
        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
        else if (a.status == "BATAL") color = context.getColor(R.color.batal)

        holder.tvStatus.setTextColor(color)

        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }

    interface Listeners {
        fun onClicked(data: Transaksi)
    }

}