package com.cliff.conch.scene.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.orhanobut.logger.Logger

class SelfTextView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        android.R.attr.textViewStyle
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d(ev?.action)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        Logger.d(ev?.action)
        return super.onTouchEvent(ev)
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Logger.d("onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            defaultSize(suggestedMinimumWidth, widthMeasureSpec),
            defaultSize(suggestedMinimumHeight, heightMeasureSpec, false)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        Logger.d("onLayout")
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
//        Logger.d("onDraw")
        super.onDraw(canvas)
    }


    private fun defaultSize(size: Int, measureSpec: Int, isWidth:Boolean = true): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        val result = when (specMode) {
            MeasureSpec.AT_MOST -> specSize   // TODO 如果是完全自定义View这里要根据isWidth字段需要重新写
            MeasureSpec.EXACTLY -> specSize
            else -> size
        }
        return result
    }
}