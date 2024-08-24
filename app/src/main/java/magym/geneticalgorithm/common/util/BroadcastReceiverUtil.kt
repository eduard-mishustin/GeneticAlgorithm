package magym.geneticalgorithm.common.util

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED

fun Context.createBroadcastPendingIntent(actionTag: String, requestCode: Int, flags: Int = 0): PendingIntent {
    return PendingIntent.getBroadcast(this, requestCode, Intent(actionTag), flags)
}

fun ContextWrapper.registerReceiver(actionTag: String, callback: (context: Context) -> Unit): BroadcastReceiver {
    return registerReceiver(IntentFilter(actionTag), callback)
}

fun ContextWrapper.registerReceiver(filter: IntentFilter, callback: (context: Context) -> Unit): BroadcastReceiver {
    return createBroadcastReceiver(callback).apply {
        registerReceiver(this, filter, RECEIVER_EXPORTED)
    }
}

fun createBroadcastReceiver(callback: (context: Context) -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            callback.invoke(context)
        }
    }
}