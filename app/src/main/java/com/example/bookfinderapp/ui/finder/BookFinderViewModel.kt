package com.example.bookfinderapp.ui.finder

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.data.response.Book
import com.example.bookfinderapp.domain.usecase.GetBookDataUseCase
import com.example.bookfinderapp.state.ResultState
import com.example.bookfinderapp.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookFinderViewModel(
    private val app: Application,
    private val getBookDataUseCase: GetBookDataUseCase
) : BaseViewModel(app) {

    private val _bookResponseData = MutableStateFlow<ResultState<List<Book>>>(ResultState.UnInitialize)
    val bookResponseData = _bookResponseData.asStateFlow()

    override fun fetchData(): Job = viewModelScope.launch {
        getBookDataUseCase.invoke("google", 0).onState {
            _bookResponseData.value = it
        }
    }

    fun nextBookList(search: String, page: Int) = viewModelScope.launch {
        Log.d("동현","page : $page")
        getBookDataUseCase.invoke(search, page).onState {
            _bookResponseData.value = it
        }
    }
}