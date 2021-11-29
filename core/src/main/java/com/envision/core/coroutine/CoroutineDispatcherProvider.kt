package com.envision.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun ioDispatcher(): CoroutineDispatcher
    fun bgDispatcher(): CoroutineDispatcher
    fun uiDispatcher(): CoroutineDispatcher
}