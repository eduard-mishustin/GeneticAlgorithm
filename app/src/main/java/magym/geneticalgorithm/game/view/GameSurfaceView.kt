package magym.geneticalgorithm.game.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import magym.geneticalgorithm.R
import magym.geneticalgorithm.common.base.surface.BaseSurfaceView
import magym.geneticalgorithm.common.util.*
import magym.geneticalgorithm.game.presentation.GamePresenter
import magym.geneticalgorithm.game.presentation.entity.*
import magym.geneticalgorithm.game.presentation.entity.base.GameObject
import magym.geneticalgorithm.game.presentation.nX
import magym.geneticalgorithm.game.presentation.nY
import org.koin.core.KoinComponent
import org.koin.core.get
import kotlin.math.min

class GameSurfaceView(
    context: Context, attributeSet: AttributeSet
) : BaseSurfaceView(context, attributeSet), KoinComponent {
    
    private var cell = 0
    private var indent = 0
    
    private val botDrawable: Drawable = resources.getDrawable(R.drawable.bot, null)
    
    private val foodPaint = createPaint("#05DB3E")
    private val poisonPaint = createPaint("#D65252")
    private val wallPaint = createPaint("#E4E4E4")
    private val gridPaint = createPaint("#F2F2F2")

    private val presenter: GamePresenter = get()
    
    
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        cell = min(measuredWidth / nX, measuredHeight / nY)
        indent = (cell * 0.9).toInt()
    }
    
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        
        if (presenter.isShowMap) {
            canvas.drawColor(Color.WHITE)
            canvas.drawMap()
            canvas.drawGrid()
        }
    }

    
    private fun Canvas.drawGrid() {
        for (i in 1 until nX) drawVerticalLine(i)
        for (i in 1 until nY) drawHorizontalLine(i)
    }
    
    private fun Canvas.drawVerticalLine(index: Int) = drawLine(
        startX = index * cell,
        startY = cell,
        stopX = index * cell,
        stopY = cell * nY - cell,
        paint = gridPaint
    )
    
    private fun Canvas.drawHorizontalLine(index: Int) = drawLine(
        startX = cell,
        startY = index * cell,
        stopX = cell * nX - cell,
        stopY = index * cell,
        paint = gridPaint
    )
    
    private fun Canvas.drawMap() {
        presenter.objects.forEach { drawGameObject(it) }
        presenter.bots.forEach { drawBot(it) }
    }
    
    private fun Canvas.drawGameObject(gameObject: GameObject) = when (gameObject) {
        is Wall -> drawSimpleGameObject(gameObject)
        is Food, is Poison -> drawRoundedGameObject(gameObject)
        else -> throw Exception("Class ${this::class} not processed")
    }
    
    private fun Canvas.drawSimpleGameObject(gameObject: GameObject) = drawRect(
        left = gameObject.x * cell,
        top = gameObject.y * cell,
        right = gameObject.x * cell + cell,
        bottom = gameObject.y * cell + cell,
        paint = gameObject.getPaint()
    )
    
    private fun Canvas.drawRoundedGameObject(gameObject: GameObject) = drawRoundRect(
        left = gameObject.x * cell + indent,
        top = gameObject.y * cell + indent,
        right = gameObject.x * cell + cell - indent,
        bottom = gameObject.y * cell + cell - indent,
        radius = CORNER_RADIUS,
        paint = gameObject.getPaint()
    )
    
    private fun GameObject.getPaint() = when (this) {
        is Food -> foodPaint
        is Poison -> poisonPaint
        is Wall -> wallPaint
        else -> throw Exception("Class ${this::class} not processed")
    }
    
    private fun Canvas.drawBot(bot: Bot) = drawDrawable(
        drawable = botDrawable,
        left = bot.x * cell,
        top = bot.y * cell,
        right = bot.x * cell + cell,
        bottom = bot.y * cell + cell,
        alpha = bot.getAlpha(),
        degree = bot.gazeDirection * GAZE_DIRECTION_RATIO
    )
    
    private fun Bot.getAlpha(): Int {
        val preAlpha = hp * HP_RATIO + MIDDLE_BOT_HP
        return if (preAlpha > MAX_ALPHA) MAX_ALPHA else preAlpha
    }
    
    companion object {
        
        private const val CORNER_RADIUS = 6
        private const val MAX_ALPHA = 255
        private const val MIDDLE_BOT_HP = 70
        private const val HP_RATIO = MAX_ALPHA / MAX_BOT_HP
        private const val GAZE_DIRECTION_RATIO = 45
        
    }
    
}