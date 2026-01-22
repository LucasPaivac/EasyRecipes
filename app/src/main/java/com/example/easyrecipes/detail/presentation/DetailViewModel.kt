package com.example.easyrecipes.detail.presentation

import android.R
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.easyrecipes.common.data.Retrofit
import com.example.easyrecipes.common.model.IngredientDto
import com.example.easyrecipes.common.model.NutrientDto
import com.example.easyrecipes.common.model.RecipeDto
import com.example.easyrecipes.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiRecipe = MutableStateFlow<RecipeDto?>(null)
    val uiRecipe: StateFlow<RecipeDto?> = _uiRecipe

    private val _uiNutrient = MutableStateFlow<NutrientDto?>(null)
    val uiNutrient: StateFlow<NutrientDto?> = _uiNutrient

    fun fetchRecipe(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailService.getRecipeDetailById(id)
            if (response.isSuccessful) {
                _uiRecipe.value = response.body()
            }else{
                Log.d("DetailViewModel", "Request Error: ${response.errorBody().toString()}")
            }
        }
    }

    fun fetchCalories(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailService.getCaloriesRecipeById(id)
            if (response.isSuccessful){
                _uiNutrient.value = response.body()?.nutrients?.firstOrNull {
                    it.name == "Calories"
                }
            }else{
                Log.d("DetailViewModel", "Resquest Error: ${response.errorBody().toString()}")
            }
        }
    }

    /*fun clearRecipeId(){
        viewModelScope.launch {
            delay(1000)
            _uiRecipe.value = null
            _uiNutrient.value = null
        }
    }*/

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val detailService = Retrofit.retrofit.create(DetailService::class.java)
                return DetailViewModel(
                    detailService = detailService
                ) as T
            }
        }
    }


}