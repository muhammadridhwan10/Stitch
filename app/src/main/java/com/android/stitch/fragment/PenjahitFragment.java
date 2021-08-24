package com.android.stitch.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.stitch.R;
import com.android.stitch.adapter.PenjahitAdapter;
import com.android.stitch.model.MPenjahit;
import com.android.stitch.util.ServerApi;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PenjahitFragment extends Fragment {
    private GridView gridView;
    private SwipeRefreshLayout refreshLayout;
    private List<MPenjahit> listPenjahit = new ArrayList<MPenjahit>();
    private RequestParams params = new RequestParams();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_penjahit, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview_penjahit);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_penjahit);
        refreshLayout.setRefreshing(true);
        listData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listData();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void listData() {
        listPenjahit.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ServerApi.PENJAHIT, params, new AsyncHttpResponseHandler() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onSuccess(String response) {
                //Toast.makeText(getContext(), "response :"+response, Toast.LENGTH_LONG).show();
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    if (arr.length() != 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = (JSONObject) arr.get(i);
                            MPenjahit item = new MPenjahit();
                            item.setId(obj.isNull("id") ? null : obj.getString("id"));
                            item.setNama(obj.isNull("nama") ? null : obj.getString("nama"));
                            item.setTelepon( obj.isNull("telepon") ? null : obj.getString("telepon"));
                            item.setEmail(obj.isNull("email") ? null : obj.getString("email"));
                            item.setAlamat(obj.isNull("alamat") ? null : obj.getString("alamat"));
                            item.setLatitude(obj.isNull("latitude") ? null : obj.getString("latitude"));
                            item.setLongitude(obj.isNull("longitude") ? null : obj.getString("longitude"));
                            item.setFoto(obj.isNull("foto") ? null : obj.getString("foto"));
                            listPenjahit.add(item);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    refreshLayout.setRefreshing(false);
                } finally {
                    final PenjahitAdapter adapter = new PenjahitAdapter(getContext(), R.layout.cardview_penjahit, listPenjahit);
                    gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(getContext(), "File you requested is not found !", Toast.LENGTH_SHORT).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getContext(), "Sorry, Server is trouble", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Please, check your internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
