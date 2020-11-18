package com.curtjrees.recipes.sharedFrontend

import com.curtjrees.recipes.sharedFrontend.db.RecipeDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.curtjrees.recipes.sharedFrontend.db.RecipeDatabase

actual fun createDb(): RecipeDatabase? {
    val driver = NativeSqliteDriver(RecipeDatabase.Schema, "recipe.db")
    return RecipeDatabase(driver)
}
