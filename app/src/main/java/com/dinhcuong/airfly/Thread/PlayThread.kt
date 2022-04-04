package com.dinhcuong.airfly.Thread

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import com.dinhcuong.airfly.Model.*
import com.dinhcuong.airfly.R
import kotlin.collections.ArrayList
import kotlin.random.Random
import com.dinhcuong.airfly.MainActivity


class PlayThread : Thread {

    private val TAG: String = "PlayThread"
    private var holder: SurfaceHolder
    private var resources: Resources


    private val FPS: Int = (1000.0 / 60.0).toInt() //Time passed per frame. 60 FPS
    private val backgroundImage = BackgroundImage() //Hello, new object
    private var bitmapImage: Bitmap? = null
    private val paint: Paint
    private val textBounds: Rect = Rect()
    private var startTime: Long = 0
    private var frameTime: Long = 0

    private var killBird: Int = 0
    private var score: Int = 0


    private val bird: Bird
    var birdArray: ArrayList<Bird> = arrayListOf()
    val numBird = 5
    var iBird = 0

    private val flight: Flight
    private val velocity = 3
    private var velocityFlight: Int = 0

    private var bullet: Bullet
    private var velocityBullet: Int = 20
    var bulletArray: ArrayList<Bullet> = arrayListOf()
    val numBullet = 16
    var iBullet = 0


    //Game state: 0 = Stop; 1 = Running; 2 = Game Over
    private var state: Int = 0
    private var velocityBird: Int = 10


    var pipe: Pipe? = null
    val numPipe = 2
    val velocityPipe = 10
    val minY = 250
    val maxY = ScreenSize.SCREEN_HEIGHT - minY - 500
    val kc = ScreenSize.SCREEN_WIDTH * 3 / 4
    var pipeArray: ArrayList<Pipe> = arrayListOf()
    var ran: Random = Random

    var iPipe = 0
    var isDead = false


    var isRunning: Boolean = false //Flag to Run or Stop
        get() = field
        set(value) {
            field = value
        }


    constructor(
        holder: SurfaceHolder,
        resources: Resources
    ) {
        this.holder = holder
        this.resources = resources
        isRunning = true

        flight = Flight(resources)

        bird = Bird(resources)
        createBird(resources)


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

        //Pipes or otherwise known as insidious Mario Pipes
        pipe = Pipe(resources)
        createPipe(resources)
    }

    private fun createBird(resources: Resources) {
        for (i in 0 until numBird) {
            val bird = Bird(resources)
            bird.x = ScreenSize.SCREEN_WIDTH
            bird.y = ScreenSize.SCREEN_HEIGHT - ran.nextInt(300, 1000)
            birdArray.add(bird)
        }
    }

    private fun createBullet(resources: Resources) {
        for (i in 0 until numBullet) {
            val bullet = Bullet(resources)
            bullet.x = flight.x + 150
            bullet.y = flight.y + ran.nextInt(150, 200)
            bulletArray.add(bullet)
        }
    }

    private fun createPipe(resources: Resources) {
        for (i in 0 until numPipe) {
            val pipe = Pipe(resources)
            pipe.x = ScreenSize.SCREEN_WIDTH + kc * i
            pipe.ccY = ran.nextInt(maxY - minY) + minY
            pipeArray.add(pipe)
        }
    }

