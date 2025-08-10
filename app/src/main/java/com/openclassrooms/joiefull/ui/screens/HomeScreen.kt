package com.openclassrooms.joiefull.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.data.ArticleData.articlesList
import com.openclassrooms.joiefull.model.Article
import com.openclassrooms.joiefull.model.Category
import com.openclassrooms.joiefull.model.Section

fun buildSections(all: List<Article>): List<Section> =
    Category.entries
        .map { cat -> Section(cat, all.filter { it.category == cat }) }
        .filter { it.articles.isNotEmpty() } // on ne garde que les sections non vides


@Composable
fun HomeScreen(
    articles: List<Article> = articlesList,
    onArticleClick: (Article) -> Unit = {},
    ) {
    val sections = remember(articles) { buildSections(articles) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sections.forEach { section ->
            item(key = section.category.name) {
                Text(
                    text = stringResource(id = section.category.translatedName),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(
                        items = section.articles,
                        key = { it.id }
                    ) { article ->
                        ArticleCard(article) { onArticleClick(article) }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(198.dp)
            .clickable { onClick()},
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Image
        Card(elevation = CardDefaults.cardElevation()) {
            Image(
                painter = painterResource(article.picture),
                contentDescription = "Modèle femme qui porte un jean et un haut jaune",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(198.dp)
                    .height(198.dp)
            )
        }

        // Texte sous l'image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.price,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Column(
                modifier = Modifier.weight(1.3f),
                horizontalAlignment = Alignment.End
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = article.rate.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Text(
                    text = article.oldPrice,
                    style = MaterialTheme.typography.titleSmall.copy(
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray),
                    maxLines = 1
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Article")
@Composable
fun ListArticleCardPreview() {
    ArticleCard(
        Article(0,
            "Jean pour femme et homme",
            "49.99 €", "59.99 €",
            4.3, 55,
            R.drawable.img_pants,
            Category.BOTTOMS), onClick = {})
}

@Preview(showBackground = true, name = "HomeScreen")
@Composable
fun HomeScreenPreview() {
    HomeScreen(articles = articlesList)
}


