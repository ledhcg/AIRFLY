package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dinhcuong.airfly.R
import kotlin.random.Random

class Bird (res : Resources) {
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
    var birdList : ArrayList<Bitmap>

    init {
        birdList = arrayListOf()
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b0))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b1))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b2))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b3))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b4))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b5))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b6))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.b7))

        x = ScreenSize.SCREEN_WIDTH
        y = ran.nextInt(10,ScreenSize.SCREEN_HEIGHT/2 - 10)
    }

    fun getBird(current : Int) : Bitmap{
        return birdList.get(current)
    }
}