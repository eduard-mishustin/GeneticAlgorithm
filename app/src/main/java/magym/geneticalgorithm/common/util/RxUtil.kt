package magym.geneticalgorithm.common.util

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@CheckReturnValue
fun repeat(initialDelay: Long = 0, period: Long = 1000, callback: () -> Unit): Disposable {
    return Observable.interval(initialDelay, period, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { callback.invoke() }
}

@CheckReturnValue
fun launch(scheduler: Scheduler = Schedulers.io(), callback: () -> Unit): Disposable {
    return Completable.fromAction(callback::invoke)
        .subscribeOn(scheduler)
        .subscribe()
}

fun createWorkedSchedulerWithFixedThreadPool(
    nThreads: Int = Runtime.getRuntime().availableProcessors() + 1
): Scheduler {
    return Schedulers.from(Executors.newFixedThreadPool(nThreads))
}