package com.plcoding.roomguideandroid.ui

import com.plcoding.roomguideandroid.model.GitHubRepositories
import com.plcoding.roomguideandroid.model.GitHubUser
import com.plcoding.roomguideandroid.model.GitHubUserOwner

data class GitHubUiState(
    val requestLoading: Boolean = false,
    val userOwnerLogin: GitHubUser? = null,
    val isRepositoryInfoOn: Boolean = false,
    val repositories : List<GitHubRepositories>? = null,
    val currentRepositoryInfoOpened: GitHubRepositories? = null,
 )
