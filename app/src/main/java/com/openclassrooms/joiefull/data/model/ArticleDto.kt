package com.openclassrooms.joiefull.data.model

import com.squareup.moshi.Json

data class ArticleDto(
    val id: Int,
    val name: String,
    val price: Double,
    @Json(name = "original_price") val originalPrice: Double,
    val likes: Int,
    val pictureDto: PictureDto,
    val category: String
)

data class PictureDto(
    val url: String,
    val description: String
)

data class SectionDto(
    val category: String,
    val articles: List<ArticleDto>
)

