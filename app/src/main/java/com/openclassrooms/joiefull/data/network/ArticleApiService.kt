package com.openclassrooms.joiefull.data.network

import com.openclassrooms.joiefull.model.Article
import retrofit2.Response
import retrofit2.http.GET

interface ArticleApiService {

    @GET("OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/api/clothes.json")
    suspend fun getArticleData(): Response<List<Article>>
}