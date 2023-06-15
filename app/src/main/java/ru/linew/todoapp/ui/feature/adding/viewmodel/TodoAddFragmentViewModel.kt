package ru.linew.todoapp.ui.feature.adding.viewmodel

import androidx.lifecycle.ViewModel
import ru.linew.todoapp.ui.feature.list.model.Priority

class TodoAddFragmentViewModel: ViewModel() {
    var body: String = ""
    var priority: Priority = Priority.NO
    var deadlineTime: Long? = null

}