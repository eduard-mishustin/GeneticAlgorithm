package magym.geneticalgorithm.common.base.surface

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import magym.geneticalgorithm.common.util.ignoredInterruptedException

abstract class BaseSurfaceView(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    
    private lateinit var renderingThread: RenderingThread
    
    init {
        isFocusable = true
        holder.addCallback(this)
    }
    
    final override fun surfaceCreated(holder: SurfaceHolder) {
        renderingThread = RenderingThread(this, getHolder()).apply {
            threadRunning = true
            start()
        }
    }
    
    final override fun surfaceChanged(arg0: SurfaceHolder, arg1: Int, arg2: Int, arg3: Int) {}
    
    final override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        renderingThread.threadRunning = false
        
        while (retry) {
            ignoredInterruptedException {
                renderingThread.join()
                retry = false
            }
        }
    }
    
}