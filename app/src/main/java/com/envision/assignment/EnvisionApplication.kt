package com.envision.assignment

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class EnvisionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EnvisionApplication)
            modules(
                listOf(
                    envisionModule(),
                    envisionDB,
                )
            )
        }
    }
}