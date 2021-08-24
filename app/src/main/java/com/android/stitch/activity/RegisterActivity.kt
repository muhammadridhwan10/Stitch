package com.android.stitch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.stitch.MainActivity
import com.android.stitch.R
import com.android.stitch.app.ApiConfig
import com.android.stitch.helper.SharedPref
import com.android.stitch.model.ResponModel
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    lateinit var sp: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sp = SharedPref(this)

        btn_register.setOnClickListener {
            register()
        }
    }

    fun register() {
        if (edt_nama.text.isEmpty()) {
            edt_nama.error = "Kolom Nama Tidak Boleh Kosong"
            edt_nama.requestFocus()
            return
        } else if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email Tidak Boleh Kosong"
            edt_email.requestFocus()
            return
        } else if (edt_phone.text.isEmpty()) {
            edt_phone.error = "Kolom No Telepon Tidak Boleh Kosong"
            edt_phone.requestFocus()
            return
        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
        }
        pb_regis.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.register(edt_nama.text.toString(), edt_email.text.toString(), edt_phone.text.toString(), edt_password.text.toString()).enqueue(object : Callback<ResponModel>{
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb_regis.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb_regis.visibility = View.GONE
                val respons = response.body()!!
                if(respons.success == 1){
                    sp.setStatusLogin(true)
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity, "Register Berhasil", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegisterActivity, "Error:"+respons.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}