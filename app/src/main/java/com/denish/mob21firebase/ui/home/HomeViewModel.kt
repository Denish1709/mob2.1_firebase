package com.denish.mob21firebase.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.denish.mob21firebase.data.repo.TodoRepo
import com.denish.mob21firebase.data.repo.TodoRepoFirestore
import com.denish.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: TodoRepo
): BaseViewModel() {

    fun getAllTasks() = repo.getAllTasks()


    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            errorHandler { repo.deleteTask(taskId) }
//            safeApiCall(fun = {repo.deleteTask(taskId)})
        }
    }
}