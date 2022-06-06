package com.dinhcuong.airfly.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Users(
    name: String?,
    email: String?,
    phone: String?,
    score: String?,
    birds_killded: String?,
    time: String?
) {
    @SerializedName("isSuccess")
    @Expose
    var isSuccess: Boolean? = null

    @SerializedName("isExists")
    @Expose
    var isExists: Boolean? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("score")
    @Expose
    var score: String? = null

    @SerializedName("birds_killed")
    @Expose
    var birdsKilled: String? = null

    @SerializedName("time")
    @Expose
    var time: String? = null
}