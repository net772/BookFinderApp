package com.example.bookfinderapp.ui.finder

import android.util.Log
import com.example.bookfinderapp.databinding.FragmentBookFinderBinding
import com.example.bookfinderapp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookFinderFragment: BaseFragment<BookFinderViewModel, FragmentBookFinderBinding>()  {
    override val viewModel by viewModel<BookFinderViewModel>()

    override fun getViewBinding() = FragmentBookFinderBinding.inflate(layoutInflater)

    override fun initFragment() = Unit

    override fun observeData() {
        viewModel.bookResponseData.onUiState(
            error = { Log.d("동현","오류") },
            loading = {  Log.d("동현","로딩")  },
            success = {
                Log.d("동현","성공 : ${it.size}")
            }
        )
    }
}