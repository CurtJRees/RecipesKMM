package com.curtjrees.recipes.desktop

import androidx.compose.desktop.Window
import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.curtjrees.recipes.sharedCore.ApiRecipe

fun main() = Window(
    title = "desktopConsole",
    size = IntSize(500, 500),
    content = {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
                MainContent()
            }
        }
    }
)

@Composable
fun MainContent() {
    val repo = remember { RecipesRepository() }
    var recipes: List<ApiRecipe> by remember { mutableStateOf(emptyList()) }

    launchInComposition {
        recipes = repo.fetchRecipesFromApi()
    }

    Box(
        gravity = ContentGravity.TopStart,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            backgroundColor = Color.White,
            modifier = Modifier.wrapContentSize().padding(24.dp)
        ) {
            LazyColumnForIndexed(items = recipes, modifier = Modifier.wrapContentSize().padding(16.dp)) { index, recipe ->
                TableItem(recipe)
                if (index != recipes.size - 1) Spacer(Modifier.height(16.dp).background(Color.Green))
            }
        }
    }
}

@Composable
fun TableItem(recipe: ApiRecipe) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(recipe.name)
    }
}