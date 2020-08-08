package magym.geneticalgorithm.game.presentation.entity

import magym.geneticalgorithm.game.presentation.entity.base.GameObject
import magym.geneticalgorithm.game.presentation.entity.common.Coordinate

class Food(
    override var coordinate: Coordinate
) : GameObject() {
    
    override val commandIndexIncrementer: Int = 4
    
}