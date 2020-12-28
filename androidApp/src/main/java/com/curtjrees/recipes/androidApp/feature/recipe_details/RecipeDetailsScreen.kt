package com.curtjrees.recipes.androidApp.feature.recipe_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.curtjrees.recipes.sharedFrontend.Recipe
import com.curtjrees.recipes.sharedFrontend.RecipesRepository
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun RecipeDetailsScreen(recipeId: Long) {
    val repo = remember { RecipesRepository() }
    val recipe = repo.getRecipe(recipeId).collectAsState(initial = null)

    RecipeDetailsContent(recipe.value)
}

@Composable
private fun RecipeDetailsContent(recipe: Recipe?) {
    Box(Modifier.fillMaxSize()) {
        val imageModifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f).align(Alignment.TopCenter)
        CoilImage(
            data = recipe?.image_url.orEmpty(),
            modifier = imageModifier,
            contentScale = ContentScale.FillWidth,
            fadeIn = true,
            loading = {
                Box(modifier = imageModifier.background(Color.LightGray.copy(alpha = 0.6f))) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        strokeWidth = 4.dp,
                        color = Color.Blue
                    )
                }
            },
            error = {
                Box(modifier = imageModifier.background(Color.LightGray.copy(alpha = 0.6f))) {
                    Icon(Icons.Rounded.Warning, modifier = Modifier.align(Alignment.Center))
                }
            },
        )

        Text("$recipe", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun PreviewRecipeDetailsContent() {
    val testRecipe = Recipe(id = 0L, name = "Test Recipe", image_url = null)
    RecipeDetailsContent(recipe = testRecipe)
}