package com.curtjrees.recipes.androidApp.feature.recipe_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.curtjrees.recipes.sharedFrontend.Recipe
import com.curtjrees.recipes.sharedFrontend.RecipesRepository

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