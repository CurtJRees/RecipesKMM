package com.curtjrees.recipes.androidApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.curtjrees.recipes.androidApp.feature.recipe_details.RecipeDetailsScreen
import com.curtjrees.recipes.androidApp.feature.recipe_list.RecipeListScreen
import com.curtjrees.recipes.androidApp.ui.RecipesAppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainNavHost()
                }
            }
        }
    }
}


@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.RecipesListScreen.route) {
        composable(Screen.RecipesListScreen.route) {
            RecipeListScreen(onRecipeSelected = { recipe ->
                navController.navigate(Screen.RecipeDetailsScreen.route.plus("/${recipe.id}"))
            })
        }
        composable(Screen.RecipeDetailsScreen.route.plus("/{$RECIPE_ID_KEY}"), arguments = listOf(recipeIdNavArgument)) { backStackEntry ->
            RecipeDetailsScreen(recipeId = requireNotNull(backStackEntry.arguments?.getLong(RECIPE_ID_KEY)))
        }
    }
}