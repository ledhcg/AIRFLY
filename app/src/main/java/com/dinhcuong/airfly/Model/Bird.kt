package com.dinhcuong.airfly.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
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
    var birdList : ArrayList<Bitmap> = arrayListOf()

    var dead: Boolean = false

    var bird_dead = BitmapFactory.decodeResource(res, R.drawable.bird_dead)
        get() = field

    init {
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_1))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_2))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_3))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_4))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_5))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_6))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_7))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.bird_8))

        x = ScreenSize.SCREEN_WIDTH
        y = ran.nextInt(10,ScreenSize.SCREEN_HEIGHT/2 - 10)
    }

    fun getBird(current : Int) : Bitmap{
        return birdList.get(current)
    }
}