package com.example.bookfinderapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.bookfinderapp.R
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.databinding.ActivityDetailBookBinding
import com.example.bookfinderapp.ui.base.BaseActivity
import com.example.bookfinderapp.ui.detail.BookDetailViewModel.Companion.KEY_BOOK_DETAIL
import com.example.bookfinderapp.utility.loadCenterCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BookDetailActivity : BaseActivity<BookDetailViewModel, ActivityDetailBookBinding>()  {
    private val bookMarkEntity by lazy { intent.getParcelableExtra<BookMarkEntity>(KEY_BOOK_DETAIL) }
    override val viewModel by viewModel<BookDetailViewModel> {
        parametersOf(bookMarkEntity)
    }
    override fun getViewBinding() = ActivityDetailBookBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.fetchData()
        setClickListeners()
        observeViewModel()
        observeState()
    }

    private fun setClickListeners() = with(binding) {
        btnBookMark.setOnClickListener {
            viewModel.setToggleBookMarked()
        }

        buyLink.setOnClickListener {
            openWebView(bookMarkEntity?.infoLink ?: "")
        }
    }

    private fun observeViewModel() = with(viewModel) {
        bookMarkState.observe {
            if (it) binding.bookMarkButton.setImageResource(R.drawable.ic_book_mark_on)
            else binding.bookMarkButton.setImageResource(R.drawable.ic_book_mark_off)
        }
    }

    private fun observeState() = with(viewModel) {
        bookMarkStateLiveData.onUiState(
            error = { handleError() },
            success = { handleSuccess(it) }
        )
    }

    private fun handleError() {
        Toast.makeText(this ,R.string.network_error, Toast.LENGTH_SHORT ).show()
    }

    private fun handleSuccess(data: BookMarkEntity) = with(binding) {
        if (data.imageLinks.isEmpty()) { bookImage.setImageResource(R.drawable.ic_img_not) }
        else  bookImage.loadCenterCrop(data.imageLinks)
        title.text = data.title
        published.text = String.format(getString(R.string.result_published), data.publishedDate)
        buyLink.text = data.infoLink
        description.text = data.description
    }

    private fun openWebView(url: String) = startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
}