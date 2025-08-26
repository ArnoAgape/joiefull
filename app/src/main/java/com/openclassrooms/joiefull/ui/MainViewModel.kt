package com.openclassrooms.joiefull.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.di.NetworkModule
import com.openclassrooms.joiefull.domain.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ArticleRepository,
) : ViewModel() {

    private val selectedArticleId = savedStateHandle.getMutableStateFlow<Int?>(KEY_SELECTED_ARTICLE_ID, null)

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())

    val listState: StateFlow<ListState> =
        combine(_articles, selectedArticleId) { list, selId ->
            ListState(articles = list, selectedArticleId = selId)
        }.stateIn(
            viewModelScope,
            WhileSubscribed(5_000),
            ListState(articles = emptyList(), selectedArticleId = null)
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val detailState: StateFlow<DetailState> =
        selectedArticleId
            .flatMapLatest { id ->
                if (id == null) {
                    flowOf(DetailState(article = null))
                } else {
                    flow {
                        val article = repository.getArticleById(id.toString())
                        emit(DetailState(article = article))
                    }
                }
            }
            .stateIn(
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


    fun toggleFavorite(currentArticleId: Int) {
        val set = _favorites.value.toMutableSet()
        if (set.contains(currentArticleId)) set.remove(currentArticleId) else set.add(currentArticleId)
        _favorites.value = set
        savedStateHandle[KEY_SELECTED_ARTICLE_ID] = currentArticleId
    }

    companion object {
        private const val KEY_SELECTED_ARTICLE_ID = "selected_article_id"

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val savedStateHandle = SavedStateHandle()

                val retrofit = NetworkModule.provideRetrofit()
                val api = retrofit.create(ArticleApiService::class.java)
                val repo = ArticleRepository(api)

                return MainViewModel(savedStateHandle, repo) as T
            }
        }
    }

}
