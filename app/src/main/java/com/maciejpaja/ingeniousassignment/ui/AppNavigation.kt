package com.maciejpaja.ingeniousassignment.ui

enum class Screen {
    HOME,
    DETAILS,
}
sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object Details : NavigationItem(Screen.DETAILS.name)
}