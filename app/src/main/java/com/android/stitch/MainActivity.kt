package com.android.stitch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.stitch.activity.IntroActivity
import com.android.stitch.fragment.*
import com.android.stitch.helper.SharedPref
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val fragmentMap: Fragment = MapFragment()
    private val fragmentHome: Fragment = HomeFragment()
    private var fragmentAkun: Fragment = AkunFragment()
    private val fragmentPenjahit: Fragment = PenjahitFragment()
    private val fragmentKeranjang: Fragment = KeranjangFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statusLogin =  false
    private lateinit var sp: SharedPref

    private var dariDetail: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = SharedPref(this)

        setUpBottomNav()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage, IntentFilter("event:keranjang"))

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Respon", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("respon fcm", token.toString())
//            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    val mMessage: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            dariDetail = true
        }
    }

    fun setUpBottomNav(){
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()
        fm.beginTransaction().add(R.id.container, fragmentKeranjang).hide(fragmentKeranjang).commit()
        fm.beginTransaction().add(R.id.container, fragmentMap).hide(fragmentMap).commit()
        fm.beginTransaction().add(R.id.container, fragmentPenjahit).hide(fragmentPenjahit).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(1)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_map->{
                    callFragment(0, fragmentMap)
                }
                R.id.navigation_home->{
                    callFragment(1, fragmentHome)
                }
                R.id.navigation_penjahit->{
                    callFragment(2, fragmentPenjahit)
                }
                R.id.navigation_keranjang->{
                    callFragment(3, fragmentKeranjang)
                }
                R.id.navigation_akun->{
                    if (sp.getStatusLogin()){
                        callFragment(4, fragmentAkun)
                    } else {
                        startActivity(Intent(this, IntroActivity::class.java))
                    }
                }
            }
            false
        }
    }

    fun callFragment(int: Int, fragment: Fragment) {
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onResume() {
        if (dariDetail) {
            dariDetail = false
            callFragment(3, fragmentKeranjang)
        }
        super.onResume()
    }
}