package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dinhcuong.airfly.R
import kotlin.random.Random

class Birb (res : Resources) {
    var ran: Random = Random
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
    val maxFrame : Int = 7
    var currentFrame : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var birbList : ArrayList<Bitmap>

    init {
        birbList = arrayListOf()
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b0))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b1))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b2))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b3))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b4))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b5))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b6))
        birbList.add(BitmapFactory.decodeResource(res, R.drawable.b7))

        x = ScreenSize.SCREEN_WIDTH
        y = ran.nextInt(10,ScreenSize.SCREEN_HEIGHT/2 - 10)
    }

    fun getBirb(current : Int) : Bitmap{
        return birbList.get(current)
    }
}