package com.example.easyrecipes.detail.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.easyrecipes.R
import com.example.easyrecipes.common.model.IngredientDto
import com.example.easyrecipes.common.model.NutrientDto
import com.example.easyrecipes.common.model.RecipeDto
import com.example.easyrecipes.detail.presentation.DetailViewModel
import kotlin.math.round

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    navController: NavController,
    viewModel: DetailViewModel
) {

    val recipeDto by viewModel.uiRecipe.collectAsState()
    val nutrientDto by viewModel.uiNutrient.collectAsState()

    viewModel.fetchRecipe(recipeId)
    viewModel.fetchCalories(recipeId)

    BackHandler {
//        viewModel.clearRecipeId()
        navController.popBackStack()
    }


    recipeDto?.let { recipe ->
        nutrientDto?.let { nutrient ->
            RecipeDetailContent(
                recipeDto = recipe,
                nutrientDto = nutrient,
            )
        }
    }

}

@Composable
private fun RecipeDetailContent(recipeDto: RecipeDto, nutrientDto: NutrientDto) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(300.dp),
                contentScale = ContentScale.Crop,
                model = recipeDto.image,
                contentDescription = "${recipeDto.title} reference image",
            )

            Box(modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                ))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .offset(y = 200.dp)
                .clip(TopConcaveShape())
                .background(color = Color.White))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .offset(y = 200.dp)
                .clip(TopConcaveShape())
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.White
                        )
                    )
                ),)

            Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = recipeDto.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomCenter) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CardInfoDetailRecipe(
                        "${recipeDto.readyInMinutes} min",
                        R.drawable.ic_black_time
                    )
                    CardInfoDetailRecipe(
                        "${recipeDto.servings} porções",
                        R.drawable.ic_black_people
                    )
                    CardInfoDetailRecipe(
                        "${round(nutrientDto.amount).toInt()} ${nutrientDto.unit}", R.drawable.ic_black_fire
                    )
                }
            }
        }

        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)) {

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = "Ingredientes",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            IngredientList(ingredients = recipeDto.extendedIngredients)

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

        }

    }

}

@Composable
fun CardInfoDetailRecipe(info: String, icon: Int) {
    Card(
        modifier = Modifier
            .height(40.dp)
            .width(105.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(icon),
                contentDescription = "Time Preparation Icon",
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = info,
                fontSize = 14.sp
            )
        }
    }

}

@Composable
fun IngredientList(ingredients: List<IngredientDto>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ingredients){
            IngredientItem(
                ingredientDto = it
            )
        }
    }

}

@Composable
fun IngredientItem(ingredientDto: IngredientDto) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(4.dp),
            painter = painterResource(R.drawable.ic_black_circle),
            contentDescription = "black point icon"
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = ingredientDto.original,
            fontSize = 14.sp
        )
    }
}