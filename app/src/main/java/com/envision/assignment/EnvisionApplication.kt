package com.envision.assignment

import android.app.Application
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class EnvisionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    envisionModule(),
                )
            )
        }
    }
}