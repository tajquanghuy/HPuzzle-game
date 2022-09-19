package com.puzzletion.hpuzzlegame.cool8puzzle

import android.app.Application
import com.puzzletion.hpuzzlegame.cool8puzzle.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appComponent)
        }
    }

}