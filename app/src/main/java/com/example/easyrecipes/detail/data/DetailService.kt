package com.example.easyrecipes.detail.data

import com.example.easyrecipes.common.model.NutritionResponse
import com.example.easyrecipes.common.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("recipes/{recipe_id}/information?includeNutrition=false")
    suspend fun getRecipeDetailById(@Path("recipe_id") recipeId: String): Response<RecipeDto>

    @GET("recipes/{recipe_id}/nutritionWidget.json")
    suspend fun getCaloriesRecipeById(@Path("recipe_id") recipeId: String): Response<NutritionResponse>

}