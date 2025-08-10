package com.openclassrooms.joiefull.data.repository

import android.util.Log
import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.model.Article
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton


@Singleton
class ArticleRepository @Inject constructor(private val apiService: ArticleApiService) {

    fun fetchArticleData(): Flow<List<Article>> = flow {
        val result = apiService.getArticleData()
        val model = result.body()?.toDomainModel() ?: throw Exception("Invalid data")

        emit(model)
    }.catch { error ->
    Log.e("ArticleRepository", error.message ?: "")}

}
