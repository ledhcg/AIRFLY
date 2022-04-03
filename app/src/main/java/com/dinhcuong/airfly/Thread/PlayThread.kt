package com.dinhcuong.airfly.Thread

import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import com.dinhcuong.airfly.Model.*
import com.dinhcuong.airfly.R
import kotlin.collections.ArrayList
import kotlin.random.Random
import android.graphics.Typeface
import java.util.*


class PlayThread : Thread {

    private val TAG: String = "PlayThread"
    private var holder: SurfaceHolder
    private var resources: Resources


    private val FPS: Int = (1000.0 / 60.0).toInt() //Time passed per frame. 60 FPS
    private val backgroundImage = BackgroundImage() //Hello, new object
    private var bitmapImage: Bitmap? = null
    private var startTime: Long = 0
    private var frameTime: Long = 0
    private val velocity = 3
    private val birb: Birb //birb model that will fly

    private val flight: Flight

    private var bullet: Bullet
    var bulletArray: ArrayList<Bullet> = arrayListOf()
    val numBullet = 16
    var iBullet = 0

    //Game state: 0 = Stop; 1 = Running; 2 = Game Over
    private var state: Int = 0
    private var velocityBirb: Int = 0
    private var velocityFlight: Int = 0
    private var velocityBullet: Int = 20


    var cot: Cot? = null
    val numCot = 2
    val velocityCot = 10
    val minY = 250
    val maxY = ScreenSize.SCREEN_HEIGHT - minY - 500
    val kc = ScreenSize.SCREEN_WIDTH * 3 / 4
    var cotArray: ArrayList<Cot> = arrayListOf()
    var ran: Random = Random

    var iCot = 0
    var isDead = false


    private val paint: Paint


    var isRunning: Boolean = false //Flag to Run or Stop
        get() = field
        set(value) {
            field = value
        }


    constructor(holder: SurfaceHolder, resources: Resources) {
        this.holder = holder
        this.resources = resources
        isRunning = true


        birb = Birb(resources)
        flight = Flight(resources)

        bullet = Bullet(resources)
        createBullet(resources)

        paint = Paint()
        val customTypeface = resources.getFont(R.font.secondary)
        paint.apply {
            flags = Paint.ANTI_ALIAS_FLAG
            this.color = Color.RED
            this.textSize = 64f
            this.typeface = customTypeface
            setShadowLayer(1f, 0f, 1f, Color.WHITE)
        }

        //background of the game
        bitmapImage = BitmapFactory.decodeResource(resources, R.drawable.background_image)
        bitmapImage = this.bitmapImage?.let { ScaleResize(it) }

        //Cots or otherwise known as insidious Mario Pipes
        cot = Cot(resources)
        createCot(resources)
    }

    private fun createBullet(resources: Resources) {
        for (i in 0 until numBullet) {
            val bullet = Bullet(resources)
            bullet.x = flight.x + 150
            bullet.y = flight.y + ran.nextInt(150, 200)
            bulletArray.add(bullet)
        }
        Log.d("This is my array", "arr: " + bulletArray);
    }

    private fun createCot(resources: Resources) {
        for (i in 0 until numCot) {
            val cot = Cot(resources)
            cot.x = ScreenSize.SCREEN_WIDTH + kc * i
            cot.ccY = ran.nextInt(maxY - minY) + minY
            cotArray.add(cot)
        }
    }

