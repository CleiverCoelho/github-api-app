package com.plcoding.roomguideandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RepositoriesScreen(
    gitHubViewModel: GitHubViewModel,
    gitHubUiState: GitHubUiState,
    ) {
    val login = if (gitHubUiState.userOwnerLogin?.login != null) "${gitHubUiState.userOwnerLogin?.login}" else "Loading User and Repositories"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Updated Github Login: $login",
                    fontSize = 20.sp
                )
                Text(text = "", fontSize = 12.sp)
            }
            IconButton(onClick = { throw RuntimeException("Test Crash") }) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Open Repository Info"
                )
            }
        }

        gitHubUiState.repositories?.forEach { repositorie ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${repositorie.name} ${repositorie.fullName}",
                        fontSize = 20.sp
                    )
                    Text(text = repositorie.createdAt, fontSize = 12.sp)
                }
                IconButton(onClick = { gitHubViewModel.updateRepositoryInfoLayer(repositorie, true) }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Open Repository Info"
                    )
                }

                IconButton(onClick = {
                    gitHubViewModel.logCustomEvent(
                        repositorie.name.replace("-", "_"),
                        repositorie.url,
                        repositorie.name.replace("-", "_"),
                    )
                }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Custom Event Caller"
                    )
                }
            }
        }
    }
}


