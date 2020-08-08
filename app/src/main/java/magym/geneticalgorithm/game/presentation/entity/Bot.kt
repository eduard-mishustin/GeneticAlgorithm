package magym.geneticalgorithm.game.presentation.entity

import magym.geneticalgorithm.common.util.random
import magym.geneticalgorithm.game.presentation.entity.base.GameObject
import magym.geneticalgorithm.game.presentation.entity.common.Coordinate

internal const val A_LITTLE_EMPTY_STEPS = 7
internal const val MAX_EMPTY_STEPS = 30

internal const val NUMBER_OF_SIDES = 8
internal const val COMMANDS_COUNT = 64

internal const val DEFAULT_HP_OF_BOT = 35
internal const val MIN_BOT_HP = 0
const val MAX_BOT_HP = 90

private val mineCustom5KCommands = listOf(
    25, 17, 62, 45, 60, 35, 63, 32,
    56, 5, 5, 5, 5, 51, 29, 51,
    5, 5, 5, 5, 5, 48, 5, 5,
    5, 5, 5, 5, 5, 5, 5, 5,
    5, 5, 5, 5, 5, 5, 5, 30,
    1, 36, 35, 34, 33, 33, 5, 5,
    9, 36, 35, 34, 33, 32, 5, 5,
    5, 5, 5, 5, 5, 5, 5, 5
).toIntArray()

private val derived35KCommands = listOf(
    17, 23, 35, 43, 22, 44, 52, 28,
    32, 9, 44, 15, 24, 46, 48, 7,
    44, 24, 18, 45, 51, 10, 2, 41,
    43, 13, 34, 51, 0, 0, 28, 15,
    43, 49, 50, 35, 21, 30, 40, 37,
    27, 56, 15, 21, 33, 40, 45, 45,
    61, 19, 44, 59, 63, 61, 7, 44,
    29, 38, 29, 23, 39, 41, 52, 34
).toIntArray()

private val randomCommands = IntArray(COMMANDS_COUNT).apply {
    for (i in 0 until COMMANDS_COUNT) set(i, random(COMMANDS_COUNT))
}

class Bot(
    val commands: IntArray = derived35KCommands,
    override var coordinate: Coordinate
) : GameObject() {
    
    override val commandIndexIncrementer: Int = 1
    
    var commandIndex: Int = 0
    
    var gazeDirection: Int = 0
    
    var hp: Int = DEFAULT_HP_OF_BOT
    
    var freeEmptySteps: Int = MAX_EMPTY_STEPS
    val hasEmptySteps: Boolean get() = freeEmptySteps > 0
    val isALittleEmptySteps: Boolean get() = freeEmptySteps <= A_LITTLE_EMPTY_STEPS
    
}