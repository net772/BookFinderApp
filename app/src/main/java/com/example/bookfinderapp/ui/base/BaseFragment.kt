package com.example.bookfinderapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.bookfinderapp.state.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding>: Fragment() {
    abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun getViewBinding(): VB

    private lateinit var fetchJob: Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchJob = viewModel.fetchData()
        initFragment()
        observeData()
    }

    abstract fun initFragment()
    abstract fun observeData()

    protected fun <T> Flow<ResultState<T>>.onUiState(
        loading: () -> Unit = {},
        success: (T) -> Unit = {},
        error: (Throwable) -> Unit = {},
        finish: () -> Unit = {}
    ) {
        onResult { state ->
            when (state) {
                ResultState.Loading -> loading()
                is ResultState.Success -> success(state.data)
                is ResultState.Error -> error(state.error)
                ResultState.Finish -> finish()
                else -> Unit
            }
        }
    }

    protected fun <T> Flow<T>.onResult(collect: (T) -> Unit) {
        onEach { collect.invoke(it) }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }

        _binding = null
        super.onDestroyView()
    }
}