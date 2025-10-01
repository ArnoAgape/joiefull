package com.openclassrooms.joiefull.data.model

import com.squareup.moshi.Json

/**
 * Data Transfer Object (DTO) representing an article retrieved from the API.
 *
 * @property id Unique identifier of the article.
 * @property name Name of the article.
 * @property price Current price of the article.
 * @property originalPrice Original (non-discounted) price of the article.
 * @property likes Number of likes associated with the article.
 * @property pictureDto DTO containing picture information for the article.
 * @property category Category name as returned by the API (e.g., "TOPS", "BOTTOMS").
 */
data class ArticleDto(
    val id: Int,
    val name: String,
    val price: Double,
    @Json(name = "original_price") val originalPrice: Double,
    val likes: Int,
    @Json(name = "picture") val pictureDto: PictureDto,
    val category: String
)

/**
 * Data Transfer Object (DTO) representing picture information for an article.
 *
 * @property url Direct URL to the image.
 * @property description Optional description of the image, useful for accessibility.
 */
data class PictureDto(
    val url: String,
    val description: String?
)

