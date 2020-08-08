package magym.geneticalgorithm.game.presentation.entity.base

import magym.geneticalgorithm.game.presentation.entity.common.Coordinate

abstract class GameObject {
    
    internal abstract val commandIndexIncrementer: Int
    
    abstract var coordinate: Coordinate
        internal set
    
    val x: Int get() = coordinate.x
    val y: Int get() = coordinate.y
    
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as GameObject
        
        if (coordinate != other.coordinate) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        return coordinate.hashCode()
    }
    
}

private const val EMPTY_COMMAND_INDEX_INCREMENTER = 5

val GameObject?.commandIndexIncrementer: Int
    get() = this?.commandIndexIncrementer ?: EMPTY_COMMAND_INDEX_INCREMENTER