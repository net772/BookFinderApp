package com.example.bookfinderapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.domain.usecase.GetAllBookMarkUseCase
import com.example.bookfinderapp.domain.usecase.GetBookDataUseCase
import com.example.bookfinderapp.domain.usecase.GetBookMarkUseCase
import com.example.bookfinderapp.state.ResultState
import com.example.bookfinderapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val app: Application,
    private val getBookDataUseCase: GetBookDataUseCase,
    private val getAllBookMarkUseCase: GetAllBookMarkUseCase,
    private val getBookMarkUseCase: GetBookMarkUseCase
) : BaseViewModel(app) {

    companion object {
        const val TAB_ALL = 0
        const val TAB_BOOK_MARK = 1
        const val MAX_RESULTS_COUNT = 30
    }

    private var page = 0
    private var resultCount = 0
    private var mSarch = ""

    private val _bookResponseData = MutableStateFlow<ResultState<List<BookMarkEntity>>>(ResultState.UnInitialize)
    val bookResponseData = _bookResponseData.asStateFlow()

    private val _bookMarkResponseData =
        MutableStateFlow<ResultState<List<BookMarkEntity>>>(ResultState.UnInitialize)
    val bookMarkResponseData = _bookMarkResponseData.asStateFlow()

    fun getBookList(search: String? = "kotlin") = viewModelScope.launch {
        mSarch = search!!
        getBookDataUseCase.invoke(mSarch, (page++ * MAX_RESULTS_COUNT), MAX_RESULTS_COUNT)
            .map { data ->
                resultCount = data.totalItems
                data.items.map { book ->
                    var bookMarkEntity = book.volumeInfo.toEntity()
                    val bookMarkEntityData = getBookMarkUseCase.invoke(bookMarkEntity.id).firstOrNull()
                    if (bookMarkEntityData != null) {
                        bookMarkEntity = bookMarkEntity.copy(state = true)
                    }
                    bookMarkEntity
                }
            }
            .onState { _bookResponseData.value = it }
    }

    fun getBookMarkList() = viewModelScope.launch {
        getAllBookMarkUseCase.invoke().onState {
            _bookMarkResponseData.value = it
        }
    }

    fun setPage() = run { page = 0 }
    fun getResultCount() = resultCount
}