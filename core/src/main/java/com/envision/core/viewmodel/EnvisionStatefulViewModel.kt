package com.envision.core.viewmodel

import com.envision.core.coroutine.CoroutineDispatcherProvider
import com.envision.core.coroutine.CoroutineDispatcherProviderImpl

open class EnvisionStatefulViewModel<STATE : Any>(
    initialState: STATE,
    dispatcherProvider: CoroutineDispatcherProvider = CoroutineDispatcherProviderImpl()
):StatefulViewModel<STATE>(initialState,dispatcherProvider)