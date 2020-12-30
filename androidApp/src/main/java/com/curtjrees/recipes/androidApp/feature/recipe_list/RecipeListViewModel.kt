package com.curtjrees.recipes.androidApp.feature.recipe_list

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

class RecipeListViewModel : ViewModel() {

    private val repo = RecipesRepository() //TODO: Do proper DI

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState
    private val currentViewState: ViewState get() = requireNotNull(_viewState.value)

    init {
        observeRecipes()
    }

    private fun observeRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRecipes()
                .map { Resource.success(it) }
                .debounceLoading()
                .onStart {
                    emit(Resource.loading(currentViewState.recipes?.data))
                }
                .collect {
                    _viewState.value = currentViewState.copy(recipes = it)
                }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _viewState.emit(currentViewState.copy(recipes = Resource.loading(currentViewState.recipes?.data)))
            repo.updateDbFromApi()
        }
    }

    data class ViewState(
        val recipes: Resource<List<Recipe>>? = null
    )
}
