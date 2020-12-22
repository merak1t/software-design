package ru.sokolov.sd.todo.dao

import ru.sokolov.sd.todo.model.ToDoItem
import ru.sokolov.sd.todo.model.ToDoList

interface ToDoDao {
    fun addTodo(listId: Int, todo: ToDoItem?)
    fun addTodoList(todoList: ToDoList?)
    val todoLists: List<ToDoList?>?

    fun removeTodoList(id: Int)
    fun changeStatus(todoId: Int)
}