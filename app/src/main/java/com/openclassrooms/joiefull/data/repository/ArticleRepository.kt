package com.openclassrooms.joiefull.data.repository

import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.toDomain
import jakarta.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticleRepository @Inject constructor(private val apiService: ArticleApiService) {
    suspend fun fetchArticleData(): List<Article> {
        return apiService.getArticleData()
            .map { dto -> dto.toDomain() }
    }

    suspend fun getArticleById(articleId: String): Article? {
        return fetchArticleData().find { it.id.toString() == articleId }
    }
}

class ApiErrorException(message: String) : Exception(message)
