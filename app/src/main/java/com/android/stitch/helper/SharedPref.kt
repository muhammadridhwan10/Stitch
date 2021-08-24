package com.android.stitch.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android.stitch.model.MPenjahit
import com.android.stitch.model.Model
import com.android.stitch.model.User
import com.google.gson.Gson

class SharedPref(activity: Activity) {
    val login = "login"
    val nama = "nama"
    val phone = "phone"
    val email = "email"
    val user = "user"
    val penjahit = "penjahit"
    val model = "model"
    val models_id = "models_id"
    val mypref = "MAIN_PREF"
    val sp:SharedPreferences

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin():Boolean{
        return sp.getBoolean(login, false)
    }

    fun setUser(value: User){
        val data:String = Gson().toJson(value, User::class.java)
        sp.edit().putString(user, data).apply()
    }

    fun getUser(): User? {
        val data : String = sp.getString(user, null)?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }

    fun getPenjahit(): MPenjahit? {
        val data : String = sp.getString(user, null)?: return null
        return Gson().fromJson<MPenjahit>(data, MPenjahit::class.java)
    }

    fun getModel() : Model? {
        val data : String = sp.getString(model, null)?: return null
        return Gson().fromJson<Model>(data, Model::class.java)
    }

    fun setString(key:String, value: String){
        sp.edit().putString(key, value).apply()
    }

    fun getString(key:String): String {
        return sp.getString(key, "")!!
    }


}