    private fun collisionHandle() {
        //Collision detection (Bird and Flight) or (Bird and LeftScreen)
        for (i in 0 until numBird) {
            if (isCollisionDetected(
                    birdArray.get(i).getBird(0), birdArray.get(i).x, birdArray.get(i).y,
                    flight.getFlight(0), flight.x, flight.y
                ) || birdArray.get(i).x < 0
            ) {
                isDead = true
            }
        }

        //Collision detection Pipe and Flight
        if (
            isCollisionDetected(
                pipeArray.get(iPipe).pipeTop,
                pipeArray.get(iPipe).x,
                pipeArray.get(iPipe).getTopY(),
                flight.getFlight(0),
                flight.x,
                flight.y
            )
            || isCollisionDetected(
                pipeArray.get(iPipe).pipeBottom,
                pipeArray.get(iPipe).x,
                pipeArray.get(iPipe).getBottomY(),
                flight.getFlight(0),
                flight.x,
                flight.y
            )
        )
            isDead = true

        //Collision detection Bird and Bullet
        for (i in 0 until numBullet) {
            for (j in 0 until numBird) {
                if (
                    isCollisionDetected(
                        bulletArray.get(i).bullet,
                        bulletArray.get(i).x,
                        bulletArray.get(i).y,
                        birdArray.get(j).getBird(0),
                        birdArray.get(j).x,
                        birdArray.get(j).y
                    )
                ) {
                    birdArray.get(j).dead = true
//                    birdArray.get(j).x = ScreenSize.SCREEN_WIDTH
//                    birdArray.get(j).y = ScreenSize.SCREEN_HEIGHT - ran.nextInt(300, 1000)

                    bulletArray.get(i).x = flight.x + 150
                    bulletArray.get(i).y = flight.y + 160
                    killBird++
                }
            }
        }

        //Collision detection Flight and BottomScreen
        if (flight.y >= (ScreenSize.SCREEN_HEIGHT - flight.getFlight(0).height)) {
            isDead = true
        }
    }

