package nl.entreco.giddyapp.libcropper

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import nl.entreco.giddyapp.libcropper.util.AspectRatioUtil
import nl.entreco.giddyapp.libcropper.util.HandleUtil
import nl.entreco.giddyapp.libcropper.util.PaintUtil
import nl.entreco.giddyapp.libcropper.window.edge.Edge
import nl.entreco.giddyapp.libcropper.window.handle.Handle
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class CropImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val guidelines_off = 0
        private const val guidelines_on_touch = 1
        private const val guidelines_on = 2
    }

    private val borderPaint = PaintUtil.newBorderPaint(context, resources)
    private val guideLinePaint = PaintUtil.newGuidelinePaint(context, resources)
    private val surroundPaint = PaintUtil.newSurroundingAreaOverlayPaint(context, resources)
    private val cornerPaint = PaintUtil.newCornerPaint(context, resources)

    private val handleRadius = resources.getDimension(R.dimen.target_radius)
    private val snapRadius = resources.getDimension(R.dimen.snap_radius)
    private val borderThickness = resources.getDimension(R.dimen.border_thickness)
    private val cornerThickness = resources.getDimension(R.dimen.corner_thickness)
    private val cornerLength = resources.getDimension(R.dimen.corner_length)

    var guidelinesMode: Int = guidelines_on
        set(value) {
            field = value
            invalidate()
        }

    var fixAspectRation: Boolean = false
        set(value) {
            field = value
            requestLayout()
        }

    private var aspectRatioX: Int
    private var aspectRatioY: Int
    private var bitmapRect = RectF()
    private var touchOffset = PointF()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CropImageView, 0, 0)
        guidelinesMode = typedArray.getInteger(R.styleable.CropImageView_guidelines, 1)
        fixAspectRation = typedArray.getBoolean(R.styleable.CropImageView_fixAspectRatio, false)
        aspectRatioX = typedArray.getInteger(R.styleable.CropImageView_aspectRatioX, 1)
        aspectRatioY = typedArray.getInteger(R.styleable.CropImageView_aspectRatioY, 1)
        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bitmapRect = getBitmapRect()
        initCropWindow(bitmapRect)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawDarkenedSurroundingArea(canvas)
        drawGuidelines(canvas)
        drawBorder(canvas)
        drawCorners(canvas)
    }

    private fun drawDarkenedSurroundingArea(canvas: Canvas?) {
        val bmp = bitmapRect
        val left = Edge.LEFT.coordinate
        val top = Edge.TOP.coordinate
        val right = Edge.RIGHT.coordinate
        val bottom = Edge.BOTTOM.coordinate

        canvas?.drawRect(bmp.left, bmp.top, bmp.right, top, surroundPaint)
        canvas?.drawRect(bmp.left, bottom, bmp.right, bmp.bottom, surroundPaint)
        canvas?.drawRect(bmp.left, top, left, bottom, surroundPaint)
        canvas?.drawRect(right, top, bmp.right, bottom, surroundPaint)
    }

    private fun drawGuidelines(canvas: Canvas?) {
        val left = Edge.LEFT.coordinate
        val top = Edge.TOP.coordinate
        val right = Edge.RIGHT.coordinate
        val bottom = Edge.BOTTOM.coordinate


        // Draw vertical guides
        val oneThirdCropWidht = Edge.width() / 3

        val x1 = left + oneThirdCropWidht
        canvas?.drawLine(x1, top, x1, bottom, guideLinePaint)
        val x2 = right - oneThirdCropWidht
        canvas?.drawLine(x2, top, x2, bottom, guideLinePaint)

        // Draw horizontal guides
        val oneThirdCropHeight = Edge.height() / 3
        val y1 = top + oneThirdCropHeight
        canvas?.drawLine(left, y1, right, y1, guideLinePaint)
        val y2 = bottom - oneThirdCropHeight
        canvas?.drawLine(left, y2, right, y2, guideLinePaint)
    }

    private fun drawBorder(canvas: Canvas?) {
        canvas?.drawRect(
            Edge.LEFT.coordinate,
            Edge.TOP.coordinate,
            Edge.RIGHT.coordinate,
            Edge.BOTTOM.coordinate,
            borderPaint
        )
    }

    private fun drawCorners(canvas: Canvas?) {
        val left = Edge.LEFT.coordinate
        val top = Edge.TOP.coordinate
        val right = Edge.RIGHT.coordinate
        val bottom = Edge.BOTTOM.coordinate

        // Absolute value of the offset by which to draw the corner line such that its inner edge is flush with the border's inner edge.
        val lateralOffset = (cornerThickness - borderThickness) / 2f
        // Absolute value of the offset by which to start the corner line such that the line is drawn all the way to form a corner edge with the adjacent side.
        val startOffset = cornerThickness - (borderThickness / 2f)

        // Top-left corner: left side
        canvas?.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + cornerLength, cornerPaint)
        // Top-left corner: top side
        canvas?.drawLine(left - startOffset, top - lateralOffset, left + cornerLength, top - lateralOffset, cornerPaint)

        // Top-right corner: right side
        canvas?.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + cornerLength, cornerPaint)
        // Top-right corner: top side
        canvas?.drawLine(right + startOffset, top - lateralOffset, right - cornerLength, top - lateralOffset, cornerPaint)

        // Bottom-left corner: left side
        canvas?.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - cornerLength, cornerPaint)
        // Bottom-left corner: bottom side
        canvas?.drawLine(left - startOffset, bottom + lateralOffset, left + cornerLength, bottom + lateralOffset, cornerPaint)

        // Bottom-right corner: right side
        canvas?.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - cornerLength, cornerPaint)
        // Bottom-right corner: bottom side
        canvas?.drawLine(right + startOffset, bottom + lateralOffset, right - cornerLength, bottom + lateralOffset, cornerPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) return false

        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                onActionDown(event.x, event.y)
                true
            }
            MotionEvent.ACTION_UP or MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
                onActionUp()
                true
            }
            MotionEvent.ACTION_MOVE -> {
                onActionMove(event.x, event.y)
                parent.requestDisallowInterceptTouchEvent(true)
                true
            }
            else -> false
        }
    }

    fun setAspectRatio(aX: Int, aY: Int) {
        if (aX <= 0 || aY <= 0) throw IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.")

        aspectRatioX = aX
        aspectRatioY = aY

        if (fixAspectRation) {
            requestLayout()
        }
    }

    fun getCroppedImage(): Bitmap? {
        if (drawable == null || drawable !is BitmapDrawable) return null

        val matrix = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F)
        imageMatrix.getValues(matrix)

        val scaleX = matrix[Matrix.MSCALE_X]
        val scaleY = matrix[Matrix.MSCALE_Y]
        val transX = matrix[Matrix.MTRANS_X]
        val transY = matrix[Matrix.MTRANS_Y]

        val bitmapLeft = if (transX < 0) abs(transX) else 0F
        val bitmapTop = if (transY < 0) abs(transY) else 0F

        val originalBitmap = (drawable as BitmapDrawable).bitmap
        val cropX = (bitmapLeft + Edge.LEFT.coordinate) / scaleX
        val cropY = (bitmapTop + Edge.TOP.coordinate) / scaleY

        val cropWidth = min(Edge.width() / scaleX, originalBitmap.width - cropX)
        val cropHeight = min(Edge.height() / scaleY, originalBitmap.height - cropY)

        return Bitmap.createBitmap(originalBitmap, cropX.toInt(), cropY.toInt(), cropWidth.toInt(), cropHeight.toInt())
    }

    private fun getBitmapRect(): RectF {
        if (drawable == null) return RectF()

        val matrix = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F)
        imageMatrix.getValues(matrix)

        val scaleX = matrix[Matrix.MSCALE_X]
        val scaleY = matrix[Matrix.MSCALE_Y]
        val transX = matrix[Matrix.MTRANS_X]
        val transY = matrix[Matrix.MTRANS_Y]

        val drawableIntrinsicWidth = drawable.intrinsicWidth
        val drawableIntrinsicHeight = drawable.intrinsicHeight

        val drawableDisplayWidth = round(drawableIntrinsicWidth * scaleX)
        val drawableDisplayHeight = round(drawableIntrinsicHeight * scaleY)

        val left = max(transX, 0F)
        val top = max(transY, 0F)
        val right = min(left + drawableDisplayWidth, width.toFloat())
        val bottom = min(top + drawableDisplayHeight, height.toFloat())

        return RectF(left, top, right, bottom)
    }

    private fun initCropWindow(bitmapRect: RectF) {
        if (fixAspectRation) initCropWindowWithFixedAspectRatio(bitmapRect)
        else {
            val horPadding = 0.1F * bitmapRect.width()
            val verPadding = 0.1F * bitmapRect.height()

            Edge.LEFT.coordinate = bitmapRect.left + horPadding
            Edge.TOP.coordinate = bitmapRect.top + verPadding
            Edge.RIGHT.coordinate = bitmapRect.right - horPadding
            Edge.BOTTOM.coordinate = bitmapRect.bottom - verPadding
        }
    }

    private fun initCropWindowWithFixedAspectRatio(bitmapRect: RectF) {
        if (AspectRatioUtil.calculateAspectRatio(bitmapRect) > getTargetRatio()) {
            val cropWidth = AspectRatioUtil.calculateWidth(bitmapRect.height(), getTargetRatio())
            Edge.LEFT.coordinate = bitmapRect.centerX() - cropWidth / 2F
            Edge.TOP.coordinate = bitmapRect.top
            Edge.RIGHT.coordinate = bitmapRect.centerX() + cropWidth / 2F
            Edge.BOTTOM.coordinate = bitmapRect.bottom
        } else {
            val cropHeight = AspectRatioUtil.calculateHeight(bitmapRect.width(), getTargetRatio())
            Edge.LEFT.coordinate = bitmapRect.left
            Edge.TOP.coordinate = bitmapRect.centerY() - cropHeight / 2F
            Edge.RIGHT.coordinate = bitmapRect.right
            Edge.BOTTOM.coordinate = bitmapRect.centerY() + cropHeight / 2F
        }
    }

    private fun getTargetRatio(): Float = (aspectRatioX / aspectRatioY).toFloat()


    private var pressedHandle: Handle? = null

    private fun onActionDown(x: Float, y: Float) {
        val left = Edge.LEFT.coordinate
        val top = Edge.TOP.coordinate
        val right = Edge.RIGHT.coordinate
        val bottom = Edge.BOTTOM.coordinate

        pressedHandle = HandleUtil.getPressedHandle(x, y, left, top, right, bottom, handleRadius)
        pressedHandle?.let {
            HandleUtil.getOffset(it, x, y, left, top, right, bottom, touchOffset)
            invalidate()
        }
    }

    private fun onActionUp() {
        pressedHandle?.let {
            pressedHandle = null
            invalidate()
        }
    }

    private fun onActionMove(x: Float, y: Float) {
        if (pressedHandle == null) return


        val rx = x + touchOffset.x
        val ry = y + touchOffset.y
        if (fixAspectRation) {
            pressedHandle?.updateCropWindow(rx, ry, getTargetRatio(), bitmapRect, snapRadius)
        } else {
            pressedHandle?.updateCropWindow(rx, ry, bitmapRect, snapRadius)
        }

        invalidate()
    }
}