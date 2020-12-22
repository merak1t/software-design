package ru.sokolov.sd.todo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import ru.sokolov.sd.todo.dao.ToDoDao
import ru.sokolov.sd.todo.model.ToDoItem
import ru.sokolov.sd.todo.model.ToDoList

@Controller
class ListController(private val toDoDao: ToDoDao) {
    @RequestMapping(value = ["/add-list"], method = [RequestMethod.POST])
    fun addTodoList(@ModelAttribute("new_todo_list") todoList: ToDoList?): String {
        toDoDao.addTodoList(todoList)
        return "redirect:/all-content"
    }

    @RequestMapping(value = ["/remove-list"], method = [RequestMethod.POST])
    fun removeTodoList(@RequestParam("list_id") listId: Int): String {
        toDoDao.removeTodoList(listId)
        return "redirect:/all-content"
    }

    @RequestMapping(value = ["/add-task"], method = [RequestMethod.POST])
    fun addTodo(@ModelAttribute("todo") todo: ToDoItem?,
                @RequestParam("list_id") listId: Int): String {
        toDoDao.addTodo(listId, todo)
        return "redirect:/all-content"
    }

    @RequestMapping(value = ["/change-status-task"], method = [RequestMethod.POST])
    fun changeTodoStatus(@RequestParam("todo_id") todoId: Int): String {
        toDoDao.changeStatus(todoId)
        return "redirect:/all-content"
    }

    @RequestMapping(value = ["/all-content"], method = [RequestMethod.GET])
    fun showTodoLists(model: Model): String {
        prepareModel(model)
        return "index"
    }

    private fun prepareModel(model: Model) {
        model.addAttribute("todo_lists", toDoDao.todoLists)
        model.addAttribute("new_todo_list", ToDoList())
        model.addAttribute("current_list", ToDoList())
        model.addAttribute("task", ToDoItem())
    }
}