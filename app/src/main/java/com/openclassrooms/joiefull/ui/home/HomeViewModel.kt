package com.openclassrooms.joiefull.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.joiefull.data.repository.ApiErrorException
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.domain.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedItemId = savedStateHandle.getMutableStateFlow<Int?>(KeySelectedItemId, null)

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        getArticleData()
    }

    fun getArticleData() {
        viewModelScope.launch {

            _uiState.update { it.copy(errorMessage = null) }
            try {
                val articles = repository.fetchArticleData()
                _uiState.update { it.copy(article = articles, errorMessage = null) }
            } catch (e: ApiErrorException) {
                _uiState.update { it.copy(errorMessage = e.message) }
            } catch (_: IOException) {
                _uiState.update { it.copy(errorMessage = "Pas de connexion Internet") }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Unknown error: ${e.message}")
            }
        }
    }

    fun onItemClick(article: Article) {
        savedStateHandle[KeySelectedItemId] = article.id
    }

    fun onDetailBackClick() {
        savedStateHandle[KeySelectedItemId] = null
    }

    companion object {
        private const val KeySelectedItemId = "selected_item_id"
    }

}

data class HomeUIState(
    val article: List<Article> = emptyList(),
    val errorMessage: String? = null
)