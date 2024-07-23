package com.example.photorg

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.homeScreen){
        composable(Routes.homeScreen){
            HomeScreen(navController = navController)
        }
        composable(Routes.albumScreen){
            AlbumScreen(navController = navController)
        }
    }
}