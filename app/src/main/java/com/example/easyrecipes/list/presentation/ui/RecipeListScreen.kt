package com.example.easyrecipes.list.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.easyrecipes.R
import com.example.easyrecipes.common.model.RecipeDto
import com.example.easyrecipes.list.presentation.ListViewModel

@Composable
fun RecipeListScreen(
    navController: NavController,
    viewModel: ListViewModel
) {

    val recipes by viewModel.uiRecipes.collectAsState()
    val query by viewModel.query.collectAsState()

    RecipeListContent(
        recipeList = recipes,
        searchQuery = query,
        searchValueChanged = viewModel::onQueryChange,
        onClick = { recipeClicked ->
            navController.navigate(route = "recipeDetail/${recipeClicked.id}")
        })

}

@Composable
private fun RecipeListContent(
    recipeList: List<RecipeDto>,
    searchQuery: String,
    searchValueChanged: (String) -> Unit,
    onClick: (RecipeDto) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hello!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.ic_hello),
                contentDescription = "Hello Icon",
                Modifier.size(24.dp)
            )
        }


        Text(
            modifier = Modifier
                .padding(bottom = 12.dp, top = 4.dp),
            text = "Wanna cook today?",
            fontSize = 16.sp
        )

        ERSearchBar(
            query = searchQuery,
            placeHolder = "Search for recipes",
            onValueChange = searchValueChanged,
            onSearchClicked = {}
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(bottom = 16.dp, top = 16.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
        )

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
            .clickable {
                onClick.invoke(recipeDto)
            }
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

                Row(verticalAlignment = Alignment.CenterVertically) {
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
private fun FeaturedRecipeItem(recipeDto: RecipeDto, onClick: (RecipeDto) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 7f)
            .clickable {
                onClick.invoke(recipeDto)
            },
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
                    Image(
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

                    Image(
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
