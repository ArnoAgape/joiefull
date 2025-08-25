package com.openclassrooms.joiefull.data.network

import com.openclassrooms.joiefull.data.model.ArticleDto
import retrofit2.http.GET

interface ArticleApiService {

    @GET("OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/api/clothes.json")
    suspend fun getArticleData(): List<ArticleDto>
}