    override fun run() {
        Log.d(TAG, "Thread Started")

        while (isRunning) {
            if (holder == null) return
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas()
            if (canvas != null) {
                try {
                    synchronized(holder) {
                        //rendering the background
                        render(canvas)


                        //rendering the pipes. Damn pipes.
                        renderCot(canvas)

                        //rendering the birb on canvas
                        renderBirb(canvas)

                        renderFlight(canvas)

                        canvas.drawText("FPS: $FPS", 100f, 100f, paint)
                    }
                } finally {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
            frameTime = (System.nanoTime() - startTime) / 1000000
            if (frameTime < FPS) {
                try {
                    Thread.sleep(FPS - frameTime)
                } catch (e: InterruptedException) {
                    Log.e("Interrupted Stuff", "Thread is asleep. Error.")
                }
            }
        }
        Log.d(TAG, "Thread has reached its finale. Zargothrax Approves.")
    }

    private fun flightDeath() {
        if (isDead) {
            isRunning = false
        }
    }

    private fun renderCot(canvas: Canvas?) {
        if (state == 1) { //if the game is running
            if (cotArray.get(iCot).x < flight.x - cot!!.w) {
                iCot++
                if (iCot > numCot - 1) {
                    iCot = 0
                }
            } else if (((cotArray.get(iCot).x) < flight.getFlight(0).width) &&
                (cotArray.get(iCot).ccY > flight.y || cotArray.get(iCot)
                    .getBottomY() < flight.y + flight.getFlight(0).height)
            )
                isDead = true

            for (i in 0 until numCot) {// 0, 1, 2
                if (cotArray.get(i).x < -cot!!.w) {
                    //creating a new cot with x = kc + old_cot
                    cotArray.get(i).x = cotArray.get(i).x + numCot * kc
                    //cot.y is going to be random
                    cotArray.get(i).ccY = ran.nextInt(maxY - minY) + minY

                }
                //moving cot right to left
                if (!isDead) {
                    cotArray.get(i).x = cotArray.get(i).x - velocityCot
                }

                //rendering top pipes
                canvas!!.drawBitmap(
                    cot!!.cotTop,
                    cotArray.get(i).x.toFloat(),
                    cotArray.get(i).getTopY().toFloat(),
                    null
                )

                //rendering bottom pipe (cotTop.x = cotBottom.x)
                canvas!!.drawBitmap(
                    cot!!.cotBottom,
                    cotArray.get(i).x.toFloat(),
                    cotArray.get(i).getBottomY().toFloat(),
                    null
                )

            }
        }
    }

    private fun renderBirb(canvas: Canvas?) {
        if (!isDead) {
            var current: Int = birb.currentFrame
            birb.x = birb.x - 6
            birb.y = birb.y - ran.nextInt(1,5) + ran.nextInt(1,5)

            canvas!!.drawBitmap(birb.getBirb(current), birb.x.toFloat(), birb.y.toFloat(), null)

            current++
            if (current > birb.maxFrame)
                current = 0
            birb.currentFrame = current
        }
        Log.d(TAG, "Render bird")
    }

    private fun renderFlight(canvas: Canvas?) {
        if (state == 1) {
            if (!isDead) {
                if (flight.y < (ScreenSize.SCREEN_HEIGHT - flight.getFlight(0).height)) {
                    velocityFlight += 2 // fall down
                    flight.y = flight.y + velocityFlight // fly up
                    renderBullet(canvas)

                } else {
//                    isDead = true
                }
            }
        }
        if (!isDead) {
            var current: Int = flight.currentFrame
            canvas!!.drawBitmap(
                flight.getFlight(current),
                flight.x.toFloat(),
                flight.y.toFloat(),
                null
            )

            current++
            if (current > flight.maxFrame)
                current = 0
            flight.currentFrame = current
        }
        Log.d(TAG, "Render flight")
    }

    private fun renderBullet(canvas: Canvas?) {
        if (state == 1) { //if the game is running
            if (bulletArray.get(iBullet).x > 900) {
                iBullet++
                if (iBullet > numBullet - 1) {
                    iBullet = 0
                }
            }


            for (i in 0 until numBullet) {// 0, 1, 2
                if (bulletArray.get(i).x < flight.x || bulletArray.get(i).x > ScreenSize.SCREEN_WIDTH) {
                    //creating a new cot with x = kc + old_cot
                    bulletArray.get(i).x = flight.x + 150
                    //cot.y is going to be random
                    bulletArray.get(i).y = flight.y + 160
                }
                //moving cot right to left
                if (!isDead) {
                    bulletArray.get(i).x = bulletArray.get(i).x + velocityBullet*ran.nextInt(1, 10)
                }

                canvas!!.drawBitmap(
                    bullet!!.bullet,
                    bulletArray.get(i).x.toFloat(),
                    bulletArray.get(i).y.toFloat(),
                    null
                )

            }
        }
    }

    //Here we are rendering the background
    private fun render(canvas: Canvas?) {
        Log.d(TAG, "Render Canvas")
        if (!isDead) {
            backgroundImage.x = backgroundImage.x - velocity
        }
        if (backgroundImage.x < -bitmapImage!!.width) {
            backgroundImage.x = 0
        }
        bitmapImage?.let {
            canvas!!.drawBitmap(
                it,
                (backgroundImage.x).toFloat(),
                (backgroundImage.y).toFloat(),
                null
            )
        }

        //Looping that image so it no longer cuts at a wonky state.
        if (backgroundImage.x < -(bitmapImage!!.width - ScreenSize.SCREEN_WIDTH)) {
            bitmapImage?.let {
                canvas!!.drawBitmap(
                    it,
                    (backgroundImage.x + bitmapImage!!.width).toFloat(),
                    (backgroundImage.y).toFloat(),
                    null
                )
            }
        }

    }

    //Let's make the screen fit into the application's full screen
    private fun ScaleResize(bitmap: Bitmap): Bitmap {
        var ratio: Float = (bitmap.width / bitmap.height).toFloat()
        val scaleWidth: Int = (ratio * ScreenSize.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap, scaleWidth, ScreenSize.SCREEN_HEIGHT, false)

    }

    fun Jump() {
        state = 1


        //Top Screen Fixed. No more flying through it.
        if (flight.y > 0) {
            velocityFlight = -30
        }
    }

}