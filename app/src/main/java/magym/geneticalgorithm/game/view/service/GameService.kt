package magym.geneticalgorithm.game.view.service

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Process
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import magym.geneticalgorithm.game.presentation.GamePresenter
import magym.geneticalgorithm.game.view.service.NotificationManager.Companion.NOTIFICATION_ID
import magym.geneticalgorithm.common.util.registerReceiver
import magym.geneticalgorithm.common.util.repeat
import org.koin.android.ext.android.get

class GameService : Service() {
    
    private val presenter: GamePresenter = get()
    private val notificationManager: NotificationManager = get()
    
    private lateinit var broadcastReceiver: BroadcastReceiver
    
    private val disposable = CompositeDisposable()
    
    
    override fun onCreate() {
        super.onCreate()
        registerStopServiceReceiver()
        startForeground(NOTIFICATION_ID, notificationManager.updateNotification())
        subscribeOnPresenter()
    }
    
    override fun onBind(intent: Intent?): Nothing? = null
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_STICKY
    
    override fun onDestroy() {
        presenter.stop()
        disposable.clear()
        unregisterReceiver(broadcastReceiver)
        stopForeground(true)
        super.onDestroy()
    }
    
    
    private fun subscribeOnPresenter() = presenter.run {
        start()

        disposable += repeat(period = 500) {
            notificationManager.updateNotification("$generation $stepsInPreviousGeneration $stepsInGeneration")
        }
    }
    
    private fun registerStopServiceReceiver() {
        broadcastReceiver = registerReceiver(STOP_SERVICE_BROADCAST_ACTION) { context ->
            context.stopGameService()
            killMyProcess()
        }
    }
    
    private fun killMyProcess() {
        Process.killProcess(Process.myPid())
    }
    
    companion object {
        
        const val STOP_SERVICE_BROADCAST_ACTION = "STOP_SERVICE_BROADCAST_ACTION"
        
        private val Context.serviceIntent
            get() = Intent(this, GameService::class.java)
        
        fun Activity.startGameService() {
            startService(serviceIntent)
        }
        
        fun Context.stopGameService() {
            stopService(serviceIntent)
        }
        
    }
    
}
