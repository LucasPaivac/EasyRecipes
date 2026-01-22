package com.example.easyrecipes.common.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
val recipes: List<RecipeDto>
)

data class SearchRecipeResponse(
    val results: List<RecipeDto>
)

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    @SerializedName("summary")
    val description: String,
    val extendedIngredients: List<IngredientDto>
)

data class IngredientDto(
    val original: String,
)

data class NutritionResponse(
    val nutrients: List<NutrientDto>
)

data class NutrientDto(
    val name: String,
    val amount: Double,
    val unit: String,
)


