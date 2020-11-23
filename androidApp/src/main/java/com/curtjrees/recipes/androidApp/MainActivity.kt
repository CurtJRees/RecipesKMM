package com.curtjrees.recipes.androidApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
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

@Composable
fun MainContent() {
    val repo = remember { RecipesRepository() }
    val recipes = repo.getRecipes().collectAsState(initial = emptyList())

    LazyColumnForIndexed(items = recipes.value, modifier = Modifier.fillMaxSize().padding(16.dp)) { index, recipe ->
        TableItem(recipe)
        if (index != recipes.value.size - 1) Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun TableItem(recipe: Recipe) {
    Row {
        Text(recipe.name)
    }
}