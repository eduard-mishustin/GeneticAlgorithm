package magym.geneticalgorithm.game.presentation.util

import magym.geneticalgorithm.game.presentation.entity.common.Coordinate
import magym.geneticalgorithm.game.presentation.entity.common.coordinateWith

/**
 * @param currentCoordinate -  текущее местоположение
 * @param direction - направление взгляда
 * @return [Coordinate] по текущему направлению
 */
internal fun directionToCoordinate(currentCoordinate: Coordinate, direction: Int): Coordinate {
    val (x, y) = currentCoordinate

    return when (direction) {
        0 -> x - 1 coordinateWith y - 1  // ↖
        1 -> x coordinateWith y - 1      // ↑
        2 -> x + 1 coordinateWith y - 1  // ↗
        3 -> x + 1 coordinateWith y      // →
        4 -> x + 1 coordinateWith y + 1  // ↘
        5 -> x coordinateWith y + 1      // ↓
        6 -> x - 1 coordinateWith y + 1  // ↙
        7 -> x - 1 coordinateWith y      // ←
        else -> throw IllegalArgumentException("direction param must be between 0 and 7 inclusively")
    }
}
