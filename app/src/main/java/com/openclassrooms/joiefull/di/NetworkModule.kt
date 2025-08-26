package com.openclassrooms.joiefull.di

import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private val retrofitSingleton: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(provideOkHttpClient())
            .build()
    }

    private val apiSingleton: ArticleApiService by lazy {
        retrofitSingleton.create(ArticleApiService::class.java)
    }

    private val repoSingleton: ArticleRepository by lazy {
        ArticleRepository(apiSingleton)
    }

    fun provideRetrofit(): Retrofit = retrofitSingleton
    fun provideArticleApiService(): ArticleApiService = apiSingleton
    fun provideArticleRepository(): ArticleRepository = repoSingleton

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}
