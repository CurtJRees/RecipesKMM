package com.curtjrees.recipes.sharedFrontend


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
