package com.openclassrooms.joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.ui.Screen
import com.openclassrooms.joiefull.ui.details.ArticleDetailsScreen
import com.openclassrooms.joiefull.ui.home.HomeScreen
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Joiefull)
        super.onCreate(savedInstanceState)
        setContent {
            JoiefullTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                }
                val windowSizeClass = calculateWindowSizeClass(this)
                JoiefullAppContent(windowSizeClass)
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
            route = Screen.ArticleDetails.route,
            arguments = Screen.ArticleDetails.navArguments
        ) {
            ArticleDetailsScreen()
        }

    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun JoiefullAppContent(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
) {

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            // Phone
            // regarder si id sélectionné, afficher detail, sinon afficher liste
            JoiefullNavHost(navHostController = navController)
        }

        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> {
            // Tablet
            // afficher toujours les deux côte à côte, si id = null, afficher detail vide
            var selectedArticle by remember { mutableStateOf<Article?>(null) }

            Row(Modifier.fillMaxSize()) {
                Box(Modifier.weight(3f)) {
                    HomeScreen(onArticleClick = { article ->
                        selectedArticle = article
                    })
                }

                Box(
                    Modifier
                        .weight(2.8f)
                        .fillMaxSize()
                ) {
                    if (selectedArticle != null) {
                        ArticleDetailsScreen()
                    } else {
                        Text(
                            text = "Choose an article",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}