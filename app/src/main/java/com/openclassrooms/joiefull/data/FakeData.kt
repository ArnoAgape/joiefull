package com.openclassrooms.joiefull.data

import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.Category
import com.openclassrooms.joiefull.domain.Picture

object FakeData {
    val articles = listOf(
        Article(
            0,
            "Jean pour femme",
            49.99, 59.99,
            4.3, 55,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                description = "Modèle femme qui porte un jean et un haut jaune"
            ),
            category = Category.BOTTOMS
        ),
        Article(
            1,
            "Sac à main orange",
            69.99, 69.99,
            4.2, 56,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/accessories/1.jpg",
                description = "Sac à main orange posé sur une poignée de porte"
            ),
            Category.ACCESSORIES
        ),
        Article(
            2,
            "Bottes noires pour l'automne",
            99.99, 119.99,
            3.9, 4,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/shoes/1.jpg",
                description = "Modèle femme qui pose dans la rue en bottes de pluie noires"
            ),
            Category.SHOES
        ),
        Article(
            3,
            "Blazer marron",
            79.99, 79.99,
            4.1, 15,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/tops/1.jpg",
                description = "Homme en costume et veste de blazer qui regarde la caméra"
            ),
            Category.TOPS
        ),
        Article(
            4,
            "Blazer marron",
            79.99, 79.99,
            4.1, 15,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/tops/1.jpg",
                description = "Homme en costume et veste de blazer qui regarde la caméra"
            ),
            Category.TOPS
        ),
        Article(
            5,
            "Jean pour femme",
            49.99, 59.99,
            4.3, 55,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                description = "Modèle femme qui porte un jean et un haut jaune"
            ),
            Category.BOTTOMS
        ),
        Article(
            6,
            "Sac à main orange",
            69.99, 69.99,
            4.2, 56,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/accessories/1.jpg",
                description = "Sac à main orange posé sur une poignée de porte"
            ),
            Category.ACCESSORIES
        ),
        Article(
            7,
            "Bottes noires pour l'automne",
            99.99, 119.99,
            3.9, 4,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/shoes/1.jpg",
                description = "Modèle femme qui pose dans la rue en bottes de pluie noires"
            ),
            Category.SHOES
        )
    )
}
