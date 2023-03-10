package com.envision.assignment

import android.app.Application
import androidx.room.Room
import com.envision.assignment.network.EnvisionAPI
import com.envision.assignment.repository.*
import com.envision.assignment.repository.offline.DocumentDao
import com.envision.assignment.repository.offline.EnvisionDatabase
import com.envision.assignment.repository.offline.InMemoryEnvisionRepository
import com.envision.assignment.repository.online.RemoteEnvisionRepository
import com.envision.assignment.usecase.ConcatParagraphsUseCase
import com.envision.assignment.usecase.SaveDocumentUseCase
import com.envision.assignment.usecase.UploadFileUseCase
import com.envision.assignment.viewmodel.CaptureViewModel
import com.envision.assignment.viewmodel.LibraryViewModel
import com.envision.core.coroutine.CoroutineDispatcherProvider
import com.envision.core.coroutine.CoroutineDispatcherProviderImpl
import com.envision.core.errorhandling.ErrorParser
import com.envision.core.errorhandling.ErrorParserImpl
import com.envision.core.network.AuthInterceptor
import com.envision.core.network.provideOkHttpClient
import com.envision.core.network.provideRetrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.create

val envisionDB = module {
    fun provideDataBase(application: Application): EnvisionDatabase =
        Room.databaseBuilder(application, EnvisionDatabase::class.java, "envision")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(dataBase: EnvisionDatabase): DocumentDao {
        return dataBase.documentDao()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

}


fun envisionModule() = module {
    viewModel {
        CaptureViewModel(get(), get(), get(), get(),get())
    }
    viewModel {
        LibraryViewModel(get(), get())
    }
    factory<ErrorParser> {
        ErrorParserImpl()
    }
    factory<CoroutineDispatcherProvider> {
        CoroutineDispatcherProviderImpl()
    }
    factory {
        UploadFileUseCase(get())
    }
    factory{
        SaveDocumentUseCase(get())
    }
    factory {
        ConcatParagraphsUseCase()
    }
    factory<OnlineEnvsionRepository> {
        RemoteEnvisionRepository(get())
    }
    factory<OfflineEnvisionRepository>{
        InMemoryEnvisionRepository(get())
    }
    single { AuthInterceptor() }

    single { provideOkHttpClient(get()) }

    factory<EnvisionAPI> {
        provideRetrofit(get()).create()
    }
}