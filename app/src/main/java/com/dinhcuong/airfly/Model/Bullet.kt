package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dinhcuong.airfly.R

class Bullet (res : Resources) {
    var x : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var y : Int = 0
        get() = field
        set(value) {
            field = value
        }
    val bullet = BitmapFactory.decodeResource(res, R.drawable.bullet)
        get() = field

    val w = bullet.width
    val h = bullet.height

    fun getTargetX() : Int {
        return x + w
    }
    fun getTargetY() : Int {
        return y + h
    }
}