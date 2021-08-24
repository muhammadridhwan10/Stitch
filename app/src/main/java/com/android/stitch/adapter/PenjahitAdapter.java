package com.android.stitch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.android.stitch.R;
import com.android.stitch.activity.DetailActivity;
import com.android.stitch.model.MPenjahit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Objects;

public class PenjahitAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MPenjahit> listPenjahit;
    private RequestOptions options;

    public PenjahitAdapter(Context context, int layout, List<MPenjahit> listPenjahit) {
        this.context = context;
        this.layout = layout;
        this.listPenjahit = listPenjahit;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

    }

    @Override
    public int getCount() {
        return listPenjahit.size();
    }


    @Override
    public Object getItem(int position) {
        return listPenjahit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        ImageView foto;
        private TextView  text_nama;
        private TextView  text_telepon;
        private TextView  text_email;
        private CardView cardview;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = Objects.requireNonNull(inflater).inflate(layout, null);
            holder.text_nama = (TextView) row.findViewById(R.id.text_nama);
            holder.text_telepon = (TextView) row.findViewById(R.id.text_telepon);
            holder.text_email = (TextView) row.findViewById(R.id.text_email);
            holder.cardview = (CardView) row.findViewById(R.id.cardview_penjahit);
            holder.foto = (ImageView) row.findViewById(R.id.foto_penjahit);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final MPenjahit penjahit = listPenjahit.get(position);
        holder.text_nama.setText(penjahit.getNama());
        holder.text_telepon.setText(penjahit.getTelepon());
        holder.text_email.setText(penjahit.getEmail());
        Glide.with(context).load(penjahit.getFoto()).apply(options).into(holder.foto);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = penjahit.getId();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
        return row;
    }

}
