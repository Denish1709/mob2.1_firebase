package com.denish.mob21firebase.ui.addEditTodo.base

import androidx.lifecycle.ViewModel
import com.denish.mob21firebase.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseManageTodoViewModel: BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    abstract fun submitTask(title: String, desc: String)
}