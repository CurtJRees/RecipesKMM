package com.curtjrees.recipes.androidApp.feature.recipe_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.curtjrees.recipes.androidApp.utils.SquareLoadingIndicator
import com.curtjrees.recipes.androidApp.utils.SwipeToRefreshLayout
import com.curtjrees.recipes.androidApp.utils.isLoading
import com.curtjrees.recipes.sharedFrontend.Recipe

@Composable
fun RecipeListScreen(onRecipeSelected: (Recipe) -> Unit) {
    val viewModel = viewModel<RecipeListViewModel>()
    val viewState = viewModel.viewState.collectAsState()

    SwipeToRefreshLayout(
        refreshingState = viewState.value.recipes.isLoading,
        onRefresh = { viewModel.refreshData() },
        refreshIndicator = { SquareLoadingIndicator() },
        content = {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "Recipes",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 24.dp, top = 24.dp, bottom = 16.dp)
                    )
                }
                items(viewState.value.recipes?.data.orEmpty()) {
                    TableItem(recipe = it, onClick = onRecipeSelected)
                }
            }
        }
    )
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