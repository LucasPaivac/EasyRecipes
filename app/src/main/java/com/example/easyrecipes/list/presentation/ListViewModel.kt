package com.example.easyrecipes.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.easyrecipes.common.data.Retrofit
import com.example.easyrecipes.common.model.RecipeDto
import com.example.easyrecipes.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiRecipes = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiRecipes: StateFlow<List<RecipeDto>> = _uiRecipes

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val searchCache = mutableMapOf<String, List<RecipeDto>>()
    private var mainListCache: List<RecipeDto> = emptyList()

    init {
        loadMainList()
        observeSearch()
    }

    fun onQueryChange(value: String) {
        _query.value = value
    }


    private fun observeSearch() {
        viewModelScope.launch {
            _query
                .debounce(600)
                .distinctUntilChanged()
                .collect { text ->
                    when {
                        text.isBlank() -> _uiRecipes.value = mainListCache
                        text.length < 3 -> Unit
                        else -> searchRecipes(text)
                    }
                }
        }
    }

    private fun loadMainList(force: Boolean = false) {
        if (mainListCache.isNotEmpty() && !force) {
            _uiRecipes.value = mainListCache
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getRandomRecipes()
            if (response.isSuccessful) {
                val recipes = requireNotNull(response.body()?.recipes)
                mainListCache = recipes
                _uiRecipes.value = recipes
            } else {
                Log.d("ListViewModel", "Resquet Error: ${response.errorBody()}")
            }
        }
    }

    private suspend fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            searchCache[query]?.let {
                _uiRecipes.value = it
                return@launch
            }

            val response = listService.getSearchRecipes(query)
            if (response.isSuccessful) {
                val result = requireNotNull(response.body()?.results)
                searchCache[query] = result
                _uiRecipes.value = result
            } else {
                Log.d("ListViewModel", "Search Error: ${response.errorBody()?.string()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = Retrofit.retrofit.create(ListService::class.java)
                return ListViewModel(
                    listService = listService
                ) as T
            }
        }

    }
}