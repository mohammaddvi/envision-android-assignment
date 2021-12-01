package com.envision.core.errorhandling

import com.envision.core.extension.error
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ErrorParserImpl : ErrorParser {
    override fun parse(throwable: Throwable): String {
        val error = throwable.error()
        error?.let {
            return if (it.message.isNotEmpty()) it.message
            else "Unknown Error"
        }
        return if (throwable is SocketTimeoutException || throwable is HttpException)
            "Response time is more than 60 seconds"
        else if (throwable is SecurityException) "Security error in device"
        else "Network is not accesible"
    }

}