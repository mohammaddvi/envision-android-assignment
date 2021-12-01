package com.envision.core.utils

import com.envision.core.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher

fun createTestCoroutineDispatcherProvider(coroutineDispatcher: CoroutineDispatcher): CoroutineDispatcherProvider {
    return object: CoroutineDispatcherProvider {
        override fun bgDispatcher(): CoroutineDispatcher = coroutineDispatcher

        override fun ioDispatcher(): CoroutineDispatcher = coroutineDispatcher

        override fun uiDispatcher(): CoroutineDispatcher = coroutineDispatcher

    }
}
