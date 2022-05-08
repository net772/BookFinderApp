package com.example.bookfinderapp.ui.finder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.data.response.BookFinderResponse
import com.example.bookfinderapp.domain.usecase.GetBookDataUseCase
import com.example.bookfinderapp.state.ResultState
import com.example.bookfinderapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookFinderViewModel(
    private val app: Application,
    private val getBookDataUseCase: GetBookDataUseCase
) : BaseViewModel(app) {

    private val _pageCount = MutableLiveData(0)
    val pageCount: LiveData<Int> get() = _pageCount

    private val _bookResponseData = MutableStateFlow<ResultState<BookFinderResponse>>(ResultState.UnInitialize)
    val bookResponseData = _bookResponseData.asStateFlow()

    fun fetchBookList(search: String? = "google") = viewModelScope.launch {
        getBookDataUseCase.invoke(search!!, pageCount.value!!).onState {
            _bookResponseData.value = it
        }
    }

    fun setNextPage(page: Int) { _pageCount.value = page }
}