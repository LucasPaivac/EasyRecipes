package com.example.easyrecipes.list.data

import com.example.easyrecipes.common.model.RecipeResponse
import com.example.easyrecipes.common.model.SearchRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListService {

    @GET ("recipes/random?number=20")
    suspend fun getRandomRecipes(): Response<RecipeResponse>

    @GET("/recipes/complexSearch?")
    suspend fun getSearchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true,
        @Query("number") number: Int = 15): Response<SearchRecipeResponse>
}