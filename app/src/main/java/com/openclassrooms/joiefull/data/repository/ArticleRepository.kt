package com.openclassrooms.joiefull.data.repository

import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.toDomain

/**
 * Repository responsible for fetching and transforming article data
 * between the remote API and the domain model.
 *
 * @property apiService Retrofit service used to fetch data.
 */
class ArticleRepository (private val apiService: ArticleApiService) {

    /**
     * Fetches all articles from the API and maps them to domain models.
     *
     * @return A list of [Article] domain objects.
     */
    suspend fun fetchArticleData(): List<Article> {
        return apiService.getArticleData()
            .map { dto -> dto.toDomain() }
    }

    /**
     * Retrieves an article by its ID.
     *
     * @param articleId The ID of the article as a string.
     * @return The matching [Article] or null if not found or invalid ID.
     */
    suspend fun getArticleById(articleId: String): Article? {
        val id = articleId.toIntOrNull() ?: return null
        return fetchArticleData().find { it.id == id }
    }
}
