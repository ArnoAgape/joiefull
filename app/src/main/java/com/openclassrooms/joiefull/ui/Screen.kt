package com.openclassrooms.joiefull.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen("home")

    data object ArticleDetails : Screen(
        route = "articleDetails/{articleId}",
        navArguments = listOf(navArgument("articleId") {
            type = NavType.Companion.StringType
        })
    ) {
        fun createRoute(articleId: String) = "articleDetails/$articleId"
    }
}