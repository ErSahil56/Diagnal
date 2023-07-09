package com.example.myapplication

import android.app.Application
import com.example.myapplication.ui.MainActivityViewModelFactory

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import android.content.Context

class AppClass : Application(), KodeinAware {


    override fun onCreate() {
        super.onCreate()

    }




    override val kodein = Kodein.lazy {


        import(androidXModule(this@AppClass))



       /* bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { MyApi(instance(),instance(),instance()) }
        bind() from singleton { UserRepository(instance()) }*/

        bind() from provider { MainActivityViewModelFactory(instance()) }


    }
}