package magym.geneticalgorithm.game.presentation.util

import magym.geneticalgorithm.game.presentation.entity.base.GameObject
import magym.geneticalgorithm.game.presentation.entity.common.Coordinate

internal fun List<GameObject>.notContainsCoordinates(coordinate: Coordinate): Boolean {
    return !containsCoordinates(coordinate)
}

internal fun List<GameObject>.containsCoordinates(coordinate: Coordinate): Boolean {
    return find { it.isSameCoordinates(coordinate) } != null
}

internal fun GameObject.isSameCoordinates(coordinate: Coordinate): Boolean {
    return this.coordinate == coordinate
}