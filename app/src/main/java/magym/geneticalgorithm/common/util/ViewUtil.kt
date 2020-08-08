package magym.geneticalgorithm.common.util

import android.view.View
import androidx.core.view.isVisible

fun View.setOnClickListener(callback: (() -> Unit)?) {
    callback?.let {
        setOnClickListener { callback.invoke() }
    } ?: run {
        setOnClickListener(null)
    }
}

fun View.setVisible(value: Boolean) {
    isVisible = value
}