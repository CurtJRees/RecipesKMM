package com.curtjrees.recipes.sharedFrontend

import android.content.Context
import com.curtjrees.recipes.sharedFrontend.db.RecipeDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver

lateinit var appContext: Context

actual fun createDb(): RecipeDatabase? {
    val driver = AndroidSqliteDriver(RecipeDatabase.Schema, appContext, "peopleinspace.db")
    return RecipeDatabase(driver)
}

//actual fun getLogger(): Logger = LogcatLogger()