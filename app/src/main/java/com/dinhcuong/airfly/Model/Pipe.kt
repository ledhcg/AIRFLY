package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dinhcuong.airfly.R


class Pipe (res : Resources){
    val pipeTop: Bitmap = BitmapFactory.decodeResource(res, R.drawable.pipe_top)
        get() = field
    val pipeBottom: Bitmap = BitmapFactory.decodeResource(res, R.drawable.pipe_bottom)
        get() = field


    val w = pipeTop.width
    val h = pipeTop.height

    var x : Int = 0
        get() = field
        set(value) {
            field = value
        }

    var ccY : Int = 0
        get() = field
        set(value) {
            field = value
        }

    fun getTopY() : Int{
        return ccY - h
    }

    fun getBottomY() : Int {
        return ccY + 500
    }
}