package com.andylai.bounceviewdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View


class BounceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // moving distance from current x/y
    private var dx = 2f
    private var dy = 5f
    // if moving distance of object is {PointF distance},
    // smaller delta (for x) means it hit (x)side before other (y)side
    private var deltaX = 0f
    private var deltaY = 0F
    // current position of object
    private var xCurrent = 0f
    private var yCurrent = 0f

    private var xLeft = 0
    private var yTop = 0
    private var xRight = 0
    private var yBottom = 0

    private var bmp: Bitmap
    private val imageResId = R.mipmap.batman

    init {
        bmp = BitmapFactory.decodeResource(resources, imageResId)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        xLeft = left
        yTop = top
        xRight = right - resources.getDrawable(imageResId, null).minimumWidth
        yBottom = bottom - resources.getDrawable(imageResId, null).minimumHeight
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val bmp = BitmapFactory.decodeResource(resources, R.mipmap.batman)
        if (dx != 0f || dy != 0f) {
            updateCurrentXY()
            canvas.drawBitmap(bmp, xCurrent, yCurrent, null)
            invalidate()
        }
    }

    private fun updateCurrentXY() {
        deltaX = getDelta().x
        deltaY = getDelta().y
        if (deltaX > deltaY) {
            if ((dy > 0 && yCurrent - yBottom >= 0) || (dy < 0 && yCurrent - yTop <= 0))
                dy = -dy
        } else {
            if ((dx > 0 && xCurrent - xRight >= 0) || (dx < 0 && xCurrent - xLeft <= 0))
                dx = -dx
        }
        xCurrent += dx
        yCurrent += dy
    }

    private fun getDelta(): PointF {
        val xBound = if (dx > 0) xRight else xLeft
        val yBound = if (dy > 0) yBottom else yTop
        deltaX = (xBound - xCurrent) / dx
        deltaY = (yBound - yCurrent) / dy
        return PointF(0f, 0f)
    }
}