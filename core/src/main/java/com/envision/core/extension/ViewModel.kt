package com.envision.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import com.envision.core.viewmodel.EnvisionStatefulViewModel

@Composable
fun <T : Any> EnvisionStatefulViewModel<T>.state(): State<T> {
    return stateLiveData().observeAsState(initial = currentState)
}