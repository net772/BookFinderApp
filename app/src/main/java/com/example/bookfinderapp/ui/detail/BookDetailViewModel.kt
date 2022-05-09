package com.example.bookfinderapp.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.domain.usecase.GetBookMarkUseCase
import com.example.bookfinderapp.domain.usecase.ReqDeleteBookMarkUseCase
import com.example.bookfinderapp.domain.usecase.ReqInsertBookMarkUseCase
import com.example.bookfinderapp.state.ResultState
import com.example.bookfinderapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BookDetailViewModel(
    app: Application,
    private val bookMarkEntity: BookMarkEntity,
    private val reqInsertBookMarkUseCase: ReqInsertBookMarkUseCase,
    private val reqDeleteBookMarkUseCase: ReqDeleteBookMarkUseCase,
    private val getBookMarkUseCase: GetBookMarkUseCase
) : BaseViewModel(app) {

    companion object {
        const val KEY_BOOK_DETAIL = "KEY_BOOK_DETAIL"
    }

    private val _bookMarkState = MutableLiveData<Boolean>()
    val bookMarkState: LiveData<Boolean> get() = _bookMarkState

    private val _bookMarkStateLiveData = MutableStateFlow<ResultState<BookMarkEntity>>(ResultState.UnInitialize)
    val bookMarkStateLiveData = _bookMarkStateLiveData.asStateFlow()

    fun fetchData() = viewModelScope.launch {
        _bookMarkStateLiveData.value = ResultState.Success(bookMarkEntity)
        setBookMarkState(bookMarkEntity.state)
    }

    fun setToggleBookMarked() = viewModelScope.launch {
        val bookMarkEntityData = getBookMarkUseCase.invoke(bookMarkEntity.id).firstOrNull()

        bookMarkEntityData?.let {
            reqDeleteBookMarkUseCase.invoke(bookMarkEntity.id)
            setBookMarkState(false)
        } ?: kotlin.run {
            val bookMarkEntity = bookMarkEntity.copy(state = true)
            reqInsertBookMarkUseCase.invoke(bookMarkEntity)
            setBookMarkState(true)
        }
    }

    private fun setBookMarkState(state: Boolean) {
        _bookMarkState.value = state
    }
}