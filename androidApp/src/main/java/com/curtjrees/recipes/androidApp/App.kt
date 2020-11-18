package com.curtjrees.recipes.androidApp

import android.app.Application
import com.curtjrees.recipes.sharedFrontend.appContext

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        //KMM appContext
        appContext = this
    }

}