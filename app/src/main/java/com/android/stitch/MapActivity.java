//package com.android.stitch;
//
//import android.Manifest;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.Toolbar;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.android.stitch.fragment.MapFragment;
//import com.android.stitch.util.PermissionsHelper;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class MapActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//    private PermissionsHelper permissionsHelper;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        getSupportActionBar().setElevation(0);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_location);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setTitle("LBS PENJAHIT");
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        checkPermissions();
//        if (findViewById(R.id.main_fragment_container) != null) {
//            MapFragment homeFragment = new MapFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment).commit();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        permissionsHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }
//
//    private void checkPermissions() {
//        permissionsHelper = new PermissionsHelper();
//        permissionsHelper.checkAndRequestPermissions(this,
//                android.Manifest.permission.ACCESS_NETWORK_STATE,
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.CAMERA,
//                android.Manifest.permission.WAKE_LOCK,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        );
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        }
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        switch (id) {
//            case R.id.navigation_map:
//                MapFragment mapFragment = new MapFragment();
//                transaction.replace(R.id.main_fragment_container, mapFragment).commit();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//}
