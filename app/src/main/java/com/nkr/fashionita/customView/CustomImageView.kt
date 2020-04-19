package com.nkr.fashionita.customView

import android.R.attr.radius
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView


class CustomImageView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ImageView(context, attrs) {



    private val mBitmapDrawBounds : RectF

    init {
        mBitmapDrawBounds = RectF()
    }


    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        color = Color.LTGRAY
        strokeCap = Paint.Cap.ROUND

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val radius = 6.0f
        val rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())

        canvas?.drawRoundRect(rect, radius, radius, linePaint)
        /*
        val clipPath = Path()
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas?.clipPath(clipPath)*/
    }



}