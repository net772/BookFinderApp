package com.example.bookfinderapp.ui.main

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.bookfinderapp.R
import com.example.bookfinderapp.databinding.ActivityMainBinding
import com.example.bookfinderapp.ui.base.BaseActivity
import com.example.bookfinderapp.ui.finder.BookFinderFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(){

    private var bookFinderFragment: BookFinderFragment? = null

    override val viewModel by viewModel<MainViewModel>()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity() {
        addFragment()
    }

    private fun addFragment()  = with(binding) {
        bookFinderFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BookFinderFragment?
                ?: BookFinderFragment.newInstance()

        addFragment(R.id.fragmentContainer, bookFinderFragment)

    }

    private fun addFragment(@IdRes containerId: Int, fragment: Fragment?, addBackStack: Boolean = false) {
        requireNotNull(fragment)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment).apply {
            if (addBackStack) addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }
}