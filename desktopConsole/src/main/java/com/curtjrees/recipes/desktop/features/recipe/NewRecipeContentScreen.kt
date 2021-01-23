package com.curtjrees.recipes.desktop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.curtjrees.recipes.sharedCore.ApiRecipe

@Composable
fun NewRecipeContentScreen() {
    val repo = remember { RecipesRepository() }
    var name by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    fun addNewRecipe() {
        val newRecipe = ApiRecipe(
            id = -1L, //Since the backend will assign an id
            name = name,
            imageUrl = if(imageUrl.isNotEmpty()) imageUrl else null,
            steps = emptyList<String>(),
            ingredients = emptyList<String>()
        )

        //Improve Scope use here
        GlobalScope.launch(Dispatchers.IO) {
            repo.addNewRecipe(newRecipe)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(Modifier.height(24.dp))
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")
            },
            modifier = Modifier
                .wrapContentHeight()
                .width(560.dp)
                .padding(horizontal = 24.dp)
        )

        Spacer(Modifier.height(24.dp))

        TextField(
            value = imageUrl,
            onValueChange = {
                imageUrl = it
            },
            label = {
                Text("Image Url")
            },
            modifier = Modifier
                .wrapContentHeight()
                .width(560.dp)
                .padding(horizontal = 24.dp)
        )

        Spacer(Modifier.height(48.dp))

        Button(
            content = {
                Text("Add New Recipe")
            },
            onClick = {
                addNewRecipe()
            },
            modifier = Modifier.padding(horizontal = 24.dp)
        )

    }
}