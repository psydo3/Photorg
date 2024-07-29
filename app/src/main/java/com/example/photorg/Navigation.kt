package com.example.photorg

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room

@Composable
fun Navigation(){
    val navController = rememberNavController()

    val context = LocalContext.current
    val db by lazy {
        Room.databaseBuilder(
            context,
            AlbumDatabase::class.java,
            "albums.db"
        ).build()
    }

    val viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T{
            return AlbumViewModel(db.dao) as T
        }
    }
    val viewModel: AlbumViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = viewModelFactory
    )
    val state by viewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Routes.homeScreen){
        composable(Routes.homeScreen){
            HomeScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
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
                colorVal = it.arguments?.getInt("colorVal"),
                onEvent = viewModel::onEvent,
                state = state
            )
        }
    }
}