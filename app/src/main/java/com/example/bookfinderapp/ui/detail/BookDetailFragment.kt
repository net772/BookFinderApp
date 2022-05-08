package com.example.bookfinderapp.ui.detail

import com.example.bookfinderapp.databinding.FragmentDetailBookBinding
import com.example.bookfinderapp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailFragment : BaseFragment<BookDetailViewModel, FragmentDetailBookBinding>()  {
    override val viewModel by viewModel<BookDetailViewModel>()

    override fun getViewBinding() = FragmentDetailBookBinding.inflate(layoutInflater)

    override fun initFragment() = Unit

    override fun observeData() = Unit
}