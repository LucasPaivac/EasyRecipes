package com.example.easyrecipes.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.easyrecipes.ApiRecipeService
import com.example.easyrecipes.RecipeDto
import com.example.easyrecipes.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    navController: NavController
    ) {

    var recipeDto by remember {
        mutableStateOf<RecipeDto?>(null)
    }

    val apiService = Retrofit.retrofit.create(ApiRecipeService::class.java)

//    apiService.getRecipeDetail(recipeId = recipeId).enqueue(
//
//    )

}