package magym.geneticalgorithm.common.util

inline fun ignoredTryCatch(callback: () -> Unit) {
    try {
        callback.invoke()
    } catch (ignored: Exception) {
    }
}

inline fun ignoredInterruptedException(callback: () -> Unit) {
    try {
        callback.invoke()
    } catch (ignored: InterruptedException) {
    }
}