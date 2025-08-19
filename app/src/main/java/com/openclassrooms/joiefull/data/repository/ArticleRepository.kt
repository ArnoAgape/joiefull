package com.openclassrooms.joiefull.data.repository

import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.model.Article
import jakarta.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticleRepository @Inject constructor(private val apiService: ArticleApiService) {
    suspend fun fetchArticleData(): List<Article> {
        val response = apiService.getArticleData()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw ApiErrorException("HTTP error ${response.code()}: ${response.message()}")
        }
    }

    suspend fun getArticleById(articleId: String): Article? {
        return fetchArticleData().find { it.id.toString() == articleId }
    }
}

class ApiErrorException(message: String) : Exception(message)
