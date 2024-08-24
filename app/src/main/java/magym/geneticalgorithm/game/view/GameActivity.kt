package magym.geneticalgorithm.game.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import magym.geneticalgorithm.R
import magym.geneticalgorithm.common.util.repeat
import magym.geneticalgorithm.common.util.setOnClickListener
import magym.geneticalgorithm.game.presentation.GamePresenter
import magym.geneticalgorithm.game.view.service.GameService.Companion.startGameService
import org.koin.android.ext.android.get

class GameActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    private val presenter: GamePresenter = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startGameService()
        subscribeOnPresenter()

        //replay.setOnClickListener(presenter::replay)
        findViewById<View>(R.id.fast).setOnClickListener(::toggleMapVisibility)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    private fun subscribeOnPresenter() {
        applyFastImageRes()

        disposable += repeat(period = 500) {
            findViewById<TextView>(R.id.generation).text = presenter.generation.toString()
            findViewById<TextView>(R.id.previous_steps).text = presenter.stepsInPreviousGeneration.toString()
            findViewById<TextView>(R.id.current_steps).text = presenter.stepsInGeneration.toString()
        }
    }

    private fun toggleMapVisibility() {
        presenter.toggleMapVisibility()
        applyFastImageRes()
    }

    private fun applyFastImageRes() {
        val res =
            if (presenter.isShowMap) R.drawable.ic_fast_forward
            else R.drawable.ic_fast_rewind

        findViewById<ImageView>(R.id.fast).setImageResource(res)
    }

}