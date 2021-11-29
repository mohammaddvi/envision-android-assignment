package com.envision.core.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveDataStore<T : Any>(
    initialState: T,
) {
    var state = initialState
        set(value) {
            field = value
            internalLiveData.value = value
        }
    private val internalLiveData = MutableLiveData<T>().apply {
        value = initialState
    }
    val currentValue = internalLiveData.value
    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) {
        internalLiveData.observe(owner, observer)
    }
    fun observeForever(observer: Observer<T>) =
        internalLiveData.observeForever(observer)
}