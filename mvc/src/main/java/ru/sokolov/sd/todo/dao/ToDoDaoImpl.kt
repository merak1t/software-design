package ru.sokolov.sd.todo.dao

import ru.sokolov.sd.todo.model.ToDoItem
import ru.sokolov.sd.todo.model.ToDoList
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger


class ToDoDaoImpl : ToDoDao {
    private val todoLastId = AtomicInteger(0)
    private val todoListLastId = AtomicInteger(0)
    override val todoLists: MutableList<ToDoList> = CopyOnWriteArrayList()
    private val todoListById: MutableMap<Int, ToDoList> = ConcurrentHashMap()
    private val todoById: MutableMap<Int, ToDoItem> = ConcurrentHashMap()
    override fun addTodo(listId: Int, todo: ToDoItem?) {
        val id = todoLastId.getAndIncrement()
        todo!!.id = id
        todoListById[listId]!!.addTodo(todo)
        todoById[id] = todo
    }

    override fun addTodoList(todoList: ToDoList?) {
        val id = todoListLastId.getAndIncrement()
        todoList!!.id = id
        todoLists.add(todoList)
        todoListById[id] = todoList
    }

    @JvmName("getTodoLists1")
    fun getTodoLists(): List<ToDoList> {
        return todoLists.toList()
    }

    override fun removeTodoList(id: Int) {
        todoLists.removeIf { todoList: ToDoList -> todoList.id == id }
        todoListById.remove(id)
    }

    override fun changeStatus(todoId: Int) {
        todoById[todoId]!!.changeStatus()
    }
}