package com.curtjrees.recipes.androidApp.feature.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtjrees.recipes.androidApp.utils.Resource
import com.curtjrees.recipes.androidApp.utils.debounceLoading
import com.curtjrees.recipes.sharedFrontend.Recipe
import com.curtjrees.recipes.sharedFrontend.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecipeDetailsViewModel: ViewModel() {

    private val repo = RecipesRepository() //TODO: Do proper DI

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState
    private val currentViewState: ViewState get() = requireNotNull(_viewState.value)

    fun observeRecipe(recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRecipe(recipeId)
                .map { Resource.success(it) }
                .debounceLoading()
                .onStart {
                    emit(Resource.loading(currentViewState.recipe?.data))
                }
                .collect {
                    _viewState.value = currentViewState.copy(recipe = it)
                }
        }
    }

    fun refreshData(recipeId: Long) {
        viewModelScope.launch {
            _viewState.emit(currentViewState.copy(recipe = Resource.loading(currentViewState.recipe?.data)))
            repo.updateDbFromApi(recipeId)
        }
    }

    data class ViewState(
        val recipe: Resource<Recipe>? = null
    )
}
