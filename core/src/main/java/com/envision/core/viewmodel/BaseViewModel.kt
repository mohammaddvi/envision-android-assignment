package com.envision.core.viewmodel

import androidx.lifecycle.ViewModel
import com.envision.core.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(coroutineDispatcherProvider.bgDispatcher() + job)
    override val coroutineContext: CoroutineContext = coroutineScope.coroutineContext

    suspend inline fun <T> onBg(crossinline coroutine: suspend () -> T): T =
        withContext(context = coroutineDispatcherProvider.bgDispatcher()) {
            coroutine()
        }

    suspend inline fun <T> onIo(crossinline coroutine: suspend () -> T): T =
        withContext(context = coroutineDispatcherProvider.ioDispatcher()) {
            coroutine()
        }

    suspend inline fun <T> onUi(crossinline coroutine: suspend () -> T): T =
        withContext(context = coroutineDispatcherProvider.uiDispatcher()) {
            coroutine()
        }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}