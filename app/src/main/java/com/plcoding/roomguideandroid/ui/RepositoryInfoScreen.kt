package com.plcoding.roomguideandroid.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//import coil.compose.AsyncImage

@Composable
fun RepositoryInfoScreen(gitHubViewModel: GitHubViewModel, gitHubUiState: GitHubUiState) {
    val avatarUrl = if (gitHubUiState.userOwnerLogin?.avatarUrl != null) "${gitHubUiState.userOwnerLogin?.avatarUrl}" else "Loading User and Repositories"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().align(Start)
        ) {
            IconButton(onClick = { gitHubViewModel.updateRepositoryInfoLayer(null, false) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go Back",
                        modifier = Modifier.size(40.dp)                    )
                }
            }

            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .align(CenterHorizontally)
                    .clip(CircleShape)
                    .size(220.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nome do Repositório: ${gitHubUiState.currentRepositoryInfoOpened?.name} " +
                                "\n\nVisibilidade: ${gitHubUiState.currentRepositoryInfoOpened?.visibility}" +
                                "\n\nCriado em: ${gitHubUiState.currentRepositoryInfoOpened?.createdAt}"
                        ,
                        fontSize = 20.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
}