package com.openclassrooms.joiefull

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(articlesList) { article ->
            ArticleCard(article)
        }
    }
}

@Composable
fun ArticleCard(article: Article) {
    Column(
        modifier = Modifier
            .width(198.dp)
            .padding(16.dp)
    ) {
        Card(
            elevation = CardDefaults.cardElevation()
        ) {
            Column {
                Box(modifier = Modifier.size(198.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.img_pants),
                        contentDescription = "Modèle femme qui porte un jean et un haut jaune",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🟩 Colonne à gauche
            Column {
                Text(text = article.title, style = MaterialTheme.typography.titleSmall)
                Text(text = article.price, style = MaterialTheme.typography.titleSmall)
            }

            // 🟦 Texte à droite
            Column {
                Text(text = article.rate.toString(), style = MaterialTheme.typography.titleSmall)
                Text(text = article.oldPrice, style = MaterialTheme.typography.titleSmall,
                    textDecoration = TextDecoration.LineThrough)
            }
        }

    }
}


data class Article(val title: String, val price: String, val rate: Double, val oldPrice: String)

val articlesList = listOf(
    Article("Veste urbaine", "89 €", 4.3, "120 €"),
    Article("Pull torsadé", "69 €", 4.1, "95 €")
)

@Preview(showBackground = true)
@Composable
fun PreviewListArticleCard() {
    ArticleCard(
        Article("Veste urbaine", "89 €", 4.3, "120 €")
    )

}


