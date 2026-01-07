package com.example.easyrecipes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRecipeService {

    @GET ("recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipeResponse>

    @GET ("recipes/{recipe_id}/information?includeNutrition=true")
    fun getRecipeDetail(@Path("recipe_id") recipeId: String): Call<RecipeDto>

}