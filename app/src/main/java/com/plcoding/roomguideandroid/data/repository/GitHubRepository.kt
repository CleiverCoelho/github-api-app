package com.plcoding.roomguideandroid.data.repository

import com.plcoding.roomguideandroid.model.GitHubRepositories
import com.plcoding.roomguideandroid.model.GitHubUser
import retrofit2.Response

interface GitHubRepository {
    suspend fun getGitHubRepositories(user: String) : List<GitHubRepositories>?
    suspend fun getGitHubOwnerInfo(user: String): GitHubUser?

    suspend fun getGitHubRepositoryByName(user: String, repositoryName: String): GitHubRepositories?

}