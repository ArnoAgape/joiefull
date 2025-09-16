package com.openclassrooms.joiefull.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.Category
import com.openclassrooms.joiefull.domain.Picture
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MainViewModelTest {

    private lateinit var repo: ArticleRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repo = mockk()

        coEvery { repo.fetchArticleData() } returns listOf(
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
                category = Category.ACCESSORIES
            )
        )

        coEvery { repo.getArticleById("4") } returns
                Article(
                    4,
                    "Jean pour femme",
                    49.99, 59.99,
                    4.3, 55,
                    Picture(
                        url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                        description = "Modèle femme qui porte un jean et un haut jaune"
                    ),
                    category = Category.BOTTOMS
                )
        val handle = SavedStateHandle(mapOf("selected_article_id" to 4))
        println("🔍 savedStateHandle['selected_article_id'] = ${handle.get<Int>("selected_article_id")}")

        viewModel = MainViewModel( handle, repo)
    }

    @Test
    fun `listState shows all articles`() = runTest {
        viewModel.listState.test {
            skipItems(1) // skip initial state

            val filtered = awaitItem()

            assertEquals(2, filtered.articles.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `detailState shows one article`() = runTest {
        viewModel.detailState.test {
            skipItems(1) // skip initial state

            val state = awaitItem()

            assertNotNull(state.article)
            assertEquals(4, state.article.id)
            assertEquals("Jean pour femme", state.article.name)
            assertEquals(false, state.isFavorite)
            assertEquals(0.0, state.userRating)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setUserRating updates detailState`() = runTest {
        viewModel.detailState.test {
            skipItems(1) // skip initial state

            val initial = awaitItem()

            assertEquals(0.0, initial.userRating, 0.0)

            viewModel.setUserRating(4, 3.5)

            val updated = awaitItem()
            assertEquals(3.5, updated.userRating, 0.0)

            cancelAndIgnoreRemainingEvents()
        }
    }
}