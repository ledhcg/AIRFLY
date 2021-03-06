package com.dinhcuong.airfly.UI

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dinhcuong.airfly.Activity.MainActivity
import com.dinhcuong.airfly.Thread.PlayThread

class PlayView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    private val TAG = "PlayView"
    private var playThread : PlayThread? = null

    init {
        val holder = holder
        holder.addCallback(this)
        isFocusable = true
        playThread = context?.let { PlayThread(it, holder, resources) }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val ev = event!!.action
        if(ev == MotionEvent.ACTION_DOWN){
            //Fly, you fool!
                if (playThread?.isRunning == true){
                    playThread?.Jump()
                } else {
                    val main = Intent(context, MainActivity::class.java)
                    context.startActivity(main)
                }

        }
        return true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        if(!playThread!!.isRunning){
            playThread = p0.let {
                PlayThread(context, it!!, resources)
            }
        }else{
            playThread!!.start()
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        if(playThread!!.isRunning){
            playThread!!.isRunning = false
            var isCheck : Boolean = true
            while (isCheck) {
                try{
                    playThread!!.join()
                }catch (e : InterruptedException) {

                }
            }
        }
    }
}