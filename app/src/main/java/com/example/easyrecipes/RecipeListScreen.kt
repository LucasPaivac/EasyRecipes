package com.example.easyrecipes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipeListScreen(navController: NavController) {

    var recipes by rememberSaveable {
        mutableStateOf<List<RecipeDto>>(emptyList())
    }

    val apiService = Retrofit.retrofit.create(ApiRecipeService::class.java)
    val callRecipes = apiService.getRandomRecipes()

    if (recipes.isEmpty()) {
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
    }

    RecipeListContent(recipeList = recipes) { }

}

@Composable
private fun RecipeListContent(recipeList: List<RecipeDto>, onClick: (RecipeDto) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        RecipeList(recipeList = recipeList, onClick = onClick)
    }

}

@Composable
private fun RecipeList(recipeList: List<RecipeDto>, onClick: (RecipeDto) -> Unit) {
    val featuredRecipe = recipeList.firstOrNull()
    val otherRecipes = recipeList.drop(1)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        featuredRecipe?.let {
            item {
                FeaturedRecipeItem(recipeDto = it, onClick = onClick)
            }
        }

        items(otherRecipes) {
            RecipeItem(
                it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecipeItem(recipeDto: RecipeDto, onClick: (RecipeDto) -> Unit) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ElevatedCard(
                modifier = Modifier
                    .width(180.dp)
                    .aspectRatio(16f / 9f),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    contentScale = ContentScale.Crop,
                    model = recipeDto.image,
                    contentDescription = "Food reference image",
                )
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {

                Text(
                    text = recipeDto.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Row(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .width(14.dp)
                            .height(14.dp)
                            .alpha(0.54f),
                        painter = painterResource(R.drawable.ic_black_time),
                        contentDescription = "Time Preparation Icon"
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        modifier = Modifier
                            .alpha(0.54f),
                        text = "${recipeDto.readyInMinutes} min",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Icon(
                        modifier = Modifier
                            .width(14.dp)
                            .height(14.dp)
                            .alpha(0.54f),
                        painter = painterResource(R.drawable.ic_black_person),
                        contentDescription = "Serving Quantity Icon"
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        modifier = Modifier
                            .alpha(0.54f),
                        text = "${recipeDto.servings} pessoas",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )
                }

            }

        }
    }


}

@Composable
fun FeaturedRecipeItem(recipeDto: RecipeDto, onClick: (RecipeDto) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 7f),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = recipeDto.image,
                contentDescription = "${recipeDto.title} reference image",
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 5f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = recipeDto.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp),
                        painter = painterResource(R.drawable.ic_white_time),
                        contentDescription = "Time Preparation Icon"
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        modifier = Modifier,
                        text = "${recipeDto.readyInMinutes} min",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        modifier = Modifier
                            .alpha(0.7f),
                        text = "|",
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Icon(
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp),
                        painter = painterResource(R.drawable.ic_white_person),
                        contentDescription = "Serving Quantity Icon"
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        modifier = Modifier,
                        text = "${recipeDto.servings} pessoas",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        modifier = Modifier
                            .alpha(0.7f),
                        text = "|",
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }

            }

        }

    }
}
