package com.example.bookfinderapp.ui.finder

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookfinderapp.R
import com.example.bookfinderapp.data.response.BookFinderResponse
import com.example.bookfinderapp.databinding.FragmentBookFinderBinding
import com.example.bookfinderapp.ui.adapter.BookListAdapter
import com.example.bookfinderapp.ui.base.BaseFragment
import com.example.bookfinderapp.ui.detail.BookDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookFinderFragment: BaseFragment<BookFinderViewModel, FragmentBookFinderBinding>()  {
    companion object {
        fun newInstance() = BookFinderFragment()
    }

    override val viewModel by viewModel<BookFinderViewModel>()

    private val bookListAdapter by lazy {
        BookListAdapter() {
            addFragment(R.id.fragmentContainer, BookDetailFragment())
        }
    }
    override fun getViewBinding() = FragmentBookFinderBinding.inflate(layoutInflater)

    override fun initFragment() {
        addOnclick()
        initAdapter()
        observeViewModel()
    }

    private fun addOnclick() {

    }

    private fun initAdapter() = with(binding) {
        bookFinderRecyclerView.apply {
            hasFixedSize()
            adapter = bookListAdapter
        }

        bookFinderRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.bookFinderRecyclerView.layoutManager

                if (!binding.bookListLoading.isVisible) {
                    val lastVisibleItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()

                    // 마지막으로 보여진 아이템 position 이
                    // 전체 아이템 개수보다 5개 모자란 경우, 데이터를 loadMore 한다
                    if (layoutManager.itemCount <= lastVisibleItem + 5) {
                        val pageCount = viewModel.pageCount.value?.plus(1)
                        viewModel.setNextPage(pageCount!!)
                    }
                }
            }
        })
    }

    private fun observeViewModel() = with(viewModel) {
        pageCount.observe {
            viewModel.fetchBookList("google")
        }
    }

    override fun observeData() {
        viewModel.bookResponseData.onUiState(
            error = { handleError() },
            loading = { handleLoading() },
            success = {
                handleSuccess(it)
            },
            finish = { handleComplete() }
        )
    }

    @SuppressLint("ShowToast")
    private fun handleError() {
        Toast.makeText(requireContext() , "네트워크 오류입니다.", Toast.LENGTH_SHORT )
    }

    private fun handleLoading() = with(binding) {
        bookListLoading.isVisible = true
    }

    private fun handleComplete() = with(binding) {
        bookListLoading.isVisible = false
    }

    private fun handleSuccess(data: BookFinderResponse) = with(binding) {
        bookListAdapter.set(data.items)
    }
}