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
import com.android.stitch.activity.DetailModelActivity;
import com.android.stitch.model.MDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Objects;

public class DetailAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MDetail> listModels;
    private RequestOptions options;

    public DetailAdapter(Context context, int layout, List<MDetail> listModels) {
        this.context = context;
        this.layout = layout;
        this.listModels = listModels;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
    }

    @Override
    public int getCount() {
        return listModels.size();
    }


    @Override
    public Object getItem(int position) {
        return listModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView foto;
        private TextView  text_model;
        private TextView  text_harga;
        private TextView  text_rating;
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
            holder.text_model = (TextView) row.findViewById(R.id.activity_detail_cardview_model);
            holder.text_harga = (TextView) row.findViewById(R.id.activity_detail_cardview_harga);
            holder.text_rating = (TextView) row.findViewById(R.id.activity_detail_cardview_rating);
            holder.foto = (ImageView) row.findViewById(R.id.activity_detail_cardview_foto);
            holder.cardview = (CardView) row.findViewById(R.id.activity_detail_cardview);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final MDetail model = listModels.get(position);
        holder.text_model.setText(model.getNama());
        holder.text_harga.setText("Rp "+model.getPrice());
        holder.text_rating.setText("Rating : "+model.getRating());
        Glide.with(context).load(model.getFoto()).apply(options).into(holder.foto);
        /*
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = model.getId();
                Intent intent = new Intent(context, DetailModelActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
         */
        return row;
    }
}
