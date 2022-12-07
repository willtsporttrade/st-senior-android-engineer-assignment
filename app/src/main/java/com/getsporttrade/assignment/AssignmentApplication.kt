package com.getsporttrade.assignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Custom Application wrapper for setting up a [Timber] debug tree
 */
@HiltAndroidApp
class AssignmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}