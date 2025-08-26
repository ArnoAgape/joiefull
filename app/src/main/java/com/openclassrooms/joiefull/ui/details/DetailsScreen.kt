package com.openclassrooms.joiefull.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.Category
import com.openclassrooms.joiefull.domain.Picture

@Composable
fun ArticleDetailsScreen(article: Article? = null) {
    if (article != null) {
        // Mode tablette : on a déjà l'objet
        ArticleDetailsContent(
            article = article,
            onBackClick = { /* TODO: gérer le retour tablette (ex: désélectionner) */ },
            onShareClick = { /* TODO: implémenter un intent de partage */ },
            onFavoriteClick = { /* TODO: toggle favori */ },
            favorite = false // valeur initiale (tu peux la gérer dans un remember ou ViewModel)
        )
    } else {
        // Mode téléphone : on charge via ViewModel
        val viewModel: DetailsViewModel = hiltViewModel()
        val articleFromVm by viewModel.article.collectAsState()

        if (articleFromVm == null) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            ArticleDetailsContent(
                article = articleFromVm!!,
                onBackClick = { /* ex: navController.popBackStack() */ },
                onShareClick = { /* TODO: implémenter un intent de partage */ },
                onFavoriteClick = { /* TODO: toggle favori */ },
                favorite = false
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsContent(
    article: Article,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    favorite: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Card(elevation = CardDefaults.cardElevation()) {
            Box {
                AsyncImage(
                    model = article.picture.url,
                    contentDescription = article.picture.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(479.dp)
                )
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = stringResource(R.string.share),
                    )
                }
                Surface(
                    shape = RoundedCornerShape(46.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription =
                                if (favorite) stringResource(R.string.no_favorite)
                                else stringResource(R.string.favorite),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onFavoriteClick() }
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "56",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "${article.price} €",
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
                    text = "${article.originalPrice} €",
                    style = MaterialTheme.typography.titleSmall.copy(
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    ),
                    maxLines = 1
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = article.picture.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_profile),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            // RatingBar
            var rating by remember { mutableIntStateOf(0) }

            Row {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier
                            .size(45.dp)
                            .clickable { rating = i }
                    )
                }
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {

            var text by remember { mutableStateOf("") }

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(R.string.comment)) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArticleDetailsPreview() {
    val fakeArticle = Article(
        0,
        "Bottes noires pour l'automne",
        99.99, 119.99,
        4.3, 55,
        picture = Picture(
            url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/shoes/1.jpg",
            description = "Modèle femme qui pose dans la rue en bottes de pluie noires"
        ),
        Category.SHOES
    )
    ArticleDetailsContent(fakeArticle, onBackClick = {}, onShareClick = {}, favorite = false, onFavoriteClick = {})
}


