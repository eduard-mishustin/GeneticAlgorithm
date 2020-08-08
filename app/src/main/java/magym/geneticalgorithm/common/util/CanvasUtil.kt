package magym.geneticalgorithm.common.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable

fun Canvas.drawText(
    text: Int,
    x: Int,
    y: Int,
    paint: Paint
) {
    drawText(
        text.toString(),
        x, y,
        paint
    )
}

fun Canvas.drawText(
    text: String,
    x: Int,
    y: Int,
    paint: Paint
) {
    drawText(
        text,
        x.toFloat(), y.toFloat(),
        paint
    )
}

fun Canvas.drawLine(
    startX: Int,
    startY: Int,
    stopX: Int,
    stopY: Int,
    paint: Paint
) = drawLine(
    startX.toFloat(), startY.toFloat(),
    stopX.toFloat(), stopY.toFloat(),
    paint
)

fun Canvas.drawRect(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
    paint: Paint
) = drawRect(
    left.toFloat(), top.toFloat(),
    right.toFloat(), bottom.toFloat(),
    paint
)

fun Canvas.drawRoundRect(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
    radius: Int,
    paint: Paint
) = drawRoundRect(
    left, top,
    right, bottom,
    radius, radius,
    paint
)

fun Canvas.drawRoundRect(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
    radiusX: Int,
    radiusY: Int,
    paint: Paint
) = drawRoundRect(
    left.toFloat(), top.toFloat(),
    right.toFloat(), bottom.toFloat(),
    radiusX.toFloat(), radiusY.toFloat(),
    paint
)

fun Canvas.drawDrawable(
    drawable: Drawable,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
    alpha: Int,
    degree: Int
) {
    drawable.setBounds(left, top, right, bottom)
    drawable.alpha = alpha
    rotateAndDraw(drawable, degree)
}

private fun Canvas.rotateAndDraw(
    drawable: Drawable,
    degree: Int
) {
    val bounds = drawable.bounds
    
    val left = bounds.left
    val right = bounds.right
    val top = bounds.top
    val bottom = bounds.bottom
    
    val width = right - left
    val height = bottom - top
    val centerX = left + width / 2
    val centerY = top + height / 2
    
    save()
    rotate(degree.toFloat(), centerX.toFloat(), centerY.toFloat())
    drawable.draw(this)
    restore()
}