    override fun run() {
        Log.d(TAG, "Thread Started")

        while (isRunning) {
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas()
            if (canvas != null) {
                try {
                    synchronized(holder) {

                        renderBackground(canvas)

                        collisionHandle()
                        flightDeath(canvas)

                        //rendering the pipes. Damn pipes.
                        renderPipe(canvas)
                        //rendering the bird on canvas
                        renderBird(canvas)
                        renderFlight(canvas)

                        canvas.drawText("Bird killed: $killBird | Score: $score", 100f, 100f, paint)
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

    private fun flightDeath(canvas: Canvas?) {
        if (isDead) {
            canvas!!.drawBitmap(
                flight.flight_dead,
                flight.x.toFloat(),
                flight.y.toFloat(),
                null
            )
            showGameOver(canvas)
            isRunning = false
        }
    }

    private fun renderPipe(canvas: Canvas?) {
        if (state == 1) { //if the game is running
            if (pipeArray.get(iPipe).x < flight.x - pipe!!.w) {
                iPipe++
                score++
                if (iPipe > numPipe - 1) {
                    iPipe = 0
                }
            }

            for (i in 0 until numPipe) {// 0, 1, 2
                if (pipeArray.get(i).x < -pipe!!.w) {

                    //creating a new pipe with x = kc + old_pipe
                    pipeArray.get(i).x = pipeArray.get(i).x + numPipe * kc
                    //pipe.y is going to be random
                    pipeArray.get(i).ccY = ran.nextInt(maxY - minY) + minY

                }
                //moving pipe right to left
                if (!isDead) {
                    pipeArray.get(i).x = pipeArray.get(i).x - velocityPipe
                }

                //rendering top pipes
                canvas!!.drawBitmap(
                    pipe!!.pipeTop,
                    pipeArray.get(i).x.toFloat(),
                    pipeArray.get(i).getTopY().toFloat(),
                    null
                )

                //rendering bottom pipe (pipeTop.x = pipeBottom.x)
                canvas!!.drawBitmap(
                    pipe!!.pipeBottom,
                    pipeArray.get(i).x.toFloat(),
                    pipeArray.get(i).getBottomY().toFloat(),
                    null
                )

            }
        }
    }

    private fun renderBird(canvas: Canvas?) {
        if (state == 1) {
            for (i in 0 until numBird) {

                if (birdArray.get(i).y > ScreenSize.SCREEN_HEIGHT) {
                    birdArray.get(i).dead = false
                    birdArray.get(i).x = ScreenSize.SCREEN_WIDTH
                    birdArray.get(i).y = ScreenSize.SCREEN_HEIGHT - ran.nextInt(300, 1000)
                }

                if (!isDead) {
                    if (birdArray.get(i).dead) {
                        birdArray.get(i).y = birdArray.get(i).y + 30
                    } else {
                        birdArray.get(i).x = birdArray.get(i).x - ran.nextInt(5, 20)
                    }
                }

                var current: Int = birdArray.get(i).currentFrame
                if (birdArray.get(i).dead) {
                    canvas!!.drawBitmap(
                        birdArray.get(i).bird_dead,
                        birdArray.get(i).x.toFloat(),
                        birdArray.get(i).y.toFloat(),
                        null
                    )
                } else {
                    canvas!!.drawBitmap(
                        birdArray.get(i).getBird(current),
                        birdArray.get(i).x.toFloat(),
                        birdArray.get(i).y.toFloat(),
                        null
                    )
                }

                current++
                if (current > birdArray.get(i).maxFrame)
                    current = 0
                birdArray.get(i).currentFrame = current

//                var x = birdArray.get(i).x
//                var y = birdArray.get(i).y
//                var h = ScreenSize.SCREEN_HEIGHT
//                Log.d("CHIM","Vi tri chim $i: [$x, $y] - Height: $h")
            }
        }
    }

    private fun renderFlight(canvas: Canvas?) {
        if (state == 1) {
            if (!isDead) {
                if (flight.y < (ScreenSize.SCREEN_HEIGHT - flight.getFlight(0).height)) {
                    velocityFlight += 2 // fall down
                    flight.y = flight.y + velocityFlight // fly up
                    renderBullet(canvas)
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
            if (bulletArray.get(iBullet).x > ScreenSize.SCREEN_WIDTH) {
                iBullet++
                if (iBullet > numBullet - 1) {
                    iBullet = 0
                }
            }


            for (i in 0 until numBullet) {// 0, 1, 2
                if (bulletArray.get(i).x < flight.x || bulletArray.get(i).x > ScreenSize.SCREEN_WIDTH) {
                    //creating a new pipe with x = kc + old_pipe
                    bulletArray.get(i).x = flight.x + 150
                    //pipe.y is going to be random
                    bulletArray.get(i).y = flight.y + 160
                }
                //moving pipe right to left
                if (!isDead) {
                    bulletArray.get(i).x =
                        bulletArray.get(i).x + velocityBullet * ran.nextInt(1, 10)
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
    private fun renderBackground(canvas: Canvas?) {
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
        val ratio: Float = (bitmap.width / bitmap.height).toFloat()
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

    private fun showGameOver(canvas: Canvas?) {
        val paint: Paint = Paint()
        val customTypeface = resources.getFont(R.font.primary)
        paint.apply {
            flags = Paint.ANTI_ALIAS_FLAG
            this.color = Color.BLACK
            this.textSize = 200f
            this.typeface = customTypeface
            setShadowLayer(1f, 5f, 5f, Color.WHITE)
        }
        val textGameOver: String = "GAME OVER"
        paint.getTextBounds(textGameOver, 0, textGameOver.length, textBounds);
        canvas!!.drawText(
            textGameOver, ScreenSize.SCREEN_WIDTH / 2 - textBounds.exactCenterX(),
            ScreenSize.SCREEN_HEIGHT / 2 - textBounds.exactCenterY(), paint
        )
    }


    // START - Collision Detection
    private fun isCollisionDetected(
        view1: Bitmap, x1: Int, y1: Int,
        view2: Bitmap, x2: Int, y2: Int
    ): Boolean {
        var bitmap1: Bitmap = view1
        var bitmap2: Bitmap = view2
        val bounds1: Rect = Rect(x1, y1, x1 + bitmap1.getWidth(), y1 + bitmap1.getHeight())
        val bounds2: Rect = Rect(x2, y2, x2 + bitmap2.getWidth(), y2 + bitmap2.getHeight())
        if (Rect.intersects(bounds1, bounds2)) {
            val collisionBounds: Rect = getCollisionBounds(bounds1, bounds2)
            for (i in collisionBounds.left until collisionBounds.right) {
                for (j in collisionBounds.top until collisionBounds.bottom) {
                    val bitmap1Pixel: Int = bitmap1.getPixel(i - x1, j - y1)
                    val bitmap2Pixel: Int = bitmap2.getPixel(i - x2, j - y2)
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun getCollisionBounds(rect1: Rect, rect2: Rect): Rect {
        val left: Int = Math.max(rect1.left, rect2.left)
        val top: Int = Math.max(rect1.top, rect2.top)
        val right: Int = Math.min(rect1.right, rect2.right)
        val bottom: Int = Math.min(rect1.bottom, rect2.bottom)
        return Rect(left, top, right, bottom)
    }

    private fun isFilled(pixel: Int): Boolean {
        return pixel != Color.TRANSPARENT
    }
    // END - Collision Detection
}