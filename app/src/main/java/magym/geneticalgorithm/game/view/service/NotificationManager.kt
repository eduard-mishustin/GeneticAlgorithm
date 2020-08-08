package magym.geneticalgorithm.game.view.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import magym.geneticalgorithm.game.view.GameActivity
import magym.geneticalgorithm.R
import magym.geneticalgorithm.game.view.service.GameService.Companion.STOP_SERVICE_BROADCAST_ACTION
import magym.geneticalgorithm.common.util.createBroadcastPendingIntent
import magym.geneticalgorithm.CHANNEL_ID

internal class NotificationManager(
    private val context: Context,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManager
) {

    fun updateNotification(text: String = ""): Notification {
        return buildNotification(text).apply {
            notificationManager.notify(NOTIFICATION_ID, this)
        }
    }

    private fun buildNotification(text: String): Notification {
        // FIXME: При апдейте нотификации кнопки дублируются
        notificationBuilder.setContentTitle(text)
            .setSmallIcon(R.drawable.bot)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
            .setGroup(CHANNEL_ID)
            .setGroupSummary(false)
            .setWhen(0)
            .setContentIntent(createNotificationIntent())
            .addAction(R.drawable.bot, "STOP", createStopServiceIntent())
        
        return notificationBuilder.build().apply {
            flags = flags or Notification.FLAG_ONGOING_EVENT
        }
    }
    
    private fun createNotificationIntent(): PendingIntent {
        val notificationIntent = Intent(context, GameActivity::class.java).apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        }

        return PendingIntent.getActivity(context, 0, notificationIntent, 0)
    }
    
    private fun createStopServiceIntent(): PendingIntent {
        return context.createBroadcastPendingIntent(
            actionTag = STOP_SERVICE_BROADCAST_ACTION,
            requestCode = 1
        )
    }

    companion object {

        const val NOTIFICATION_ID = 78428

    }

}