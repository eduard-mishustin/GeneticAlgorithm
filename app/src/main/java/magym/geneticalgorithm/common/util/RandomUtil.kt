package magym.geneticalgorithm.common.util

import magym.geneticalgorithm.game.presentation.entity.common.Coordinate
import magym.geneticalgorithm.game.presentation.entity.common.coordinateWith

fun random(firstUntil: Int, secondUntil: Int): Coordinate {
    return random(firstUntil) coordinateWith random(secondUntil)
}

fun random(until: Int): Int {
    return (0 until until).random()
}