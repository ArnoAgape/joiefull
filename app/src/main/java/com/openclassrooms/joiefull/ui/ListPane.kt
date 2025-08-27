package com.openclassrooms.joiefull.ui

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImage
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.data.FakeData
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.Category
import com.openclassrooms.joiefull.domain.Section
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme

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
                    text = article.price.toString() + " €",
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
                    text = article.originalPrice.toString() + " €",
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

fun buildSections(all: List<Article>): List<Section> =
    Category.entries
        .map { cat -> Section(cat, all.filter { it.category == cat }) }
        .filter { it.articles.isNotEmpty() }

@Composable
fun ListPane(
    state: ListState,
    onItemClick: (article: Article) -> Unit,
) {
    val sections = remember(state.articles) { buildSections(state.articles) }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = paddingValues,
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
                        items(section.articles, key = { it.id }) { article ->
                            ArticleCard(article) { onItemClick(article) }
                        }
                    }
                }
            }
        }
    }
}

data class ListState(
    val articles: List<Article>,
    val selectedArticleId: Int?
)

@Preview(showBackground = true)
@Composable
fun ListPanePreview() {
    JoiefullTheme {
        var selectedItemId by remember { mutableStateOf<Int?>(null) }

        ListPane(
            state = ListState(articles = FakeData.articles, selectedArticleId = selectedItemId),
            onItemClick = { selectedItemId = it.id },
        )
    }
}
