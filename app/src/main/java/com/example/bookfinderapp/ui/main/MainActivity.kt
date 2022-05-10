package com.example.bookfinderapp.ui.main

import android.content.Intent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookfinderapp.R
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.databinding.ActivityMainBinding
import com.example.bookfinderapp.ui.adapter.BookListAdapter
import com.example.bookfinderapp.ui.base.BaseActivity
import com.example.bookfinderapp.ui.detail.BookDetailActivity
import com.example.bookfinderapp.ui.detail.BookDetailViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by viewModel<MainViewModel>()

    private val bookListAdapter by lazy {
        BookListAdapter() {
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra(BookDetailViewModel.KEY_BOOK_DETAIL, it)
            startActivity(intent)
        }
    }

    private val scrollListener by lazy {
        OnScrollListener()
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity() {
        addOnclick()
        initAdapter()
        tabSetting()
    }

    override fun onResume() {
        super.onResume()
        onTabSelected(binding.tabLayout.selectedTabPosition)
    }

    private fun addOnclick() = with(binding) {
        btnSearch.setOnClickListener {
            if (searchEditText.text.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, getString(R.string.research_input_check), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            bookListAdapter.clear()
            viewModel.setPage()
            viewModel.getBookList(searchEditText.text.toString())
        }
    }

    private fun initAdapter() = with(binding) {
        bookFinderRecyclerView.apply {
            hasFixedSize()
            adapter = bookListAdapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun tabSetting() = with(binding) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) { onTabSelected(tab?.position ?: 0) }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun onTabSelected(position: Int) {
        bookListAdapter.clear()
        when (position) {
            MainViewModel.TAB_ALL -> {
                viewModel.setPage()
                viewModel.getBookList()
            }

            MainViewModel.TAB_BOOK_MARK -> { viewModel.getBookMarkList() }
        }
    }

    override fun observeData() {
        viewModel.bookResponseData.onUiState(
            error = { handleError() },
            loading = { handleLoading() },
            success = { data ->
                if (data.isNullOrEmpty()) Toast.makeText(this, getString(R.string.no_more_list), Toast.LENGTH_SHORT).show()
                else { handleSuccess(data, viewModel.getResultCount()) }
            }
        )

        viewModel.bookMarkResponseData.onUiState(
            error = { handleError() },
            loading = { handleLoading() },
            success = { handleSuccess(it, it.size) }
        )
    }

    private fun handleError() {
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading() = with(binding) {
        bookListLoading.isVisible = true
        resultCount.isVisible = false
    }

    private fun handleSuccess(data: List<BookMarkEntity>, count: Int) = with(binding) {
        bookListAdapter.addAllSet(data)
        resultCount.text = String.format(getString(R.string.result_count), count)
        resultCount.isVisible = true
        bookListLoading.isVisible = false
    }

    inner class OnScrollListener: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            if (!binding.bookListLoading.isVisible && binding.tabLayout.selectedTabPosition == MainViewModel.TAB_ALL) {
                val layoutManager = binding.bookFinderRecyclerView.layoutManager
                val lastVisibleItem = (layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition()

                if (layoutManager.itemCount <= lastVisibleItem + 5) {
                    viewModel.getBookList()
                }
            }
        }
    }
}