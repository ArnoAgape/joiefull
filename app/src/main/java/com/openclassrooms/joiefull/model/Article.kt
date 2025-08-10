package com.openclassrooms.joiefull.model

import androidx.annotation.StringRes
import com.openclassrooms.joiefull.R
import com.squareup.moshi.Json

data class Article(
    val id: Int,
    val name: String,
    val price: Double,
    @Json(name = "original_price") val originalPrice: Double,
    val rate: Double?,
    val likes: Int,
    val picture: Picture,
    val category: String
)

data class Picture(
    val url: String,
    val description: String
)

data class Section(
    val category: Category,
    val articles: List<Article>
)

enum class Category {
    TOPS,
    BOTTOMS,
    ACCESSORIES,
    SHOES;

    @get:StringRes
    val translatedName: Int
        get() = when (this) {
            TOPS -> R.string.category_tops
            BOTTOMS -> R.string.category_bottoms
            SHOES -> R.string.category_shoes
            ACCESSORIES -> R.string.category_accessories
        }
}

