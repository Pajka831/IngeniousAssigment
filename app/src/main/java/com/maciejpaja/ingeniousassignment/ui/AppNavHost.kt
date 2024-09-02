package com.maciejpaja.ingeniousassignment.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import com.maciejpaja.ingeniousassignment.ui.detail.DetailScreen
import com.maciejpaja.ingeniousassignment.ui.user.UserScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    imageLoader: ImageLoader,
    startDestination: String = NavigationItem.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            UserScreen(
                imageLoader,
                { navController.navigate("${NavigationItem.Details.route}/$it") },
                { navController.popBackStack() })
        }
        composable(
            "${NavigationItem.Details.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {
            it.arguments?.getInt("userId")?.let { userId ->
                DetailScreen(userId, imageLoader) {
                    navController.popBackStack()
                }
            }
        }
    }
}