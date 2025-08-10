package com.openclassrooms.joiefull.di

import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideArticleRepository(apiService: ArticleApiService):
            ArticleRepository {
        return ArticleRepository(apiService)
    }
}