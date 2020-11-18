package com.curtjrees.recipes.sharedFrontend

import com.curtjrees.recipes.sharedFrontend.db.RecipeDatabase

expect fun createDb() : RecipeDatabase?

//expect fun getLogger(): Logger