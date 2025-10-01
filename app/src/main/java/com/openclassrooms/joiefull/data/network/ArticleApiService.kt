package com.openclassrooms.joiefull.data.network

import com.openclassrooms.joiefull.data.model.ArticleDto
import retrofit2.http.GET

/**
 * Retrofit API service interface used to fetch article data from a remote source.
 */
interface ArticleApiService {
    /**
     * Retrieves a list of articles from the remote JSON endpoint.
     *
     * @return A list of [ArticleDto] objects.
     */
    @GET("api/clothes.json")
    suspend fun getArticleData(): List<ArticleDto>
}