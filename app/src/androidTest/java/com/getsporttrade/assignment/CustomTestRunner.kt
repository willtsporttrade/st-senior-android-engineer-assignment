package com.getsporttrade.assignment

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
//        return super.newApplication(cl, className, context)
        val clsName = HiltTestApplication::class.java.name
        return super.newApplication(cl, clsName, context)
    }
}