package com.openclassrooms.joiefull.domain

import androidx.annotation.StringRes
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.data.model.ArticleDto

data class Article(
    val id: Int,
    val name: String,
    val price: Double,
    val originalPrice: Double,
    val rate: Double = 0.0,
    val likes: Int,
    val picture: Picture,
    val category: Category
)

data class Picture(
    val url: String,
    val description: String?
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

fun ArticleDto.toDomain(): Article = Article(
    id = id,
    name = name,
    price = price,
    originalPrice = originalPrice,
    rate = 0.0,
    likes = likes,
    picture = Picture(pictureDto.url, pictureDto.description),
    category = Category.valueOf(category.uppercase())
)



