package magym.geneticalgorithm.game.presentation

import magym.geneticalgorithm.game.presentation.entity.Bot
import magym.geneticalgorithm.game.presentation.entity.Food
import magym.geneticalgorithm.game.presentation.entity.Poison
import magym.geneticalgorithm.game.presentation.entity.Wall
import magym.geneticalgorithm.game.presentation.entity.base.GameObject

internal interface GameObjectsHolder {
    
    val bots: List<Bot>
        get() = mutableBots
    
    /**
     * [GameObject] objects excluding a [Bot]
     */
    val objects: List<GameObject>
        get() = poisons + foods + walls
    
    val mutableBots: MutableList<Bot>
    val poisons: MutableList<Poison>
    val foods: MutableList<Food>
    val walls: MutableList<Wall>
    
    var generation: Int
    var stepsInGeneration: Int
    var stepsInPreviousGeneration: Int

    fun clear()
    
}

internal class GameObjectsHolderImpl: GameObjectsHolder {
    
    override val mutableBots: MutableList<Bot> = mutableListOf()
    override val poisons: MutableList<Poison> = mutableListOf()
    override val foods: MutableList<Food> = mutableListOf()
    override val walls: MutableList<Wall> = mutableListOf()
    
    override var generation: Int = 0
    override var stepsInGeneration: Int = 0
    override var stepsInPreviousGeneration: Int = 0

    override fun clear() {
        mutableBots.clear()
        poisons.clear()
        foods.clear()
        walls.clear()

        generation = 0
        stepsInGeneration = 0
        stepsInPreviousGeneration = 0
    }

}