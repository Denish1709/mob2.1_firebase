package com.denish.mob21firebase.ui.addEditTodo.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.denish.mob21firebase.data.model.Task
import com.denish.mob21firebase.data.repo.TodoRepo
import com.denish.mob21firebase.data.repo.TodoRepoFirestore
import com.denish.mob21firebase.ui.addEditTodo.base.BaseManageTodoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val todoRepo: TodoRepo,
    private val state: SavedStateHandle
): BaseManageTodoViewModel() {
    val task: MutableStateFlow<Task?> = MutableStateFlow(null)
    private val taskId = state.get<String>("taskId")

    init {
        viewModelScope.launch {
            taskId?.let { id ->
                task.value = todoRepo.getTodosById(id)
            }
        }
    }
    override fun submitTask(title: String, desc: String) {
        task.value?.let {
            val newTask = it.copy(title = title, desc = desc)
            viewModelScope.launch {
                todoRepo.updateTask(newTask)
                finish.emit(Unit)
            }
        }
    }
}