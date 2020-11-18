package com.curtjrees.recipes.androidApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import com.curtjrees.recipes.androidApp.ui.RecipesAppTheme
import com.curtjrees.recipes.sharedCore.ApiRecipe
import com.curtjrees.recipes.sharedFrontend.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        //        setContentView(R.layout.activity_main)
        //
        //
        //        GlobalScope.launch(Dispatchers.IO) {
        //            repo.getRecipes().collect {
        //                val recipes = it
        //                launch(Dispatchers.Main) {
        //                    findViewById<TextView>(R.id.text_view).text = recipes.toString()
        //                }
        //            }
        //        }
    }
}

@Composable
fun MainContent() {
    val repo = remember { RecipesRepository() }
    val recipes = repo.getRecipes().collectAsState(initial = emptyList())

    LazyColumnFor(items = recipes.value, modifier = Modifier.fillMaxSize()) { recipe ->
        Text(
            text = recipe.name
        )
    }
}