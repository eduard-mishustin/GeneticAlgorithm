package magym.geneticalgorithm.game.presentation.entity.common

internal sealed class Command {
    
    @Suppress("LeakingThis")
    val isFinal: Boolean = this is Move || this is Take || this is Stay
    
    
    class Move(val direction: Int) : Command()
    
    class Take(val direction: Int) : Command()
    
    class Look(val direction: Int) : Command()
    
    class Turn(val turnIncrementer: Int) : Command()
    
    object CheckALittleEmptySteps : Command()
    
    object Stay : Command()
    
}