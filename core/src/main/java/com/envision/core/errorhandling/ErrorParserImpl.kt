package com.envision.core.errorhandling

import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.SocketTimeoutException

class ErrorParserImpl : ErrorParser {
    override fun parse(throwable: Throwable): EnvisionError {
        if (throwable is NullPointerException){
            return EnvisionError("there is no text")
        }
        if (throwable is HttpException) {
            if (throwable.code() == 404) return EnvisionError(message = "Api not found")
            else if (throwable.code() == 500) return EnvisionError(message = "Server is ruined")
        }
        return if (throwable is SocketTimeoutException) EnvisionError("Response time is more than 60 seconds")
        else EnvisionError("Unknown Error")
    }
}

data class EnvisionError(val message:String)