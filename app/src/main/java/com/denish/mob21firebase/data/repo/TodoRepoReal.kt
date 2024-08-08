package com.denish.mob21firebase.data.repo

import com.denish.mob21firebase.data.model.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class TodoRepoReal: TodoRepo {
    private fun getDbRef(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("todos")
    }

    override fun getAllTasks() = callbackFlow<List<Task>> {
        val listener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todos = mutableListOf<Task>()
                for (todoSnapshot in snapshot.children) {
                    todoSnapshot.getValue<Task>()?.let {
                        todos.add(it.copy(id = todoSnapshot.key ?: ""))
                    }
                }
                trySend(todos)
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        }
        getDbRef().addValueEventListener(listener)
        awaitClose()
    }

    override suspend fun addTask(task: Task): String? {
        getDbRef().push().setValue(task.toMap()).await()
        return "success"
    }

    override suspend fun deleteTask(id: String) {
        getDbRef().child(id).removeValue().await()
    }

    override suspend fun updateTask(task: Task) {
        getDbRef().child(task.id!!).setValue(task.toMap())
    }

    override suspend fun getTodosById(id: String): Task? {
        val res = getDbRef().child(id).get().await()
        if(res.key == null) {
            return null
        }
        val task = res.getValue<Task>()
        return task?.copy(id = res.key)
    }

}