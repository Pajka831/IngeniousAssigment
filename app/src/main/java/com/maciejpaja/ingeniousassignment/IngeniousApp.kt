package com.maciejpaja.ingeniousassignment

import android.app.Application
import com.maciejpaja.ingeniousassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class IngeniousApp : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@IngeniousApp)
            modules(appModule)
        }
    }
}