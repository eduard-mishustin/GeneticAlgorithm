package magym.geneticalgorithm.common.base.surface

import android.graphics.Canvas
import android.view.SurfaceHolder
import magym.geneticalgorithm.common.util.ignoredInterruptedException

internal class RenderingThread(
    private val view: BaseSurfaceView,
    private val holder: SurfaceHolder
) : Thread() {
    
    var threadRunning: Boolean = false
    
    override fun run() {
        while (threadRunning) {
            var canvas: Canvas? = null
            
            try {
                canvas = holder.lockCanvas()
                
                synchronized(holder) {
                    canvas?.let(view::draw)
                }
            } catch (e: Exception) {
                // Ignore
            } finally {
                canvas?.let(holder::unlockCanvasAndPost)
            }
            
            sleepTime()
        }
    }
    
    private fun sleepTime(fps: Long = 60) {
        val ticksPS = 1000 / fps
        val startTime = System.currentTimeMillis()
        val sleepTime = ticksPS - (System.currentTimeMillis() - startTime)
        
        ignoredInterruptedException {
            if (sleepTime > 0) sleep(sleepTime)
            else sleep(100)
        }
    }
    
}