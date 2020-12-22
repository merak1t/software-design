package ru.sokolov.sd.todo.model

open class ToDoItem() {
    var id = 0
    var name: String? = null
    private var isComplete: Boolean

    init {
        isComplete = false
    }

    fun isCompleted(): String {
        return if (isComplete) "Completed" else "In progress"
    }

    fun getBgColor(): String {
        return if (isComplete) "green" else "orange"
    }

    fun changeStatus() {
        isComplete = !isComplete
    }
}