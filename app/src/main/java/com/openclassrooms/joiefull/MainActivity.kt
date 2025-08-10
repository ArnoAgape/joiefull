package com.openclassrooms.joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.openclassrooms.joiefull.data.ArticleData.articlesList
import com.openclassrooms.joiefull.ui.screens.ArticleDetailsScreen
import com.openclassrooms.joiefull.ui.screens.HomeScreen
import com.openclassrooms.joiefull.ui.screens.Screen
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Joiefull)
        super.onCreate(savedInstanceState)
        setContent {
            JoiefullTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(articlesList)
                }
            }
        }
    }
}

@Composable
fun JoiefullNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onArticleClick = { article ->
                    navHostController.navigate(
                        Screen.ArticleDetails.createRoute(
                            articleId = article.id.toString()
                        )
                    )
                }
            )
        }

        composable(
            route = Screen.ArticleDetails.route, // par ex. "articleDetails/{articleId}"
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
            ArticleDetailsScreen(articleId = articleId)
        }
    }
}
