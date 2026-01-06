package com.example.easyrecipes

import retrofit2.Call
import retrofit2.http.GET

interface ApiRecipeService {

    @GET ("recipes/random?number=10")
    fun getRandomRecipes(): Call<RecipeResponse>
}