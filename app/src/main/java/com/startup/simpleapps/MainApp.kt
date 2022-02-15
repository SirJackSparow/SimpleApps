package com.startup.simpleapps

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.startup.simpleapps.di.moduleApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    private var instance: MainApp? = null
    fun getInstance(): MainApp? {
        return instance
    }

    fun getContext(): Context? {
        return instance
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@MainApp)
            modules(moduleApp)
        }
    }
}
