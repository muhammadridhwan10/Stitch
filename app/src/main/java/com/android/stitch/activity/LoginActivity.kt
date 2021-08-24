package com.android.stitch.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.stitch.MainActivity
import com.android.stitch.R
import com.android.stitch.app.ApiConfig
import com.android.stitch.helper.SharedPref
import com.android.stitch.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var sp:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sp = SharedPref(this)

        mainButton()

        btn_login.setOnClickListener {
            login()
        }
    }
    fun login() {
        if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email Tidak Boleh Kosong"
            edt_email.requestFocus()
            return
        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
        }
        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(edt_email.text.toString(), edt_password.text.toString()).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE
                val respon = response.body()!!
                if(respon.success == 1){
                    sp.setStatusLogin(true)
                    sp.setUser(respon.user)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity, "Selamat Datang"+respon.user.name, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity, "Error:"+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun mainButton(){
        edt_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}