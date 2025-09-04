package com.openclassrooms.joiefull.ui

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.Category
import com.openclassrooms.joiefull.domain.Picture
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPane(
    state: DetailState,
    showBack: Boolean,
    showNoItem: Boolean,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRatingSelected: (articleId: Int, stars: Double) -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues),
        ) {
            if (state.article == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!showNoItem) {
                        Text(stringResource(R.string.no_item_selected))
                    }
                }
            } else {
                val priceDescription = stringResource(
                    R.string.price,
                    Utils.formatPriceForAccessibility(state.article.price)
                )
                val rateDescription = stringResource(
                    R.string.rate,
                    state.article.rate
                )
                val originalPriceDescription = stringResource(
                    R.string.original_price,
                    Utils.formatPriceForAccessibility(state.article.originalPrice)
                )
                val likesDescription = pluralStringResource(
                    R.plurals.likes,
                    state.article.likes,
                    state.article.likes
                )
                Card(
                    elevation = CardDefaults.cardElevation(),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box {
                        ImageGallery(
                            url = state.article.picture.url,
                            description = stringResource(R.string.description)
                        )
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp)
                        ) {
                            if (showBack) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = stringResource(R.string.back),
                                )
                            }
                        }
                        IconButton(
                            onClick = onShareClick,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
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
                                    imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription =
                                        if (state.isFavorite) stringResource(R.string.no_favorite)
                                        else stringResource(R.string.favorite),
                                    tint = if (state.isFavorite) MaterialTheme.colorScheme.primary else LocalContentColor.current,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { onFavoriteClick() }
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = state.article.likes.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.semantics {
                                        contentDescription = likesDescription
                                    }
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
                            text = state.article.name,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        val articlePrice = state.article.price
                        val articlePriceWithCurrency =
                            Utils.formatAmount(articlePrice, Locale.getDefault())
                        Text(
                            text = articlePriceWithCurrency,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.semantics {
                                contentDescription = priceDescription
                            }
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
                                text = state.userRating.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.semantics {
                                    contentDescription = rateDescription
                                }
                            )
                        }
                        val articleOriginalPrice = state.article.originalPrice
                        val articleOriginalPriceWithCurrency =
                            Utils.formatAmount(articleOriginalPrice, Locale.getDefault())
                        Text(
                            text = articleOriginalPriceWithCurrency,
                            style = MaterialTheme.typography.titleSmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            ),
                            modifier = Modifier.semantics {
                                contentDescription = originalPriceDescription
                            },
                            color = MaterialTheme.colorScheme.primary,
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
                        text = state.article.picture.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Start),
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
                    Row {
                        for (i in 1..5) {
                            val reviewDescription = pluralStringResource(
                                R.plurals.review_with_star,
                                count = i, i
                            )
                            Icon(
                                imageVector = if (i <= state.userRating) Icons.Default.Star else Icons.Outlined.Star,
                                contentDescription = reviewDescription,
                                tint = if (i <= state.userRating) Color(0xFFFFC107) else Color.Gray,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clickable {
                                        state.article.id.let { id ->
                                            onRatingSelected(
                                                id,
                                                i.toDouble()
                                            )
                                        }
                                    }

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
    }
}

// To zoom-in
@Composable
fun ImageGallery(url: String, description: String) {
    var selectedImage by remember { mutableStateOf<String?>(null) }

    AsyncImage(
        model = url,
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(479.dp)
            .clickable { selectedImage = url }
    )
    // Full screen image
    if (selectedImage != null) {
        Dialog(
            onDismissRequest = { selectedImage = null }, // closes when click
            properties = DialogProperties(usePlatformDefaultWidth = false) // full screen
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { selectedImage = null }, // closes when click
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = selectedImage,
                    contentDescription = description,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


data class DetailState(
    val article: Article?,
    val isFavorite: Boolean = false,
    val userRating: Double = 0.0
)

@PreviewLightDark
@Composable
fun ArticleDetailsPreview() {
    JoiefullTheme {
        DetailPane(
            onBackClick = {},
            onShareClick = {},
            onFavoriteClick = {},
            // onImageClick = {},
            state = DetailState(
                article = Article(
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
            ),
            showBack = true,
            showNoItem = false,
            onRatingSelected = { _, stars -> }
        )
    }
}
