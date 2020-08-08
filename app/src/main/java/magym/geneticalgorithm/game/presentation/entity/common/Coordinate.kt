package magym.geneticalgorithm.game.presentation.entity.common

data class Coordinate(val x: Int, val y: Int)

infix fun Int.coordinateWith(y: Int) = Coordinate(x = this, y = y)