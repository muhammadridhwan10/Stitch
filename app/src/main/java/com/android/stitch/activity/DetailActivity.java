package com.android.stitch.activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.stitch.R;
import com.android.stitch.activity.RatingActivity;
import com.android.stitch.adapter.DetailAdapter;
import com.android.stitch.model.MDetail;
import com.android.stitch.util.ServerApi;
import com.android.stitch.util.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.android.stitch.util.Tools.params;
public class DetailActivity extends AppCompatActivity {
    private TextView detail_txt_nama, detail_txt_telepon, detail_txt_email, detail_txt_alamat;
    private ImageView detail_foto;
    private ProgressDialog progressDialog;
    private GridView gridView;
    private SwipeRefreshLayout refreshLayout;
    private List<MDetail> listModels = new ArrayList<MDetail>();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        id = getIntent().getExtras().getString("id");
        detail_txt_nama = findViewById(R.id.detail_nama);
        detail_txt_telepon = findViewById(R.id.detail_telepon);
        detail_txt_email = findViewById(R.id.detail_email);
        detail_txt_alamat = findViewById(R.id.detail_alamat);
        detail_foto = findViewById(R.id.detail_foto);
        gridView = (GridView)findViewById(R.id.gridview_detail);
        gridView = (GridView)findViewById(R.id.gridview_detail);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_detail);
        refreshLayout.setRefreshing(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Proses");
        initToolbar();
        getData();
        getDataModels();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                getDataModels();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Penjahit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void getData() {
        params.put("id", id);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ServerApi.DETAIL, params, new AsyncHttpResponseHandler() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    if (arr.length() != 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = (JSONObject) arr.get(i);
                            String id1=obj.isNull("id") ? null : obj.getString("id");
                            String nama=obj.isNull("nama") ? null : obj.getString("nama");
                            String telepon=obj.isNull("telepon") ? null : obj.getString("telepon");
                            String email=obj.isNull("email") ? null : obj.getString("email");
                            String alamat=obj.isNull("alamat") ? null : obj.getString("alamat");
                            String foto =obj.isNull("foto") ? null : obj.getString("foto");
                            String latitude =obj.isNull("latitude") ? "0" : obj.getString("latitude");
                            String longitude =obj.isNull("longitude") ? "0" : obj.getString("longitude");
                            detail_txt_nama.setText(""+nama);
                            detail_txt_telepon.setText(""+telepon);
                            detail_txt_email.setText(""+email);
                            detail_txt_alamat.setText(""+alamat);
                            SetImage(foto);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(DetailActivity.this, "File you requested is not found !", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(DetailActivity.this, "Sorry, Server is trouble", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Please, check your internet connection !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getDataModels() {
        listModels.clear();
        params.put("id", id);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ServerApi.DETAIL_MODELS, params, new AsyncHttpResponseHandler() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public void onSuccess(String response) {
                try {
                    //Toast.makeText(DetailActivity.this, "response : " +response, Toast.LENGTH_LONG).show();
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    if (arr.length() != 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = (JSONObject) arr.get(i);
                            MDetail mdetail =new MDetail();
                            mdetail.setId(obj.isNull("id") ? null : obj.getString("id"));
                            mdetail.setPenjahit(obj.isNull("penjahit") ? null : obj.getString("penjahit"));
                            mdetail.setNama(obj.isNull("nama") ? null : obj.getString("nama"));
                            mdetail.setPrice(obj.isNull("price") ? null : obj.getString("price"));
                            mdetail.setMaterial(obj.isNull("material") ? null : obj.getString("material"));
                            mdetail.setStock(obj.isNull("stock") ? null : obj.getString("stock"));
                            mdetail.setDescription(obj.isNull("description") ? null : obj.getString("description"));
                            mdetail.setEstimasi(obj.isNull("estimasi") ? null : obj.getString("estimasi"));
                            mdetail.setKategori(obj.isNull("category_id") ? null : obj.getString("category_id"));
                            mdetail.setCreatedAt(obj.isNull("created_at") ? null : obj.getString("created_at"));
                            mdetail.setUpdatedAt(obj.isNull("updated_at") ? null : obj.getString("updated_at"));
                            mdetail.setRating(obj.isNull("rating") ? "0" : obj.getString("rating"));
                            mdetail.setFoto(obj.isNull("foto") ? null : obj.getString("foto"));
                            listModels.add(mdetail);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    refreshLayout.setRefreshing(false);
                } finally {
                    final DetailAdapter adapter = new DetailAdapter(DetailActivity.this, R.layout.activity_detail_cardview, listModels);
                    gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(DetailActivity.this, "File you requested is not found !", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(DetailActivity.this, "Sorry, Server is trouble", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Please, check your internet connection !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SetImage(String foto){
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
        Glide.with(this).load(foto).apply(options).into(detail_foto);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}