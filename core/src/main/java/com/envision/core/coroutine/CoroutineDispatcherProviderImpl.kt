package com.envision.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
    override fun bgDispatcher(): CoroutineDispatcher = Dispatchers.Default
    override fun uiDispatcher(): CoroutineDispatcher = Dispatchers.Main
}