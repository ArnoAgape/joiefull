package com.openclassrooms.joiefull.data.network

import retrofit2.Response
import retrofit2.http.GET

interface ArticleApiService {

    @GET("api/clothes.json")
    suspend fun getArticleData(): Response<ArticleDataResponse>
}