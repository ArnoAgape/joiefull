package com.openclassrooms.joiefull.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsScreen(articleId: String) {
    // Ici, on pourrait récupérer les données depuis un ViewModel
    // val viewModel: ArticleDetailsViewModel = hiltViewModel()
    // val article by viewModel.getArticle(articleId).collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails de l'article") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "ID de l'article : $articleId",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Ici tu affiches le contenu complet de l'article.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


