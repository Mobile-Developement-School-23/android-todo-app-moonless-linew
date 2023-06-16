package ru.linew.todoapp.data.repository

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.ui.feature.list.interactor.TodoItemsRepository


class TodoItemsRepositoryImpl(): TodoItemsRepository {
    //хардкод

    companion object {
        val todos = mutableListOf(
            TodoItemDto(
                id = "1",
                body = "Сделать ДЗ",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = true,
                creationTime = 1686560399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "2",
                body = "Погладить кошку",
                priority = "NO",
                deadlineTime = null,
                isCompleted = false,
                creationTime = 1683540399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "3",
                body = "Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы" +
                        " Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы точно не для" +
                        " того чтобы проверить высоту элемента",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = true,
                creationTime = 1686987399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "4",
                body = "Поиграть в футбол",
                priority = "LOW",
                deadlineTime = null,
                isCompleted = true,
                creationTime = 1686560399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "5",
                body = "Сделать курсач",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = false,
                creationTime = 1686560399527,
                modificationTime = 1234567891027
            ),
            TodoItemDto(
                id = "6",
                body = "Сделать ДЗ",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = true,
                creationTime = 1686560399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "7",
                body = "Погладить кошку",
                priority = "NO",
                deadlineTime = null,
                isCompleted = false,
                creationTime = 1683540399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "8",
                body = "Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы" +
                        " Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы точно не для" +
                        " того чтобы проверить высоту элемента",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = true,
                creationTime = 1686987399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "9",
                body = "Поиграть в футбол",
                priority = "LOW",
                deadlineTime = null,
                isCompleted = true,
                creationTime = 1686560399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "10",
                body = "Сделать курсач",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = false,
                creationTime = 1686560399527,
                modificationTime = 1234567891027
            ),
            TodoItemDto(
                id = "11",
                body = "Сделать ДЗ",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = true,
                creationTime = 1686560399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "12",
                body = "Погладить кошку",
                priority = "NO",
                deadlineTime = null,
                isCompleted = false,
                creationTime = 1683540399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "13",
                body = "Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы" +
                        " Сделать алгосы Сделать алгосы Сделать алгосы Сделать алгосы точно не для" +
                        " того чтобы проверить высоту элемента",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = true,
                creationTime = 1686987399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "14",
                body = "Поиграть в футбол",
                priority = "LOW",
                deadlineTime = null,
                isCompleted = true,
                creationTime = 1686560399527,
                modificationTime = null
            ),
            TodoItemDto(
                id = "15",
                body = "Сделать курсач",
                priority = "HIGH",
                deadlineTime = 1686560699527,
                isCompleted = false,
                creationTime = 1080060399527,
                modificationTime = 1234567891027
            ))
    }
    override var dataUpdatedCallback: () -> Unit = {}
    override fun addOrUpdateTodo(item: TodoItemDto) {
        try {
            todos[todos.indexOfFirst { it.id == item.id }] = item
        }
        catch(e:Exception) {
            todos.add(item)
        }
        dataUpdatedCallback()
    }


    override fun deleteTodoById(id: String) {
        todos.removeIf { it.id == id }
        dataUpdatedCallback()
    }

    override fun getTodoById(id: String): TodoItemDto {
        return todos.single{it.id == id}
    }


    override fun provideListOfTodo(): List<TodoItemDto> {
        return todos

    }


}