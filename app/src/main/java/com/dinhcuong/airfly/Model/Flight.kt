package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.dinhcuong.airfly.R

class Flight (res : Resources) {
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

    val flight_dead = BitmapFactory.decodeResource(res, R.drawable.flight_dead)
        get() = field

    var flightList : ArrayList<Bitmap> = arrayListOf()

    init {
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_1))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_2))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_1))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_2))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_1))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_2))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_1))
        flightList.add(BitmapFactory.decodeResource(res, R.drawable.flight_2))

        x = ScreenSize.SCREEN_WIDTH/5 - flightList[0].width/2
        y = ScreenSize.SCREEN_HEIGHT/2 - flightList[0].height/2
    }

    fun getFlight(current : Int) : Bitmap{
        return flightList.get(current)
    }
}