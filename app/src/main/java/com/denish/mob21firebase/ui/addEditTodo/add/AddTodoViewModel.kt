package com.denish.mob21firebase.ui.addEditTodo.add

import androidx.lifecycle.viewModelScope
import com.denish.mob21firebase.data.model.Extra
import com.denish.mob21firebase.data.model.Task
import com.denish.mob21firebase.data.repo.TodoRepo
import com.denish.mob21firebase.data.repo.TodoRepoFirestore
import com.denish.mob21firebase.ui.addEditTodo.base.BaseManageTodoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val repo: TodoRepo
): BaseManageTodoViewModel() {

    override fun submitTask(title: String, desc: String) {
        viewModelScope.launch {
            repo.addTask(Task(
                title = title,
                desc = desc,
                extra = Extra(location = "Penang", time = "Today")))
            finish.emit(Unit)
        }
    }
}