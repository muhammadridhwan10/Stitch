package com.android.stitch.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.stitch.R;
import com.android.stitch.adapter.RatingAdapter;
import com.android.stitch.model.MRating;
import com.android.stitch.util.ServerApi;
import com.android.stitch.util.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.stitch.util.Tools.params;

public class RatingActivity extends AppCompatActivity {
    public ImageView mFoto;
    public TextView mKode, mPenjahit, mModel;
    private Button bFinish;
    public ProgressDialog progressDialog;
    public ListView listview;
    public SwipeRefreshLayout refreshLayout;
    public List<MRating> listModels = new ArrayList<MRating>();
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        id = getIntent().getExtras().getString("id");
        mKode = findViewById(R.id.activity_rating_kode);
        mModel = findViewById(R.id.activity_rating_model);
        mPenjahit = findViewById(R.id.activity_rating_penjahit);
        mFoto = findViewById(R.id.activity_rating_foto);
        bFinish = findViewById(R.id.activity_rating_finish);
        listview = findViewById(R.id.activity_rating_listview);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_detail);
        refreshLayout.setRefreshing(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Proses");
        initToolbar();
        getData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshLayout.setRefreshing(false);
            }
        });
        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SUBMIT RATING");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void getData() {
        params.put("id", id);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ServerApi.RATING, params, new AsyncHttpResponseHandler() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public void onSuccess(String response) {
                //Toast.makeText(RatingActivity.this, "kode :"+kode, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String kode=jsonObject.isNull("kode") ? null : jsonObject.getString("kode");
                    String model=jsonObject.isNull("model") ? null : jsonObject.getString("model");
                    String penjahit=jsonObject.isNull("penjahit") ? null : jsonObject.getString("penjahit");
                    String foto =jsonObject.isNull("foto") ? null : jsonObject.getString("foto");
                    JSONArray jsonArray = jsonObject.getJSONArray("mkriteria");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        MRating mkriteria =new MRating();
                        mkriteria.setId(object.isNull("id") ? null : object.getString("id"));
                        mkriteria.setIdKriteria(object.isNull("id_kriteria") ? null : object.getString("id_kriteria"));
                        mkriteria.setKriteria(object.isNull("kriteria") ? null : object.getString("kriteria"));
                        mkriteria.setRating(object.isNull("rating") ? "0" : object.getString("rating"));
                        listModels.add(mkriteria);
                    }
                    mKode.setText(""+kode);
                    mModel.setText(""+model);
                    mPenjahit.setText(""+penjahit);
                    SetImage(foto);

                } catch (JSONException e) {
                    e.printStackTrace();
                    refreshLayout.setRefreshing(false);
                } finally {
                    final RatingAdapter adapter = new RatingAdapter(RatingActivity.this, R.layout.activity_rating_listview, listModels);
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(RatingActivity.this, "File you requested is not found !", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(RatingActivity.this, "Sorry, Server is trouble", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RatingActivity.this, "Please, check your internet connection !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SetImage(String foto){
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
        Glide.with(this).load(foto).apply(options).into(mFoto);
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
