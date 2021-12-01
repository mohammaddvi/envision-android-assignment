package com.envision.core.errorhandling

interface ErrorParser {
    fun parse(throwable: Throwable): String
}