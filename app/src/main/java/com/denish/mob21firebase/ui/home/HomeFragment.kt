package com.denish.mob21firebase.ui.home

import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denish.mob21firebase.R
import com.denish.mob21firebase.data.model.Task
import com.denish.mob21firebase.databinding.FragmentHomeBinding
import com.denish.mob21firebase.ui.adapter.TaskAdapter
import com.denish.mob21firebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_home
    private lateinit var adapter: TaskAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)

        setupAdapter()
        binding?.fabAdd?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddTodoFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding?.btnProfile?.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            )
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.getAllTasks().collect{
                adapter.setTasks(it)
                binding?.ivEmpty?.isInvisible = adapter.itemCount != 0
            }
        }
    }

    private fun setupAdapter() {
        adapter = TaskAdapter(emptyList())
        binding?.rvTasks?.adapter = adapter
        binding?.rvTasks?.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = object: TaskAdapter.Listener {
            override fun onClickItem(task: Task) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToEditTodoFragment(task.id!!)
                )
            }

            override fun onDeleteItem(task: Task) {
                viewModel.deleteTask(task.id!!)
            }

            override fun onLongClick(task: Task) {
                TODO("Not yet implemented")
            }
        }
    }

}