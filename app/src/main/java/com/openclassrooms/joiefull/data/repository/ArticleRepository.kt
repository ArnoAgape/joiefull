package com.openclassrooms.joiefull.data.repository

import android.util.Log
import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.model.Article
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton


@Singleton
class ArticleRepository @Inject constructor(private val apiService: ArticleApiService) {

    fun fetchArticleData(): Flow<List<Article>> = flow {
        try {
            val response = apiService.getArticleData()
            if (response.isSuccessful) {
                emit(response.body() ?: emptyList())
            } else {
                Log.e("ArticleRepository", "Erreur HTTP : ${response.code()}")
                emit(emptyList())
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Erreur API : ${e.message}")
            emit(emptyList())
        }
    }

}
