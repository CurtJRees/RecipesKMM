package com.curtjrees.recipes.androidApp.feature.recipe_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.curtjrees.recipes.androidApp.utils.Resource
import com.curtjrees.recipes.androidApp.utils.SquareLoadingIndicator
import com.curtjrees.recipes.androidApp.utils.SwipeToRefreshLayout
import com.curtjrees.recipes.androidApp.utils.WithDelay
import com.curtjrees.recipes.androidApp.utils.isLastIndex
import com.curtjrees.recipes.androidApp.utils.isLoading
import com.curtjrees.recipes.sharedFrontend.Recipe
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun RecipeDetailsScreen(recipeId: Long) {
    val viewModel = viewModel<RecipeDetailsViewModel>()
    val viewState = viewModel.viewState.collectAsState()

    onActive {
        viewModel.observeRecipe(recipeId)
    }

    SwipeToRefreshLayout(
        refreshingState = viewState.value.recipe.isLoading,
        onRefresh = { viewModel.refreshData(recipeId) },
        refreshIndicator = { SquareLoadingIndicator() },
        content = {
            viewState.value.recipe?.data?.let {
                RecipeDetailsContent(it)
            } ?: run {
                RecipeDetailsContentEmptyState(viewState.value.recipe?.status)
            }
        }
    )
}

@Composable
private fun RecipeDetailsContent(recipe: Recipe) {
    ScrollableColumn(Modifier.fillMaxSize()) {
        val imageModifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f)
        CoilImage(
            data = recipe.image_url.orEmpty(),
            modifier = imageModifier,
            contentScale = ContentScale.FillWidth,
            fadeIn = true,
            loading = {
                WithDelay(500L) {
                    Box(modifier = imageModifier.background(Color.LightGray.copy(alpha = 0.6f))) {
                        CircularProgressIndicator(
                            modifier = Modifier.preferredSize(36.dp).align(Alignment.Center),
                            strokeWidth = 4.dp,
                            color = Color.Blue
                        )
                    }
                }
            },
            error = {
                Box(modifier = imageModifier.background(Color.LightGray.copy(alpha = 0.6f))) {
                    Icon(Icons.Rounded.Warning, modifier = Modifier.align(Alignment.Center))
                }
            },
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = recipe.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 16.dp, end = 24.dp)
        )

        Spacer(Modifier.height(16.dp))


        val ingredients = recipe.ingredients?.split("$$")?.filterNot { it.isEmpty() }
        if (!ingredients.isNullOrEmpty()) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
        }

        ingredients?.forEachIndexed { index, ingredient ->
            Text(
                text = ingredient,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            if (!ingredients.isLastIndex(index)) Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(24.dp))

        val steps = recipe.steps?.split("$$")?.filterNot { it.isEmpty() }
        steps?.forEachIndexed { index, step ->
            val stepNum = index + 1

            Text(
                text = "Step $stepNum",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = step,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            if (!steps.isLastIndex(index)) Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun RecipeDetailsContentEmptyState(status: Resource.Status?) {
    Column(Modifier.fillMaxSize()) {
        if (status == Resource.Status.ERROR) {
            Spacer(modifier = Modifier.height(48.dp))
            Image(imageVector = Icons.Rounded.Warning, modifier = Modifier.preferredSize(48.dp).align(Alignment.CenterHorizontally))
            Text("Something went wrong", modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewRecipeDetailsContent() {
    val testRecipe = Recipe(
        id = 0L,
        name = "Test Recipe",
        image_url = "https://pressfrom.info/upload/images/real/2019/03/25/i-d-invite-myself-over-for-this-spicy-chicken-katsu-sandwich__343749_.jpg?content=1",
        steps = null,
        ingredients = null
    )
    RecipeDetailsContent(testRecipe)
}

@Preview(showBackground = true)
@Composable
private fun PreviewNull_RecipeDetailsContentEmptyState() {
    RecipeDetailsContentEmptyState(null)
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoading_RecipeDetailsContentEmptyState() {
    RecipeDetailsContentEmptyState(Resource.Status.LOADING)
}

@Preview(showBackground = true)
@Composable
private fun PreviewError_RecipeDetailsContentEmptyState() {
    RecipeDetailsContentEmptyState(Resource.Status.ERROR)
}
