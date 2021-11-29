package com.envision.assignment

import android.app.Application
//import org.koin.core.context.GlobalContext

class EnvisionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        GlobalContext.startKoin {
//            modules(
//                listOf(
//                    envisionModule(),
//                )
//            )
//        }
    }
}