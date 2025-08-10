package com.openclassrooms.joiefull.data.network

import com.openclassrooms.joiefull.model.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDataResponse(
    @Json(name = "list")
    val articles: List<ArticleResponse>
) {

    @JsonClass(generateAdapter = true)
    data class ArticleResponse(
        @Json(name = "id")
        val id: Int,
        @Json(name = "picture")
        val picture: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "category")
        val category: String,
        @Json(name = "likes")
        val likes: Int,
        @Json(name = "price")
        val price: Double,
        @Json(name = "original_price")
        val originalPrice: Double
    )

    fun toDomainModel(): List<Article> {
        return articles.map { article ->
        Article (
            id = article.id,
            picture = article.picture,
            title = article.name,
            category = article.category,
            favorite = article.likes,
            price = article.price,
            oldPrice = article.originalPrice,
            rate = null
        )
    }
    }
}
