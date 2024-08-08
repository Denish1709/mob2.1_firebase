package com.denish.mob21firebase.ui.addEditTodo.add

import androidx.fragment.app.viewModels
import com.denish.mob21firebase.R
import com.denish.mob21firebase.ui.addEditTodo.base.BaseManageTodoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTodoFragment : BaseManageTodoFragment() {
    override val viewModel: AddTodoViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_manage_todo
}