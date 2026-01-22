package com.example.easyrecipes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easyrecipes.detail.presentation.DetailViewModel
import com.example.easyrecipes.detail.presentation.ui.RecipeDetailScreen
import com.example.easyrecipes.list.presentation.ListViewModel
import com.example.easyrecipes.list.presentation.ui.RecipeListScreen

@Composable
    fun RecipeApp(
        listViewModel: ListViewModel,
        detailViewModel: DetailViewModel
    ) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "RecipeList"){
        composable(route = "RecipeList") {
            RecipeListScreen(
                navController = navController,
                viewModel = listViewModel)
        }

        composable(
            route = "recipeDetail" + "/{recipeId}",
            arguments =listOf(navArgument("recipeId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val recipeId = requireNotNull(backStackEntry.arguments?.getString("recipeId"))
            RecipeDetailScreen(
                recipeId = recipeId,
                navController = navController,
                viewModel = detailViewModel)
        }
    }
}