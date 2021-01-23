package com.curtjrees.recipes.desktop

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.selection.DisableSelection
import androidx.compose.ui.unit.IntSize
import com.curtjrees.recipes.desktop.features.home.HomeContentScreen
import com.curtjrees.recipes.desktop.features.recipe.NewRecipeContentScreen
import com.curtjrees.recipes.desktop.features.recipe.RecipesListContentScreen


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
        SidebarItem("Home", Icons.Default.Fastfood, ContentScreen.Home),
        SidebarItem("Recipes", Icons.Default.List, ContentScreen.RecipesList),
        SidebarItem("Add New Recipe", Icons.Default.PlusOne, ContentScreen.NewRecipe),
    )
    var currentScreen: ContentScreen by remember { mutableStateOf(ContentScreen.Home) }
    val selectedItem = sidebarItems.first { it.screen == currentScreen }


    Row(Modifier.fillMaxSize()) {
        Sidebar(items = sidebarItems, selectedIndex = sidebarItems.indexOf(selectedItem), onItemClick = { currentScreen = it.screen })
        ContentNavigationHost(currentScreen)
    }
}

sealed class ContentScreen {
    object Home : ContentScreen()
    object RecipesList : ContentScreen()
    object NewRecipe : ContentScreen()
}

@Composable
fun ContentNavigationHost(currentScreen: ContentScreen) {
    when (currentScreen) {
        ContentScreen.Home -> HomeContentScreen()
        ContentScreen.RecipesList -> RecipesListContentScreen()
        ContentScreen.NewRecipe -> NewRecipeContentScreen()
    }
}