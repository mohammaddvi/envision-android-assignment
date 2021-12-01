package com.envision.core.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ViewStateStore<T : Any>(initialState: T) {
    var state = initialState
        set(value) {
            field = value
            internalLiveData.value = value
        }
    private val internalLiveData = MutableLiveData<T>().apply {
        value = initialState
    }

    val liveData = internalLiveData as LiveData<T>

    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) =
        internalLiveData.observe(owner, { observer(it!!) })

    fun observeForever(observer: Observer<T>) =
        internalLiveData.observeForever(observer)

    fun removeObserver(observer: Observer<T>) = internalLiveData.removeObserver(observer)

}
