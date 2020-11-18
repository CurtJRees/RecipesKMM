package com.curtjrees.recipes.androidApp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.curtjrees.recipes.sharedFrontend.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val repo = RecipesRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlobalScope.launch(Dispatchers.IO) {
            repo.getRecipes().collect {
                val recipes = it
                launch(Dispatchers.Main) {
                    findViewById<TextView>(R.id.text_view).text = recipes.toString()
                }
            }
        }
    }
}
