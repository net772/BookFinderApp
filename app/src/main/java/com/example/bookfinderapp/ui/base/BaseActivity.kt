package com.example.bookfinderapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

abstract class BaseActivity<VM: BaseViewModel, VB: ViewBinding> : AppCompatActivity() {
    abstract val viewModel: VM

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB
    protected open fun initActivity() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        initActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
