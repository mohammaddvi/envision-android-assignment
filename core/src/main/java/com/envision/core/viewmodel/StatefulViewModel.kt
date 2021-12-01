package com.envision.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.envision.core.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.runBlocking

abstract class StatefulViewModel<STATE : Any>(
    initialState: STATE,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel(coroutineDispatcherProvider) {

    private val _internalLiveData = MutableLiveData<STATE>().apply {
        value = initialState
    }

    fun stateLiveData(): LiveData<STATE> = _internalLiveData
    val currentState: STATE
        get() {
            return _internalLiveData.value!!
        }

    fun applyState(function: STATE.() -> STATE) = runBlocking {
        _internalLiveData.postValue(function(_internalLiveData.value!!))
    }
}