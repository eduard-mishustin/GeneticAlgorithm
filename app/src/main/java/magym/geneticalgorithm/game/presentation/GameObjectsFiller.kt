package magym.geneticalgorithm.game.presentation

import magym.geneticalgorithm.game.presentation.entity.*
import magym.geneticalgorithm.game.presentation.entity.common.Coordinate
import magym.geneticalgorithm.game.presentation.entity.common.coordinateWith
import magym.geneticalgorithm.game.presentation.util.notContainsCoordinates
import magym.geneticalgorithm.common.util.random

internal interface GameObjectsFiller{
    
    fun initStartObjects()
    
    fun addUpObjects()
    
}

internal class GameObjectsFillerImpl(
    private val objectsHolder: GameObjectsHolder
) : GameObjectsFiller{
    
    private val bots get() = objectsHolder.bots
    private val objects get() = objectsHolder.objects
    private val mutableBots get() = objectsHolder.mutableBots
    private val poisons get() = objectsHolder.poisons
    private val foods get() = objectsHolder.foods
    private val walls get() = objectsHolder.walls
    
    override fun initStartObjects() {
        fillWalls()
        fillBots()
        addUpPoisons()
        addUpFoods()
    }
    
    override fun addUpObjects() {
        addUpPoisons()
        addUpFoods()
        addUpBots()
    }
    
    private fun fillWalls() {
        for (x in 0 until nX) {
            for (y in 0 until nY) {
                if (x == 0 || x == nX - 1 || y == 0 || y == nY - 1) {
                    walls += Wall(coordinate = x coordinateWith y)
                }
            }
        }
    }
    
    private fun fillBots() {
        for (i in 0 until MAX_BOTS_COUNT) {
            mutableBots += Bot(coordinate = findRandFreePosition())
        }
    }
    
    private fun addUpBots() {
        if (bots.size != MIN_BOTS_COUNT) return
        incrementGeneration()
        recoveryBotsHp()
        
        for (i in 0 until MIN_BOTS_COUNT) {
            createDescendants(bots[i])
        }
    }
    
    private fun incrementGeneration() = objectsHolder.run {
        generation++
        stepsInPreviousGeneration = stepsInGeneration
        stepsInGeneration = 0
    }
    
    private fun recoveryBotsHp() {
        bots.filter { it.hp < DEFAULT_HP_OF_BOT }
            .forEach { it.hp = DEFAULT_HP_OF_BOT }
    }
    
    private fun createDescendants(parent: Bot) {
        for (i in 0 until MIN_BOTS_COUNT - 1) {
            createDescendant(parent = parent, mutate = i == 0)
        }
    }
    
    private fun createDescendant(parent: Bot, mutate: Boolean) {
        val newBot = parent.copy()
        
        if (mutate) {
            newBot.mutate()
        }
        
        mutableBots += newBot
    }
    
    private fun Bot.copy() = Bot(
        commands = commands.clone(),
        coordinate = findRandFreePosition()
    )
    
    private fun Bot.mutate() {
        val randomCommandIndex = random(COMMANDS_COUNT)
        val randomCommand = random(COMMANDS_COUNT)
        
        commands[randomCommandIndex] = randomCommand
    }
    
    private fun addUpPoisons() {
        for (i in poisons.size until COUNT_OF_POISON) {
            poisons += Poison(coordinate = findRandFreePosition())
        }
    }
    
    private fun addUpFoods() {
        for (i in foods.size until COUNT_OF_FOOD) {
            foods += Food(coordinate = findRandFreePosition())
        }
    }
    
    private fun findRandFreePosition(): Coordinate {
        while (true) {
            val coordinate = random(nX - 1, nY - 1)
            
            if (bots.notContainsCoordinates(coordinate) &&
                objects.notContainsCoordinates(coordinate) &&
                isPointInArray(coordinate)
            ) {
                return coordinate
            }
        }
    }
    
    private fun isPointInArray(coordinate: Coordinate): Boolean {
        val (x, y) = coordinate
        return x > 0 && y > 0 && x < nX - 1 && y < nY - 1
    }
    
    private companion object {
        
        private const val MIN_BOTS_COUNT = 8
        private const val MAX_BOTS_COUNT = MIN_BOTS_COUNT * MIN_BOTS_COUNT
        
        private const val COUNT_OF_POISON = 60
        private const val COUNT_OF_FOOD = 60
        
    }
    
}