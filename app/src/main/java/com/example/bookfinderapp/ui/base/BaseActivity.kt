package com.example.bookfinderapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

abstract class BaseActivity<VM: BaseViewModel, VB: ViewBinding> : AppCompatActivity() {
    abstract val viewModel: VM

    protected lateinit var binding: VB
    private lateinit var fetchJob: Job

    abstract fun getViewBinding(): VB
    protected open fun initActivity() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        initActivity()
        fetchJob = viewModel.fetchData()

    }

    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }
}
