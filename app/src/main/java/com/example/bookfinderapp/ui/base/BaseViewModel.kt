package com.example.bookfinderapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.state.ResultState
import com.example.bookfinderapp.utility.config.KoinConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

abstract class BaseViewModel(
    private val app: Application
) : AndroidViewModel(app), KoinComponent {
    protected val ioDispatcher: CoroutineDispatcher by inject(named(KoinConstants.DISPATCHER_IO))

    protected fun <T> Flow<T>.onResult(collect: (T) -> Unit) {
        flowOn(ioDispatcher).onEach { item ->
            collect.invoke(item)
        }.launchIn(viewModelScope)
    }

    protected fun <T> Flow<T>.onState(collect: (ResultState<T>) -> Unit) {
        flowOn(ioDispatcher)
            .onCompletion { collect(ResultState.Finish) }
            .onStart { collect(ResultState.Loading) }
            .catch { collect(ResultState.Error(it)) }
            .onEach { collect(ResultState.Success(it)) }
            .launchIn(viewModelScope)
    }
}