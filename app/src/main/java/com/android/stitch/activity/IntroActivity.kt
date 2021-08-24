package com.android.stitch.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.stitch.R
import com.android.stitch.helper.SharedPref
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    lateinit var sp: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        sp = SharedPref(this)

        mainButton()
    }

    private fun mainButton(){
        btn_prosesLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btn_regis.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}