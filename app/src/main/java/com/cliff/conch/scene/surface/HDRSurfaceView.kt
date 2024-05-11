package com.cliff.conch.scene.surface

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.cliff.conch.R
import com.orhanobut.logger.Logger

class HDRSurfaceView(context: Context, attrs: AttributeSet? = null) :
    SurfaceView(context, attrs),
    SurfaceHolder.Callback2 {
    private var mIsHardwareAccel: Boolean = false

    init {
        try {
            val attributes =
                context.obtainStyledAttributes(attrs, R.styleable.HDRSurfaceView)
            mIsHardwareAccel =
                attributes.getBoolean(R.styleable.HDRSurfaceView_mIsHardwareAccel, false)
            attributes.recycle()
        } catch (ignore: Exception) {
        }
        holder?.setFormat(PixelFormat.RGBA_F16)
        holder?.addCallback(this)
    }


    private fun drawImage(holder: SurfaceHolder) {
        val mCanvas = if (mIsHardwareAccel) {
            holder.lockHardwareCanvas()
        } else {
            holder.lockCanvas()
        }

        val windowWidth = resources.displayMetrics.widthPixels
        val windowHeight = resources.displayMetrics.heightPixels

        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.surface, opts)
        opts.inJustDecodeBounds = false
        opts.inScaled = true
        opts.inSampleSize = calculateInSampleSize(opts, windowWidth, windowHeight)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.surface, opts)

        Logger.i("bitmapRect: width:${bitmap.width}")
        val hardwareAccelerated = mCanvas?.isHardwareAccelerated ?: false
        if (mCanvas != null && bitmap != null) {
            mCanvas.drawColor(Color.BLACK)
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            mCanvas.drawBitmap(bitmap, rect, rect, Paint())
        }
        if (hardwareAccelerated) {
            mCanvas.drawText("硬件加速Canvas", 0.0f, 50.0f, Paint(Paint.ANTI_ALIAS_FLAG))
        } else {
            mCanvas.drawText("普通Canvas", 0.0f, 50.0f, Paint(Paint.ANTI_ALIAS_FLAG))
        }
        holder.unlockCanvasAndPost(mCanvas) //解锁画布同时提交
    }

    private fun calculateInSampleSize(
        opts: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val width = opts.outWidth
        val height = opts.outHeight
        var inSampleSize = 1
        while (width / inSampleSize > reqWidth || height / inSampleSize > reqHeight) {
            inSampleSize *= 2
        }
        Logger.i("calculateInSampleSize: width:$width height:$height  reqWidth:$reqWidth reqHeight:$reqHeight")
        return inSampleSize
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        Logger.i("surfaceCreated")
        drawImage(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Logger.i("surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Logger.i("surfaceDestroyed")
    }

    override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
        Logger.i("surfaceRedrawNeeded")
    }
}