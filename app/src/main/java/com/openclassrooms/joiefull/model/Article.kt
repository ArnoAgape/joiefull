package com.openclassrooms.joiefull.model

import androidx.annotation.StringRes
import com.openclassrooms.joiefull.R

data class Article(
    val id: Int,
    val title: String,
    val price: Double,
    val oldPrice: Double,
    val rate: Double?,
    val favorite: Int,
    val picture: String,
    val category: String
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

