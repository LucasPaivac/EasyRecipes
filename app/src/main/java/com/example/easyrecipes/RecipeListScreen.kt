package com.example.easyrecipes

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipeListScreen(navController: NavController) {

    var recipes by remember {
        mutableStateOf<List<RecipeDto>>(emptyList())
    }

    val apiService = Retrofit.retrofit.create(ApiRecipeService::class.java)
    val callRecipes = apiService.getRandomRecipes()

    callRecipes.enqueue(object : Callback<RecipeResponse> {
        override fun onResponse(
            call: Call<RecipeResponse?>,
            response: Response<RecipeResponse?>
        ) {
            if (response.isSuccessful) {
                val recipesResponse = response.body()?.recipes
                if (recipesResponse != null) {
                    recipes = recipesResponse
                }
            } else {
                Log.d("RecipeList", "Request error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(
            call: Call<RecipeResponse?>,
            t: Throwable
        ) {
            Log.d("RecipeList", "Network error: ${t.message}")
        }

    })

    RecipeListContent(recipeList = recipes) { }

}

@Composable
private fun RecipeListContent(recipeList: List<RecipeDto>, onClick: (RecipeDto) -> Unit) {
    RecipeList(recipeList = recipeList, onClick = onClick)
}

@Composable
private fun RecipeList(recipeList: List<RecipeDto>, onClick: (RecipeDto) -> Unit) {
    LazyColumn() {
        items(recipeList) {
            RecipeItem(
                it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecipeItem(recipeDto: RecipeDto, onClick: (RecipeDto) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = recipeDto.title
        )

    }
}