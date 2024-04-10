package com.cliff.conch.scene.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.orhanobut.logger.Logger

class SelfButton(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.buttonStyle,
) : AppCompatButton(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        androidx.appcompat.R.attr.buttonStyle
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d(ev?.action)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.d(event?.action)
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Logger.d("onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Logger.d("onLayout")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Logger.d("onDraw")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Logger.d("onAttachedToWindow")
    }

}