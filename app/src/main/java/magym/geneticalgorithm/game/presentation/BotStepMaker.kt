@file:Suppress("MoveVariableDeclarationIntoWhen")

package magym.geneticalgorithm.game.presentation

import magym.geneticalgorithm.game.presentation.entity.*
import magym.geneticalgorithm.game.presentation.entity.base.GameObject
import magym.geneticalgorithm.game.presentation.entity.base.commandIndexIncrementer
import magym.geneticalgorithm.game.presentation.entity.common.Command
import magym.geneticalgorithm.game.presentation.entity.common.Command.*
import magym.geneticalgorithm.game.presentation.entity.common.Coordinate
import magym.geneticalgorithm.game.presentation.util.directionToCoordinate
import magym.geneticalgorithm.game.presentation.util.isSameCoordinates

internal interface BotStepMaker {
    
    fun makeStepForAllBots()
    
}

internal class BotStepMakerImpl(
    private val objectsHolder: GameObjectsHolder,
    private val filler: GameObjectsFiller
) : BotStepMaker {
    
    private val bots get() = objectsHolder.bots
    private val objects get() = objectsHolder.objects
    private val mutableBots get() = objectsHolder.mutableBots
    private val poisons get() = objectsHolder.poisons
    private val foods get() = objectsHolder.foods
    
    private var until: Int = 0
    
    
    override fun makeStepForAllBots() {
        until = bots.size
        
        for (index in 0 until until) {
            if (index >= until) return
            
            bots[index].run {
                makeStep()
                checkHp()
            }
            
            filler.addUpObjects()
        }
    }
    
    private fun Bot.makeStep() {
        var command = getNextCommand()
        
        while (true) {
            handleCommand(command)
            
            if (command.isFinal || !hasEmptySteps) {
                hp--
                freeEmptySteps = MAX_EMPTY_STEPS
                return
            } else {
                command = getNextCommand()
            }
        }
    }
    
    
    private fun Bot.handleCommand(command: Command) {
        when (command) {
            is Move -> handleMoveCommand(command.direction)
            is Take -> handleTakeCommand(command.direction)
            is Turn -> turn(command.turnIncrementer)
        }
    }
    
    private fun Bot.handleMoveCommand(moveDirection: Int) {
        val lookAtCoordinate = directionToCoordinate(coordinate, moveDirection)
        val lookAt = findGameObject(lookAtCoordinate)
        
        when (lookAt) {
            is Poison -> die()
            is Food -> moveOnFoodAndEatIt(food = lookAt)
            null -> move(coordinate = lookAtCoordinate)
        }
    }
    
    private fun Bot.handleTakeCommand(takeDirection: Int) {
        val lookAtCoordinate = directionToCoordinate(coordinate, takeDirection)
        val lookAt = findGameObject(lookAtCoordinate)
        
        when (lookAt) {
            is Poison -> convertPoisonToFood(poison = lookAt)
            is Food -> eatFood(food = lookAt)
        }
    }
    
    
    private fun Bot.die() {
        until--
        mutableBots -= this
    }
    
    private fun Bot.moveOnFoodAndEatIt(food: Food) {
        move(food.coordinate)
        hp += 10
        foods -= food
    }
    
    private fun Bot.move(coordinate: Coordinate) {
        this.coordinate = coordinate
    }
    
    private fun convertPoisonToFood(poison: Poison) {
        foods += Food(poison.coordinate)
        poisons -= poison
    }
    
    private fun Bot.eatFood(food: Food) {
        hp += 10
        foods -= food
    }
    
    private fun Bot.turn(turnIncrementer: Int) {
        gazeDirection = (gazeDirection + turnIncrementer) % NUMBER_OF_SIDES
    }
    
    
    private fun Bot.getNextCommand(): Command {
        val direction = getCurrentDirection() ?: return Stay
        val command = commands[commandIndex]
        incrementCommandIndex(command, direction)
        return makeCommand(command, direction)
    }
    
    private fun Bot.getCurrentDirection(): Int? {
        while (true) {
            if (!hasEmptySteps) return null
            val command = commands[commandIndex]
            freeEmptySteps--
            
            if (command in isIncrement) {
                incrementCommandIndex(command)
            } else {
                return (command + gazeDirection) % NUMBER_OF_SIDES
            }
        }
    }
    
    private fun Bot.incrementCommandIndex(command: Int, direction: Int) {
        val incrementer = getCommandIndexIncrementer(command = command, direction = direction)
        incrementCommandIndex(incrementer)
    }
    
    private fun Bot.getCommandIndexIncrementer(command: Int, direction: Int): Int {
        return when (command) {
            in isTurn -> 1
            checkIsALittleEmptySteps -> if (isALittleEmptySteps) 1 else 2
            else -> findGameObject(direction).commandIndexIncrementer
        }
    }
    
    private fun Bot.incrementCommandIndex(value: Int) {
        commandIndex = (commandIndex + value) % COMMANDS_COUNT
    }
    
    private fun makeCommand(command: Int, direction: Int): Command {
        return when (command) {
            in isMove -> Move(direction)
            in isTake -> Take(direction)
            in isLook -> Look(direction)
            in isTurn -> Turn(turnIncrementer = command % NUMBER_OF_SIDES)
            checkIsALittleEmptySteps -> CheckALittleEmptySteps
            else -> Stay
        }
    }
    
    
    private fun Bot.checkHp() {
        checkMinHp()
        checkMaxHp()
    }
    
    private fun Bot.checkMaxHp() {
        if (hp > MAX_BOT_HP) {
            hp = MAX_BOT_HP
        }
    }
    
    private fun Bot.checkMinHp() {
        if (hp <= MIN_BOT_HP) {
            die()
        }
    }
    
    
    private fun Bot.findGameObject(direction: Int): GameObject? {
        val coordinate = directionToCoordinate(coordinate, direction)
        return findGameObject(coordinate)
    }
    
    private fun findGameObject(coordinate: Coordinate): GameObject? {
        return (bots + objects).find { it.isSameCoordinates(coordinate) }
    }
    
    
    private companion object {
        
        private val isMove = 0..7
        private val isTake = 8..15
        private val isLook = 16..23
        private val isTurn = 24..31
        private val isIncrement = 32..62
        private const val checkIsALittleEmptySteps = 63
        
    }
    
}