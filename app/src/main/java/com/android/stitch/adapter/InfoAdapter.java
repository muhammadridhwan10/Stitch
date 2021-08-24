package com.android.stitch.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.stitch.R;
import com.android.stitch.model.MInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public InfoAdapter(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.info_window, null);
        TextView iwd_nama = view.findViewById(R.id.iw_nama);
        TextView iwd_telepon = view.findViewById(R.id.iw_telepon);
        TextView iwd_email = view.findViewById(R.id.iw_email);
        TextView iwd_alamat= view.findViewById(R.id.iw_alamat);

        MInfo MInfo = (MInfo) marker.getTag();
        iwd_nama.setText(MInfo.getNama());
        iwd_email.setText(MInfo.getEmail());
        iwd_telepon.setText(MInfo.getTelepon());
        iwd_alamat.setText(MInfo.getAlamat());
        return view;
    }
}
