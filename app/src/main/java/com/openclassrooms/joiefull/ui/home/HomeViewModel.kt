package com.openclassrooms.joiefull.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        getArticleData()
    }

    private fun getArticleData() {
        repository.fetchArticleData()
            .onEach { articleUpdate ->
                _uiState.update { currentState ->
                    currentState.copy(
                        article = articleUpdate
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

data class HomeUIState(
    val article: List<Article> = emptyList()
)