package com.curtjrees.recipes.desktop

import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.selection.DisableSelection
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curtjrees.recipes.sharedCore.ApiRecipe

fun main() = Window(
    title = "RecipesKMM - desktopConsole",
    size = IntSize(1280, 720),
    content = {
        MaterialTheme {
            DisableSelection {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
                    MainContent()
                }
            }
        }
    }
)

@Composable
fun MainContent() {
    val sidebarItems = listOf(
        SidebarItem("Recipes", Icons.Default.List),
        SidebarItem("Other", Icons.Default.Fastfood)
    )
    var selectedItem by remember { mutableStateOf(sidebarItems.first()) }

    Row(Modifier.fillMaxSize()) {
        Sidebar(items = sidebarItems, selectedIndex = sidebarItems.indexOf(selectedItem), onItemClick = { selectedItem = it })
        ContentSection()
    }
}

@Composable
fun ContentSection() {
    val repo = remember { RecipesRepository() }
    var recipes: List<ApiRecipe> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect("subject") {
        recipes = repo.fetchRecipesFromApi()
    }

    Box(
        contentAlignment = Alignment.TopStart,
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