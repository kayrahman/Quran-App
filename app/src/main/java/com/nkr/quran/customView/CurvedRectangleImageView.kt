package com.nkr.quran.customView

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


class CurvedRectangleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ImageView(context, attrs) {
/*

    private var mBitmapShader: Shader? = null
    private val mShaderMatrix: Matrix

    private val mBitmapDrawBounds: RectF
    private val mStrokeBounds: RectF

    private var mBitmap: Bitmap? = null

    private val mBitmapPaint: Paint
    private val mStrokePaint: Paint
    private val mPressedPaint: Paint

    private val mInitialized: Boolean
    private var mPressed: Boolean = false
    private var mHighlightEnable: Boolean = false

    var isHighlightEnable: Boolean
        get() = mHighlightEnable
        set(enable) {
            mHighlightEnable = enable
            invalidate()
        }

    var highlightColor: Int
        @ColorInt
        get() = mPressedPaint.color
        set(@ColorInt color) {
            mPressedPaint.color = color
            invalidate()
        }

    var strokeColor: Int
        @ColorInt
        get() = mStrokePaint.color
        set(@ColorInt color) {
            mStrokePaint.color = color
            invalidate()
        }

    var strokeWidth: Float
        @Dimension
        get() = mStrokePaint.strokeWidth
        set(@Dimension width) {
            mStrokePaint.strokeWidth = width
            invalidate()
        }

    init {

        var strokeColor = Color.TRANSPARENT
        var strokeWidth = 0f
        var highlightEnable = true
        var highlightColor = DEF_PRESS_HIGHLIGHT_COLOR

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0)

            strokeColor = a.getColor(R.styleable.CircleImageView_strokeColor, Color.TRANSPARENT)
            strokeWidth =
                a.getDimensionPixelSize(R.styleable.CircleImageView_strokeWidth, 0).toFloat()
            highlightEnable = a.getBoolean(R.styleable.CircleImageView_highlightEnable, true)
            highlightColor =
                a.getColor(R.styleable.CircleImageView_highlightColor, DEF_PRESS_HIGHLIGHT_COLOR)

            a.recycle()
        }

        mShaderMatrix = Matrix()
        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mStrokeBounds = RectF()
        mBitmapDrawBounds = RectF()
        mStrokePaint.color = strokeColor
        mStrokePaint.style = Paint.Style.STROKE
        mStrokePaint.strokeWidth = strokeWidth

        mPressedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPressedPaint.color = highlightColor
        mPressedPaint.style = Paint.Style.FILL

        mHighlightEnable = highlightEnable
        mInitialized = true

        setupBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        setupBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        setupBitmap()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        setupBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        setupBitmap()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val halfStrokeWidth = mStrokePaint.strokeWidth / 2f
        updateCircleDrawBounds(mBitmapDrawBounds)
        mStrokeBounds.set(mBitmapDrawBounds)
        mStrokeBounds.inset(halfStrokeWidth, halfStrokeWidth)

        updateBitmapSize()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var processed = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isInCircle(event.x, event.y)) {
                    return false
                }
                processed = true
                mPressed = true
                invalidate()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                processed = true
                mPressed = false
                invalidate()
                if (!isInCircle(event.x, event.y)) {
                    return false
                }
            }
        }
        return super.onTouchEvent(event) || processed
    }

    override fun onDraw(canvas: Canvas) {
        drawBitmap(canvas)
        drawStroke(canvas)
        drawHighlight(canvas)
    }

    protected fun drawHighlight(canvas: Canvas) {
        if (mHighlightEnable && mPressed) {
            canvas.drawOval(mBitmapDrawBounds, mPressedPaint)
        }
    }

    protected fun drawStroke(canvas: Canvas) {
        if (mStrokePaint.strokeWidth > 0f) {
            canvas.drawOval(mStrokeBounds, mStrokePaint)
        }
    }

    protected fun drawBitmap(canvas: Canvas) {
       // canvas.drawOval(mBitmapDrawBounds, mBitmapPaint)
       // canvas.drawRoundRect(mBitmapDrawBounds,10F,10F, mBitmapPaint)
       // canvas.drawRect(mBitmapDrawBounds,10F,10F, mBitmapPaint)


        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }
        val b = (drawable as BitmapDrawable).bitmap
        val bitmap = b.copy(Bitmap.Config.ARGB_8888, true)

        val w = width
        val h = height

        val roundBitmap = getRoundedCroppedBitmap(bitmap, w)
        canvas.drawBitmap(roundBitmap, 0F, 0F, null)



    }


    fun getRoundedCroppedBitmap(bitmap: Bitmap, radius: Int): Bitmap {
        val finalBitmap: Bitmap
        if (bitmap.width != radius || bitmap.height != radius)
            finalBitmap = Bitmap.createScaledBitmap(
                bitmap, radius, radius,
                false
            )
        else
            finalBitmap = bitmap
        val output = Bitmap.createBitmap(
            finalBitmap.width,
            finalBitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)

        val paint = Paint()
        val rect = Rect(
            0, 0, finalBitmap.width,
            finalBitmap.height
        )

        val point1_draw = Point(75, 0)
        val point2_draw = Point(0, 50)
        val point3_draw = Point(0, 100)
        val point4_draw = Point(75, 150)
        val point5_draw = Point(150, 100)
        val point6_draw = Point(150, 50)

        val path = Path()
        path.moveTo(point1_draw.x.toFloat(), point1_draw.y.toFloat())
        path.lineTo(point2_draw.x.toFloat(), point2_draw.y.toFloat())
        path.lineTo(point3_draw.x.toFloat(), point3_draw.y.toFloat())
        path.lineTo(point4_draw.x.toFloat(), point4_draw.y.toFloat())
        path.lineTo(point5_draw.x.toFloat(), point5_draw.y.toFloat())
        path.lineTo(point6_draw.x.toFloat(), point6_draw.y.toFloat())

        path.close()
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = Color.parseColor("#BAB399")
        canvas.drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(finalBitmap, rect, rect, paint)

        return output
    }


    protected fun updateCircleDrawBounds(bounds: RectF) {
        val contentWidth = (width - paddingLeft - paddingRight).toFloat()
        val contentHeight = (height - paddingTop - paddingBottom).toFloat()

        var left = paddingLeft.toFloat()
        var top = paddingTop.toFloat()
        if (contentWidth > contentHeight) {
            left += (contentWidth - contentHeight) / 2f
        } else {
            top += (contentHeight - contentWidth) / 2f
        }

        val diameter = Math.min(contentWidth, contentHeight)
        bounds.set(left, top, left + diameter, top + diameter)
    }

    private fun setupBitmap() {
        if (!mInitialized) {
            return
        }
        mBitmap = getBitmapFromDrawable(drawable)
        if (mBitmap == null) {
            return
        }

        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBitmapPaint.shader = mBitmapShader

        updateBitmapSize()
    }

    private fun updateBitmapSize() {
        if (mBitmap == null) return

        val dx: Float
        val dy: Float
        val scale: Float

        // scale up/down with respect to this view size and maintain aspect ratio
        // translate bitmap position with dx/dy to the center of the image
        if (mBitmap!!.width < mBitmap!!.height) {
            scale = mBitmapDrawBounds.width() / mBitmap!!.width.toFloat()
            dx = mBitmapDrawBounds.left
            dy =
                mBitmapDrawBounds.top - mBitmap!!.height * scale / 2f + mBitmapDrawBounds.width() / 2f
        } else {
            scale = mBitmapDrawBounds.height() / mBitmap!!.height.toFloat()
            dx =
                mBitmapDrawBounds.left - mBitmap!!.width * scale / 2f + mBitmapDrawBounds.width() / 2f
            dy = mBitmapDrawBounds.top
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(dx, dy)
        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    private fun isInCircle(x: Float, y: Float): Boolean {
        // find the distance between center of the view and x,y point
        val distance = Math.sqrt(
            Math.pow(
                (mBitmapDrawBounds.centerX() - x).toDouble(),
                2.0
            ) + Math.pow((mBitmapDrawBounds.centerY() - y).toDouble(), 2.0)
        )
        return distance <= mBitmapDrawBounds.width() / 2
    }

    companion object {


        private val DEF_PRESS_HIGHLIGHT_COLOR = 0x32000000
    }*/
}


