package com.example.easyrecipes

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
val recipes: List<RecipeDto>
)

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    @SerializedName("summary")
    val description: String
)