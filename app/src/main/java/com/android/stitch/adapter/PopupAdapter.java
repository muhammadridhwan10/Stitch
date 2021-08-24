package com.android.stitch.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.stitch.R;
import com.android.stitch.model.MInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class PopupAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public PopupAdapter(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.info_popup, null);
        TextView popup_nama = view.findViewById(R.id.popup_nama);
        MInfo MInfo = (MInfo) marker.getTag();
        popup_nama.setText(MInfo.getNama());
        return view;
    }
}
