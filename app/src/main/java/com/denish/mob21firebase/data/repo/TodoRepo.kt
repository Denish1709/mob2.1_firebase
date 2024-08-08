package com.denish.mob21firebase.data.repo

import com.denish.mob21firebase.data.model.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface TodoRepo {

    fun getAllTasks() : Flow<List<Task>>

    suspend fun addTask(task: Task): String?

    suspend fun deleteTask(id: String)

    suspend fun updateTask(task: Task)

    suspend fun getTodosById(id: String): Task?
}