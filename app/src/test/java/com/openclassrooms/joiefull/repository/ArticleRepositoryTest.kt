package com.openclassrooms.joiefull.repository

import com.openclassrooms.joiefull.data.model.ArticleDto
import com.openclassrooms.joiefull.data.model.PictureDto
import com.openclassrooms.joiefull.data.network.ArticleApiService
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.domain.toDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ArticleRepositoryTest {

    private val api: ArticleApiService = mockk()
    private lateinit var repo: ArticleRepository

    @Before
    fun setup() {
        repo = ArticleRepository(api)
    }

    @Test
    fun `fetchArticleData returns a list of mapped articles`() = runTest {

        val articleDto = ArticleDto(
            0,
            "Jean pour femme",
            49.99, 59.99,
            55, PictureDto(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                description = "Modèle femme qui porte un jean et un haut jaune"
            ),
            category = "BOTTOMS"
        )
        coEvery { api.getArticleData() } returns listOf(articleDto)
        val expected = articleDto.toDomain()

        // Act
        val result = repo.fetchArticleData()

        // Assert
        assertTrue(result.isNotEmpty())
        assertEquals(listOf(expected), result)
    }

    @Test
    fun `getArticleById returns a candidate`() = runTest {

        val articleDto = ArticleDto(
            0,
            "Jean pour femme",
            49.99, 59.99,
            55, PictureDto(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                description = "Modèle femme qui porte un jean et un haut jaune"
            ),
            category = "BOTTOMS"
        )
        coEvery { api.getArticleData() } returns listOf(articleDto)
        val expected = articleDto.toDomain()

        // Act
        val result = repo.getArticleById("0")

        // Assert
        assertEquals(expected, result)
    }
}