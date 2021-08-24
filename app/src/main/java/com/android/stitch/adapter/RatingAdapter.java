package com.android.stitch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.android.stitch.R;
import com.android.stitch.model.MRating;
import com.android.stitch.util.ServerApi;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;
import static com.android.stitch.util.Tools.params;

public class RatingAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MRating> listModels;
    private RequestOptions options;

    public RatingAdapter(Context context, int layout, List<MRating> listModels) {
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
        private TextView text_kriteria;
        private RatingBar text_rating;
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
            holder.text_kriteria = (TextView) row.findViewById(R.id.activity_rating_listview_kriteria);
            holder.text_rating = (RatingBar) row.findViewById(R.id.activity_rating_listview_ratingBar);
            holder.cardview = (CardView) row.findViewById(R.id.activity_rating_listview_card);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final MRating model = listModels.get(position);
        holder.text_kriteria.setText(""+model.getKriteria());
        holder.text_rating.setRating(Float.parseFloat(model.getRating()));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = model.getId();
            }
        });
        holder.text_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String id = model.getId();
                String kriteria = model.getIdKriteria();
                ratingSend(context,id,kriteria,String.valueOf(rating));
            }
        });
        return row;
    }

    private void ratingSend(final Context context,String id,String kriteria,String rating) {
        params.put("id", id);
        params.put("kriteria", kriteria);
        params.put("rating", rating);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ServerApi.RATING_SEND, params, new AsyncHttpResponseHandler() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String status=jsonObject.isNull("status") ? null : jsonObject.getString("status");
                    if(status.equals("sukses")){
                        Toast.makeText(context, "Rating send !", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(context, "File you requested is not found !", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(context, "Sorry, Server is trouble", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Please, check your internet connection !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

