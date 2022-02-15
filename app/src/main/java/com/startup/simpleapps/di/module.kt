package com.startup.simpleapps.di

import com.google.gson.GsonBuilder
import com.startup.simpleapps.data.datastore.DataStorePreferences
import com.startup.simpleapps.data.service.GlobalInterceptor
import com.startup.simpleapps.data.service.Services
import com.startup.simpleapps.repository.*
import com.startup.simpleapps.view.login.LoginViewModel
import com.startup.simpleapps.view.register.RegisterViewModel
import com.startup.simpleapps.view.transaction.TrandsactionViewModel
import com.startup.simpleapps.view.transfer.TransferViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {
    single { DataStorePreferences(androidApplication()) }
    single { createOkHttpClient(get()) }
    single { webService<Services>() }
}

val viewModel = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { TrandsactionViewModel(get(), get()) }
    viewModel { TransferViewModel(get(), get()) }
}

val repository = module {
    single<AuthRepo> { AuthRepoImpl(get()) }
    single<BalanceRepo> { BalanceRepoImpl(get()) }
    single<PayeesRepo> { PayeesRepoImpl(get()) }
    single<TransactionRepo> { TransactionsRepoImpl(get()) }
    single<TransferRepo> { TransferRepoImpl(get()) }
}

fun createOkHttpClient(interceptor: GlobalInterceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val timeout = 10L
    return OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .build()
}

inline fun <reified T> webService(): T {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://green-thumb-64168.uc.r.appspot.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

val moduleApp = listOf(appModule, viewModel, repository)
