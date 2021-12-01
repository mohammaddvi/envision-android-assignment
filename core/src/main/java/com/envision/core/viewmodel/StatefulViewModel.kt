package com.envision.core.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.envision.core.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.runBlocking

abstract class StatefulViewModel<STATE : Any>(
    initialState: STATE,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel(coroutineDispatcherProvider) {

    private fun create() = onCreate()

    private val stateStore = ViewStateStore(initialState)
    private val lazyState: ViewStateStore<STATE> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        stateStore.also {
            create()
        }
    }

    protected open fun onCreate() {}

    fun stateLiveData(): LiveData<STATE> = lazyState.liveData

    fun observe(owner: LifecycleOwner, observer: (STATE) -> Unit) =
        lazyState.observe(owner, observer)

    fun observeForever(observer: Observer<STATE>) = lazyState.observeForever(observer)
    fun removeObserver(observer: Observer<STATE>) = lazyState.removeObserver(observer)
    val currentState: STATE
        get() {
            return stateStore.state
        }

    fun applyState(function: STATE.() -> STATE) = runBlocking {
        return@runBlocking onUi {
            val oldState = stateStore.state
            val newState = function(oldState)
            if (newState == oldState)
                return@onUi
            stateStore.state = newState
        }
    }
}