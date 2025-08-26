package com.openclassrooms.joiefull.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.data.FakeData
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Joiefull)
        super.onCreate(savedInstanceState)
        setContent {
            JoiefullTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                }
                val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.listState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    MainScreen(
        state = state,
        detailState = detailState,
        onItemClick = viewModel::onArticleClick,
        onDetailBackClick = viewModel::onDetailBackClick,
        onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
        onRatingSelected = { id, stars -> viewModel.setUserRating(id, stars) }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun MainScreen(
    state: ListState,
    detailState: DetailState,
    onItemClick: (Article) -> Unit,
    onDetailBackClick: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onRatingSelected: (articleId: Int, stars: Double) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    BackHandler(navigator.canNavigateBack()) {
        scope.launch {
            navigator.navigateBack()
        }
    }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                ListPane(
                    state = state,
                    onItemClick = { item ->
                        onItemClick(item)
                        scope.launch {
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                item.id,
                            )
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                DetailPane(
                    state = detailState,
                    showBack = navigator.scaffoldValue.secondary == PaneAdaptedValue.Hidden,
                    onBackClick = {
                        onDetailBackClick()
                        scope.launch {
                            navigator.navigateBack()
                        }
                    },
                    onShareClick = {
                        detailState.article?.let { a ->
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, a.name)
                                putExtra(Intent.EXTRA_TEXT, "${a.name} - ${a.picture.url}")
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    intent,
                                    "Share..."
                                )
                            )
                        }
                    },
                    onFavoriteClick = {
                        detailState.article?.id?.let(onToggleFavorite)
                    },
                    onRatingSelected = onRatingSelected
                )
            }
        },
    )
}

@PreviewScreenSizes
@Composable
fun MainScreenPreview() {
    JoiefullTheme {
        var selectedItemId by remember { mutableStateOf<Int?>(2) }
        var favorites by remember { mutableStateOf<Set<Int>>(emptySet()) }
        var userRating by remember { mutableDoubleStateOf(0.0) }

        MainScreen(
            state = ListState(articles = FakeData.articles, selectedArticleId = selectedItemId),
            detailState = DetailState(
                article = FakeData.articles.first { it.id == selectedItemId },
                userRating = userRating),
            onItemClick = { selectedItemId = it.id },
            onDetailBackClick = {},
            onToggleFavorite = { id ->
                favorites = if (favorites.contains(id)) {
                    favorites - id
                } else {
                    favorites + id
                }
            },
            onRatingSelected = { _, stars -> userRating = stars }
        )
    }
}