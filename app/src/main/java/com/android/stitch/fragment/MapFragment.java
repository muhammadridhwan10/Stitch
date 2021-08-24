package com.android.stitch.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.stitch.R;
import com.android.stitch.activity.DetailActivity;
import com.android.stitch.adapter.InfoAdapter;
import com.android.stitch.model.MInfo;
import com.android.stitch.util.PermissionsHelper;
import com.android.stitch.util.ServerApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

public class MapFragment extends Fragment {
    private PermissionsHelper permissionsHelper;
    SupportMapFragment mMapView;
    GoogleMap googleMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center;
    LatLng latLng;
    RequestParams params=new RequestParams();
    LocationManager mLocationManager = null;
    String provider = null;
    Marker mCurrentPosition = null;
    static final int LOCATION_PERMISSION_ID = 1001;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null, false);
        checkPermissions();
        mMapView = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_home);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                getMarkers();
                center = new LatLng(-6.1696017,106.8676763);
                cameraPosition = new CameraPosition.Builder().target(center).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return view;
    }

    public void getMarkers() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ServerApi.HOMEPAGE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    if(arr.length() != 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = (JSONObject) arr.get(i);
                            final String id = obj.isNull("id") ? null : obj.getString("id");
                            final String nama = obj.isNull("nama") ? null : obj.getString("nama");
                            final String telepon = obj.isNull("telepon") ? null : obj.getString("telepon");
                            final String email = obj.isNull("email") ? null : obj.getString("email");
                            final String alamat = obj.isNull("alamat") ? null : obj.getString("alamat");
                            final String latitude = obj.isNull("latitude") ? "0" : obj.getString("latitude");
                            final String longitude = obj.isNull("longitude") ? "0" : obj.getString("longitude");
                            final String rating = obj.isNull("rating") ? "0" : obj.getString("rating");
                            try {
                                latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                addMarker(latLng, id,nama,telepon,email,alamat,rating);
                            } catch (NumberFormatException e) {
                                Toast.makeText(getActivity(), "Error "+e , Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "Data tidak di temukan !", Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {

                if (statusCode == 404) {
                    Toast.makeText(getActivity(), "File not found !", Toast.LENGTH_LONG).show();
                }else if (statusCode == 500) {
                    Toast.makeText(getActivity(), "Server is trouble !", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "check your internet connection !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addMarker(LatLng latlng,final String id, final String nama,final String telepon,final String email,final String alamat,final String rating) {
        markerOptions.position(latlng);
        markerOptions.title(""+id);
        MInfo info = new MInfo();
        info.setNama(""+nama);
        info.setTelepon("Telepon : "+telepon);
        info.setEmail("Email : "+email);
        info.setAlamat("Alamat : "+alamat);
        info.setRating("Rating : "+rating);
        InfoAdapter customAdapterInfoWindow = new InfoAdapter(getActivity());
        googleMap.setInfoWindowAdapter(customAdapterInfoWindow);
        Marker m = googleMap.addMarker(markerOptions);
        m.setTag(info);
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i= new Intent(getActivity(), DetailActivity.class);
                i.putExtra("id",  marker.getTitle());
                startActivity(i);
            }
        });
    }
    private void locateCurrentPosition() {
        int status = getActivity().getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, getActivity().getPackageName());
        if (status == PackageManager.PERMISSION_GRANTED) {
            Location location = mLocationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            long minTime = 1000;// ms
            float minDist = 5.0f;// meter
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locateCurrentPosition();
        }
    }

    private void updateWithNewLocation(Location location) {

        if (location != null && provider != null) {
            double lng = location.getLongitude();
            double lat = location.getLatitude();

            addBoundaryToCurrentPosition(lat, lng);

            CameraPosition camPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng)).zoom(10f).build();

            if (googleMap != null)
                googleMap.animateCamera(CameraUpdateFactory

                        .newCameraPosition(camPosition));
        } else {
            Log.d("Location error", "Something went wrong");
        }
    }

    private void addBoundaryToCurrentPosition(double lat, double lang) {
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.position(new LatLng(lat, lang));
        mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        mMarkerOptions.title("Posisi Anda");
        mMarkerOptions.anchor(0.2f, 0.2f);

        CircleOptions mOptions = new CircleOptions()
                .center(new LatLng(lat, lang)).radius(10)
                .strokeColor(0x110000FF).strokeWidth(1).fillColor(0x110000FF);
        googleMap.addCircle(mOptions);
        if (mCurrentPosition != null)
            mCurrentPosition.remove();
        mCurrentPosition = googleMap.addMarker(mMarkerOptions);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void checkPermissions() {
        permissionsHelper = new PermissionsHelper();
        permissionsHelper.checkAndRequestPermissions(getActivity(),
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

}