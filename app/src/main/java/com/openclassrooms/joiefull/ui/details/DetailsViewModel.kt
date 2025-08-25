package com.openclassrooms.joiefull.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.data.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ArticleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article.asStateFlow()

    init {
        val articleId: String = savedStateHandle["articleId"] ?: ""
        loadArticle(articleId)
    }

    fun loadArticle(articleId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getArticleById(articleId)
                _article.value = result
            } catch (_: Exception) {
                _article.value = null
            }
        }
    }
}