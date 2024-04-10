package com.cliff.conch.scene.event

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.orhanobut.logger.Logger

class CliffLeanerLayout(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.i("CliffLeanerLayout-dispatchTouchEvent-begin")
        val dispatch = super.dispatchTouchEvent(ev)
        Logger.i("CliffLeanerLayout-dispatchTouchEvent-end-$dispatch")
        return dispatch
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Logger.i("CliffLeanerLayout-onInterceptTouchEvent-begin")
        val intercept = super.onInterceptTouchEvent(ev)
        Logger.i("CliffLeanerLayout-onInterceptTouchEvent-end-$intercept")
        return intercept
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        Logger.i("CliffLeanerLayout-onTouchEvent-begin-${ev?.action}")
        val onTouchEvent = super.onTouchEvent(ev)
        Logger.i("CliffLeanerLayout-onTouchEvent-end-$onTouchEvent")
        return onTouchEvent
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}