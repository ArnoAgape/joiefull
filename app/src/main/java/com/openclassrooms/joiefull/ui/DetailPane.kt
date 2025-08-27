package com.openclassrooms.joiefull.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.domain.Article
import com.openclassrooms.joiefull.domain.Category
import com.openclassrooms.joiefull.domain.Picture
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPane(
    state: DetailState,
    showBack: Boolean,
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
                    Text(stringResource(R.string.no_item_selected))
                }
            } else {
                Card(elevation = CardDefaults.cardElevation()) {
                    Box {
                        ZoomableImage(
                            url = state.article.picture.url,
                            description = state.article.picture.description
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
                                    tint = if (state.isFavorite) Color.Black else LocalContentColor.current,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { onFavoriteClick() }
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = state.article.likes.toString(),
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
                            text = state.article.name,
                            style = MaterialTheme.typography.titleSmall,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = "${state.article.price} €",
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
                                text = state.userRating.toString(),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                        Text(
                            text = "${state.article.originalPrice} €",
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
                        text = state.article.picture.description,
                        style = MaterialTheme.typography.bodyMedium
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
                            Icon(
                                imageVector = if (i <= state.userRating) Icons.Default.Star else Icons.Outlined.Star,
                                contentDescription = null,
                                tint = if (i <= state.userRating) Color(0xFFFFC107) else Color.Gray,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clickable { state.article.id.let { id -> onRatingSelected(id, i.toDouble()) } }

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
fun ZoomableImage(
    url: String,
    description: String
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    AsyncImage(
        model = url,
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(479.dp)
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, pan, zoom, _ ->
                        scope.launch {
                            scale.snapTo((scale.value * zoom).coerceIn(1f, 5f))
                            offset.snapTo(offset.value + pan)
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.changes.all { !it.pressed }) {
                            scope.launch {
                                scale.animateTo(1f)
                                offset.animateTo(Offset.Zero)
                            }
                        }
                    }
                }
            }
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value,
                translationX = offset.value.x,
                translationY = offset.value.y
            )
    )
}

data class DetailState(
    val article: Article?,
    val isFavorite: Boolean = false,
    val userRating: Double = 0.0
)

@Preview(showBackground = true)
@Composable
fun ArticleDetailsPreview() {
    JoiefullTheme {
        DetailPane(
            onBackClick = {},
            onShareClick = {},
            onFavoriteClick = {},
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
            onRatingSelected = { _, stars -> }
        )
    }
}
