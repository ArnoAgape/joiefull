package com.openclassrooms.joiefull.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.openclassrooms.joiefull.data.FakeData.articles
import com.openclassrooms.joiefull.data.repository.ArticleRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Unit tests for [MainViewModel].
 *
 * These tests validate state management for the list and detail screens,
 * including article selection, ratings, and favorites.
 */
class MainViewModelTest {

    private lateinit var repo: ArticleRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repo = mockk()

        coEvery { repo.fetchArticleData() } returns articles

        coEvery { repo.getArticleById("0") } returns articles[0]
        coEvery { repo.getArticleById("1") } returns articles[1]
        coEvery { repo.getArticleById("4") } returns articles[4]

        viewModel = MainViewModel(SavedStateHandle(), repo)
    }

    /**
     * Verifies that clicking an article updates the [DetailState]
     * with the correct article information.
     */
    @Test
    fun `clicking an article updates detailState`() = runTest {

        // Arrange
        viewModel.refresh()

        viewModel.detailState.test {
            // article null because no click
            val initial = awaitItem()
            assertNull(initial.article)

            // Act -> click on article[0]
            viewModel.onArticleClick(articles[0])

            // article[0]
            val detail0 = awaitItem()
            assertNotNull(detail0.article)
            assertEquals(0, detail0.article.id)
            assertEquals("Jean pour femme", detail0.article.name)

            // Act -> click on article[1]
            viewModel.onArticleClick(articles[1])

            // article[1]
            val detail1 = awaitItem()
            assertNotNull(detail1.article)
            assertEquals(1, detail1.article.id)
            assertEquals("Sac à main orange", detail1.article.name)

            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Ensures that [ListState] contains all articles
     * after refreshing the repository.
     */
    @Test
    fun `listState shows all articles`() = runTest {
        viewModel.listState.test {
            skipItems(1) // skip initial state

            val filtered = awaitItem()

            assertEquals(8, filtered.articles.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * Ensures that calling [MainViewModel.setUserRating] updates
     * the [DetailState] with the new user rating.
     */
    @Test
    fun `setUserRating updates detailState`() = runTest {

        val handle = SavedStateHandle(mapOf("selected_article_id" to 4)) // article 4 selected
        val newViewModel = MainViewModel(handle, repo) // viewModel with article 4 selected

        newViewModel.detailState.test {
            skipItems(1) // skip initial state

            val initial = awaitItem()
            assertEquals(0.0, initial.userRating, 0.0)

            newViewModel.setUserRating(4, 3.5)

            val updated = awaitItem()
            assertEquals(3.5, updated.userRating, 0.0)

            cancelAndIgnoreRemainingEvents()
        }
    }
}