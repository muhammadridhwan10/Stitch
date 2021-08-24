package com.android.stitch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.stitch.R
import com.android.stitch.helper.Helper
import kotlinx.android.synthetic.main.toolbar.*

class PanduanLbahuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panduanlbahu)
        Helper().setToolbar(this, toolbar, "Cara Mengukur Lebar Bahu")
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}