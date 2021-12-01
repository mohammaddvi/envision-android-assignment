package com.envision.core.extension

import retrofit2.HttpException

data class EnvisionError(val message: String)

fun Throwable.error(): EnvisionError? {
    try {
        if (this is HttpException) {
            when {
                this.code() == 404 -> return EnvisionError(message = "Api not found")
                this.code() == 500 -> return EnvisionError(message = "Server is ruined")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    return null
}