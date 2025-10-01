package com.openclassrooms.joiefull.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.di.NetworkModule
import com.openclassrooms.joiefull.domain.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel managing article list and detail states.
 *
 * Handles fetching, favorites, ratings, and navigation between list and detail.
 *
 * @property savedStateHandle Used to persist the selected article ID.
 * @property repository Provides article data from API.
 */
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ArticleRepository,
) : ViewModel() {

    private val selectedArticleId = savedStateHandle.getMutableStateFlow<Int?>(KEY_SELECTED_ARTICLE_ID, null)
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    private val _favorites = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    private val _ratings = MutableStateFlow<Map<Int, Double>>(emptyMap())

    val listState: StateFlow<ListState> =
        combine(_articles, selectedArticleId, _ratings) { list, selId, ratings ->
            val adjustedList = list.map { article ->
                val userRating = ratings[article.id] ?: 0.0
                article.copy(rate = userRating)
            }
            ListState(articles = adjustedList, selectedArticleId = selId)
        }.stateIn(
            viewModelScope,
            WhileSubscribed(5_000),
            ListState(articles = emptyList(), selectedArticleId = null)
        )

    val detailState: StateFlow<DetailState> =
        combine(selectedArticleId, _favorites, _ratings) { id, favorites, ratings ->
            if (id == null) {
                DetailState(article = null)
            } else {
                val article = repository.getArticleById(id.toString())
                val isFav = favorites[id] ?: false
                val userRating = ratings[id] ?: 0.0

                DetailState(
                    article = article?.copy(
                        likes = article.likes + if (isFav) 1 else 0
                    ),
                    isFavorite = isFav,
                    userRating = userRating
                )
            }
        }.stateIn(
            viewModelScope,
            WhileSubscribed(5_000),
            DetailState(article = null)
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _articles.value = repository.fetchArticleData()
            } catch (_: Exception) {
                _articles.value = emptyList()
            }
        }
    }

    fun onArticleClick(article: Article) {
        savedStateHandle[KEY_SELECTED_ARTICLE_ID] = article.id
    }

    fun onDetailBackClick() {
        savedStateHandle[KEY_SELECTED_ARTICLE_ID] = null
    }

    fun toggleFavorite(articleId: Int) {
        val currentFavorites = _favorites.value.toMutableMap()
        val current = currentFavorites[articleId] ?: false
        currentFavorites[articleId] = !current
        _favorites.value = currentFavorites
    }

    fun setUserRating(articleId: Int, rating: Double) {
        val currentRatings = _ratings.value.toMutableMap()
        currentRatings[articleId] = rating
        _ratings.value = currentRatings
    }

    companion object {
        private const val KEY_SELECTED_ARTICLE_ID = "selected_article_id"

        fun provideFactory(
            repository: ArticleRepository? = null,
            savedStateHandle: SavedStateHandle = SavedStateHandle()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = repository ?: run {
                    val retrofit = NetworkModule.provideRetrofit()
                    val api = retrofit.create(ArticleApiService::class.java)
                    ArticleRepository(api)
                }
                return MainViewModel(savedStateHandle, repo) as T
            }
        }
    }
}