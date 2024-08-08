package com.denish.mob21firebase.data.repo

import com.denish.mob21firebase.core.service.AuthService
import com.denish.mob21firebase.data.model.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TodoRepoFirestore(
    private val authService: AuthService
): TodoRepo {
    fun getCollection(): CollectionReference {
        val uid = authService.getUId() ?: throw Exception("User doesn't exist")
        return Firebase.firestore
            .collection("root_db/$uid/todos")
    }

    suspend fun getAllTasksByTitle(title: String): List<Task> {
        val res = getCollection().orderBy("title")
            .startAt(title)
            .endAt("$title\uf8ff").get().await()
        val tasks = mutableListOf<Task>()
        res.documents.forEach { doc ->
            doc.data?.let {
                val task = Task.fromMap(it)
                tasks.add(task)
            }
        }
        return tasks
    }
    override fun getAllTasks() = callbackFlow<List<Task>> {
        val listener = getCollection().addSnapshotListener{ value, error ->
            if(error != null) {
                throw error
            }

            val tasks = mutableListOf<Task>()

            value?.documents?.map { item ->
                item.data?.let {taskMap ->
                    val task = Task.fromMap(taskMap)
                    tasks.add(task.copy(id = item.id))
                }
            }
            trySend(tasks)
        }
        awaitClose{
            listener.remove()
        }
    }

    override suspend fun addTask(task: Task): String? {
        val res = getCollection().add(task.toMap()).await()
        return res?.id
    }

    override suspend fun deleteTask(id: String) {
        getCollection().document(id).delete().await()
    }

    override suspend fun updateTask(task: Task) {
        getCollection().document(task.id!!).set(task.toMap()).await()
    }

    override suspend fun getTodosById(id: String): Task? {
        val res = getCollection().document(id).get().await()
        return res.data?.let { Task.fromMap(it).copy(id = res.id) }
    }

    suspend fun getGreetings(): List<String> {
        val greetCollection = Firebase.firestore.collection("greetings")
        val res = greetCollection.get().await()
        val msgs = mutableListOf<String>()

        res.documents.map { doc ->
            doc.data?.let {
                val msg = it["msg"].toString()
                msgs.add(msg)
            }
        }
        return msgs
    }
}