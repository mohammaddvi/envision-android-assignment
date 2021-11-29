//package com.envision.assignment
//
//import com.envision.assignment.network.EnvisionAPI
//import com.envision.assignment.repository.EnvisionRepository
//import com.envision.assignment.repository.RemoteEnvisionRepository
//import com.envision.assignment.viewmodel.CaptureViewModel
//import com.envision.core.network.provideRetrofit
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//import retrofit2.create
//
//fun envisionModule() = module {
//
//    viewModel {
//        CaptureViewModel(get())
//    }
//    factory<EnvisionRepository> {
//        RemoteEnvisionRepository(get())
//    }
//    factory<EnvisionAPI> {
//        provideRetrofit(get()).create()
//    }
//}