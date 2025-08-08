package com.openclassrooms.joiefull.data

import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.model.Article
import com.openclassrooms.joiefull.model.Category

object ArticleData {

    private val first = Article(
        0,
        "Jean pour femme",
        "49.99 €", "59.99 €",
        4.3, 55,
        R.drawable.img_pants,
        Category.BOTTOMS)

    private val second = Article(
        1,
        "Sac à main orange",
        "69.99 €","69.99 €",
        4.2, 56,
        R.drawable.img_bag,
        Category.ACCESSORIES)

    private val third = Article(
        2,
        "Bottes noires pour l'automne",
        "99.99 €", "119.99 €",
        3.9, 4,
        R.drawable.img_boots,
        Category.SHOES)

    private val fourth = Article(
        3,
        "Blazer marron",
        "79.99 €", "79.99 €",
        4.1, 15,
        R.drawable.img_blazer,
        Category.TOPS)

    private val fifth = Article(
        4,
        "Blazer marron",
        "79.99 €", "79.99 €",
        4.1, 15,
        R.drawable.img_blazer,
        Category.TOPS)

    val articlesList = listOf(first, second, third, fourth, fifth)
}