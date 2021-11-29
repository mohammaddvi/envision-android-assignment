package com.envision.core.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.envision.core.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.runBlocking

abstract class StatefulViewModel<STATE : Any>(
    initialState: STATE,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(coroutineDispatcherProvider) {

    private val stateStore = LiveDataStore(initialState)
    private val lazyState: LiveDataStore<STATE> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        stateStore.also {
            create()
        }
    }

    private fun create() = onCreate()
    protected open fun onCreate() {}

    fun applyState(function: STATE.() -> STATE) = runBlocking {
        onUi {
            stateStore.state = function(stateStore.state)
        }
    }

    fun observe(owner: LifecycleOwner, observer: (STATE) -> Unit) =
        lazyState.observe(owner, observer)

    fun observeForever(observer: Observer<STATE>) = lazyState.observeForever(observer)

}