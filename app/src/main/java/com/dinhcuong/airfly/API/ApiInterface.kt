package com.dinhcuong.airfly.API

import com.dinhcuong.airfly.Model.Users
import okhttp3.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("login.php")
    suspend fun getAllUsers(): retrofit2.Response<Users>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun check(
        @Field("action") action: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): retrofit2.Response<Users>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun create(
        @Field("action") action: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): retrofit2.Response<Users>

    @FormUrlEncoded
    @POST("score.php")
    suspend fun getScore(
        @Field("action") action: String
    ): retrofit2.Response<List<Users>>

    @FormUrlEncoded
    @POST("score.php")
    suspend fun updateHighScore(
        @Field("action") action: String,
        @Field("email") email: String,
        @Field("score") score: Int,
        @Field("birds_killed") birds_killed: Int,
    ): retrofit2.Response<Users>
}