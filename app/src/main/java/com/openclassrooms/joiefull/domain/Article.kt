package com.openclassrooms.joiefull.domain

import androidx.annotation.StringRes
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.data.model.ArticleDto

/**
 * Domain model representing an article.
 *
 * @property id Unique identifier of the article.
 * @property name Name of the article.
 * @property price Current price of the article.
 * @property originalPrice Original (non-discounted) price of the article.
 * @property rate Rating of the article (default: 0.0).
 * @property likes Number of likes the article received.
 * @property picture Associated picture of the article.
 * @property category Category of the article.
 */
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

/**
 * Domain model representing a picture.
 *
 * @property url Direct URL to the image.
 * @property description Optional description for accessibility purposes.
 */
data class Picture(
    val url: String,
    val description: String?
)

/**
 * Represents a section grouping multiple articles by category.
 *
 * @property category Category of the section.
 * @property articles List of articles belonging to that category.
 */
data class Section(
    val category: Category,
    val articles: List<Article>
)

/**
 * Enum representing all available article categories.
 */
enum class Category {
    TOPS,
    BOTTOMS,
    ACCESSORIES,
    SHOES;

    /**
     * Returns the translated string resource for each category.
     */
    @get:StringRes
    val translatedName: Int
        get() = when (this) {
            TOPS -> R.string.category_tops
            BOTTOMS -> R.string.category_bottoms
            SHOES -> R.string.category_shoes
            ACCESSORIES -> R.string.category_accessories
        }
}

/**
 * Extension function mapping [ArticleDto] to the domain [Article].
 *
 * @return The corresponding domain [Article] object.
 */
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



