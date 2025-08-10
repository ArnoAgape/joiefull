package com.openclassrooms.joiefull.ui.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.model.Article
import com.openclassrooms.joiefull.model.Category
import com.openclassrooms.joiefull.model.Picture
import com.openclassrooms.joiefull.model.Section


fun buildSections(all: List<Article>): List<Section> =
    Category.entries
        .map { cat -> Section(cat, all.filter { it.category == cat.toString() }) }
        .filter { it.articles.isNotEmpty() }

@Composable
fun HomeScreen(
    onArticleClick: (Article) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val sections = remember(uiState.article) { buildSections(uiState.article) }

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
    onClick: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .width(198.dp)
            .clickable { onClick(article) },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Image
        Card(elevation = CardDefaults.cardElevation()) {
            AsyncImage(
                model = article.picture.url,
                contentDescription = article.picture.description,
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
                    text = article.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.price.toString(),
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
                    text = article.originalPrice.toString(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun HomeList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
) {
    LazyColumn(modifier) {
        items(articles) { article ->
            ArticleCard(
                article = article,
                onClick = onArticleClick
            )
        }
    }
}

@Preview(showBackground = true, name = "Article")
@Composable
fun ListArticleCardPreview() {
    ArticleCard(
        Article(
            0,
            "Jean pour femme et homme",
            49.99, 59.99,
            4.3, 55,
            Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                description = "Modèle femme qui porte un jean et un haut jaune"
            ),
            category = "BOTTOMS"
        ), onClick = {})
}

@Preview(showBackground = true, name = "HomeScreen")
@Composable
fun HomeScreenPreview() {
    HomeList(
        articles = listOf(
            Article(
                0,
                "Jean pour femme",
                49.99, 59.99,
                4.3, 55,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                    description = "Modèle femme qui porte un jean et un haut jaune"
                ),
                category = "BOTTOMS"),
            Article(
                1,
                "Sac à main orange",
                69.99,69.99,
                4.2, 56,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/accessories/1.jpg",
                    description = "Sac à main orange posé sur une poignée de porte"
                ),
                "ACCESSORIES"),
            Article(
                2,
                "Bottes noires pour l'automne",
                99.99, 119.99,
                3.9, 4,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/shoes/1.jpg",
                    description = "Modèle femme qui pose dans la rue en bottes de pluie noires"
                ),
                "SHOES"),
            Article(
                3,
                "Blazer marron",
                79.99, 79.99,
                4.1, 15,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/tops/1.jpg",
                    description = "Homme en costume et veste de blazer qui regarde la caméra"
                ),
                "TOPS"),
            Article(
                4,
                "Blazer marron",
                79.99, 79.99,
                4.1, 15,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/tops/1.jpg",
                    description = "Homme en costume et veste de blazer qui regarde la caméra"
                ),
                "TOPS"),
            Article(
                5,
                "Jean pour femme",
                49.99, 59.99,
                4.3, 55,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/bottoms/1.jpg",
                    description = "Modèle femme qui porte un jean et un haut jaune"
                ),
                "BOTTOMS"),
            Article(
                6,
                "Sac à main orange",
                69.99,69.99,
                4.2, 56,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/accessories/1.jpg",
                    description = "Sac à main orange posé sur une poignée de porte"
                ),
                "ACCESSORIES"),
            Article(
                7,
                "Bottes noires pour l'automne",
                99.99, 119.99,
                3.9, 4,
                Picture(
                    url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/shoes/1.jpg",
                    description = "Modèle femme qui pose dans la rue en bottes de pluie noires"
                ),
                "SHOES")
        ),
        onArticleClick = {}
    )
}


