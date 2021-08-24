package com.android.stitch.app

import com.android.stitch.model.Checkout
import com.android.stitch.model.ResponModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("signup.php")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") nomortlp: String,
        @Field("password") password: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponModel>

    @POST("checkout.php")
    fun checkout(
            @Body data: Checkout
    ): Call<ResponModel>

//    @FormUrlEncoded
//    @POST("size")
//    fun size(
//            @Field("user_id") user_id: String,
//            @Field("models_id") models_id: String,
//            @Field("lingkarbadan") lingkarbadan: String,
//            @Field("lingkarpinggang") lingkarpinggang: String,
//            @Field("lingkarpinggul") lingkarpinggul: String,
//            @Field("lingkarbahu") lingkarbahu: String,
//            @Field("panjangtangan") panjangtangan: String,
//            @Field("panjangbaju") panjangbaju: String
//    ): Call<ResponModel>

    @GET("checkoutid.php")
    fun getRiwayat(
            @Query("id") id: Int
    ): Call<ResponModel>

    @GET("baru.php")
    fun getModel(): Call<ResponModel>

    @GET("bahan.php")
    fun getBahan(
        @Query("id") id: String
    ): Call<ResponModel>

    @POST("bataltransaksi.php")
    fun batalcheckout(
            @Query("id") id: Int
    ): Call<ResponModel>


}