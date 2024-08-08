package com.denish.mob21firebase.ui.addEditTodo.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.denish.mob21firebase.R
import com.denish.mob21firebase.ui.addEditTodo.base.BaseManageTodoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTodoFragment: BaseManageTodoFragment() {
    override val viewModel: EditTodoViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_manage_todo


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnSubmit?.text = getString(R.string.update)

        lifecycleScope.launch {
            viewModel.task.collect{
                if (it != null) {
                    binding?.etTitle?.setText(it.title)
                    binding?.etDesc?.setText(it.desc)
                }
            }
        }
    }
}