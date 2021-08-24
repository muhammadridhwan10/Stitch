package com.android.stitch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.stitch.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_about)
    }
}