package magym.geneticalgorithm.common.util

import android.graphics.Paint
import android.graphics.Paint.Style
import androidx.annotation.ColorInt

fun createPaint(
    color: String,
    style: Style = Style.FILL
) = createPaint(
    style = style,
    color = color.color()
)

fun createPaint(
    @ColorInt color: Int,
    style: Style = Style.FILL
) = Paint().apply {
    this.style = style
    this.color = color
}