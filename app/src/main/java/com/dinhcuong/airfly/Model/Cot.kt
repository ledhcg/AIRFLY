package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.dinhcuong.airfly.R


class Cot (res : Resources){
    val cotTop = BitmapFactory.decodeResource(res, R.drawable.col_top)
        get() = field
    val cotBottom = BitmapFactory.decodeResource(res, R.drawable.col_bottom)
        get() = field


    val w = cotTop.width
    val h = cotTop.height

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