package com.curtjrees.recipes.androidApp

import androidx.navigation.NavType
import androidx.navigation.compose.navArgument

const val RECIPE_ID_KEY = "recipeid"
val recipeIdNavArgument = navArgument(RECIPE_ID_KEY) { type = NavType.LongType }

sealed class Screen(val route: String) {
    object RecipesListScreen : Screen("RecipesListScreen")
    object RecipeDetailsScreen : Screen("RecipeDetailsScreen")
}