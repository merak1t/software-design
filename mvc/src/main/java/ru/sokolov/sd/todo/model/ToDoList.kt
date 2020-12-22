package ru.sokolov.sd.todo.model

class ToDoList() {
    var id = 0
    var name: String? = null
    private var todoList: MutableList<ToDoItem>

    init {
        todoList = ArrayList()
    }

    fun getToDoList(): MutableList<ToDoItem> {
        return todoList
    }

    fun setToDoList(todoList: MutableList<ToDoItem>) {
        this.todoList = todoList
    }

    fun addTodo(todo: ToDoItem) {
        todoList.add(todo)
    }
}