package magym.geneticalgorithm.game.presentation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import magym.geneticalgorithm.common.util.launch
import magym.geneticalgorithm.game.presentation.entity.Bot
import magym.geneticalgorithm.game.presentation.entity.base.GameObject

const val nX = 25
const val nY = 41

interface GamePresenter {
    
    val bots: List<Bot>
    
    /**
     * [GameObject] objects excluding a [Bot]
     */
    val objects: List<GameObject>
    
    val isShowMap: Boolean
    
    val generation: Int
    val stepsInGeneration: Int
    val stepsInPreviousGeneration: Int
    
    fun start()
    fun stop()
    
    fun replay()
    
    fun toggleMapVisibility()
    
}

internal class GamePresenterImpl(
    private val objectsHolder: GameObjectsHolder,
    private val filler: GameObjectsFiller,
    private val botStepMaker: BotStepMaker
) : GamePresenter {
    
    private val disposable = CompositeDisposable()
    
    override val bots get() = objectsHolder.bots
    override val objects get() = objectsHolder.objects
    
    override var isShowMap: Boolean = false
    
    override val generation: Int get() = objectsHolder.generation
    override val stepsInGeneration: Int get() = objectsHolder.stepsInGeneration
    override val stepsInPreviousGeneration: Int get() = objectsHolder.stepsInPreviousGeneration
    
    override fun start() {
        disposable += launch {
            filler.initStartObjects()
            
            // FIXME: Убрать while и sleep
            while (true) {
                botStepMaker.makeStepForAllBots()
                objectsHolder.stepsInGeneration++
                if (isShowMap) Thread.sleep(200)
            }
        }
    }
    
    override fun stop() {
        // TODO
    }
    
    override fun replay() {
        // TODO
    }
    
    override fun toggleMapVisibility() {
        isShowMap = !isShowMap
    }
    
}