package ru.sokolov.sd.todo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sokolov.sd.todo.dao.ToDoDao
import ru.sokolov.sd.todo.dao.ToDoDaoImpl

/**
 * @author merak1t
 */
@Configuration
open class InMemoryDaoContextConfiguration {
    @Bean
    open fun todoDao(): ToDoDao {
        return ToDoDaoImpl()
    }
}