package com.example.bookfinderapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

    }

    override fun onResume() {
        super.onResume()
        initFragment()
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
        onEach { collect.invoke(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { action.invoke(it) }
    }

    /* Add Fragment (FrameLayout 아이디, Fragment() 프레그먼트 생성한거) */
    fun addFragment(containerViewId:Int, mFragment: Fragment, backStack: Boolean = false) {
        activity?.let {
            val transaction = it.supportFragmentManager.beginTransaction()
            if(backStack) transaction.addToBackStack(null)
            transaction.replace( containerViewId, mFragment)
            transaction.commitAllowingStateLoss()
        }
    }

    /* Remove Fragment (Fragment() 프레그먼트 생성한거) */
    fun removeFragment(mFragment: Fragment) {
        activity?.let {
            val manager = it.supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.remove(mFragment)
            transaction.commitAllowingStateLoss()
            manager.popBackStack()
        }
    }
}