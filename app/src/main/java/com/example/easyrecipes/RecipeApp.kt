package com.example.easyrecipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
    fun RecipeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "RecipeList"){
        composable(route = "RecipeList") {
            RecipeListScreen(navController = navController)
        }
    }
}