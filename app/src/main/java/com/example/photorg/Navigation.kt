package com.example.photorg

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.homeScreen){
        composable(Routes.homeScreen){
            HomeScreen(navController = navController)
        }
        composable(
            Routes.albumScreen + "/{name}/{colorVal}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "Unnamed Album"
                },
                navArgument("colorVal"){
                    type = NavType.IntType
                }
            )

        ){
            AlbumScreen(
                navController = navController,
                albumName = it.arguments?.getString("name"),
                colorVal = it.arguments?.getInt("colorVal")
            )
        }
    }
}