package com.curtjrees.recipes.androidApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.curtjrees.recipes.androidApp.ui.RecipesAppTheme
import com.curtjrees.recipes.sharedFrontend.Recipe
import com.curtjrees.recipes.sharedFrontend.RecipesRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object RecipesListScreen : Screen("RecipesListScreen")
    object RecipeDetailsScreen : Screen("RecipeDetailsScreen")
}


//Navigation
const val RECIPE_ID_KEY = "recipeid"
val recipeIdNavArgument = navArgument(RECIPE_ID_KEY) { type = NavType.LongType }

@Composable
fun MainContent() {
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

@Composable
fun RecipeListScreen(onRecipeSelected: (Recipe) -> Unit) {
    val repo = remember { RecipesRepository() }
    val recipes = repo.getRecipes().collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(recipes.value) {
            TableItem(recipe = it, onClick = onRecipeSelected)
        }
    }
}

@Composable
fun TableItem(modifier: Modifier = Modifier, recipe: Recipe, onClick: (Recipe) -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke(recipe) })
            .padding(16.dp)
    ) {
        Text(recipe.name)
    }
}

@Composable
fun RecipeDetailsScreen(recipeId: Long) {
    Box(Modifier.fillMaxSize()) {
        Text("RecipeDetails - $recipeId", modifier = Modifier.align(Alignment.Center))
    }
}