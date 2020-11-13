package com.curtjrees.recipes.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.curtjrees.recipes.sharedFrontend.Greeting
import android.widget.TextView
import com.curtjrees.recipes.sharedCore.CoreModel

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    val model = CoreModel("core model on Android")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = model.name
    }
}
