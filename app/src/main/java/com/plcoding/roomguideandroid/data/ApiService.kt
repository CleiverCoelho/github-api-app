package com.plcoding.roomguideandroid.data

import com.plcoding.roomguideandroid.model.GitHubRepositories
import com.plcoding.roomguideandroid.model.GitHubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{user}/repos?page=1&per_page=10")
    suspend fun getGitHubRepositories (@Path("user") user: String) : Response <List<GitHubRepositories>>
    @GET("repos/{user}/{repoName}")
    suspend fun getGitHubRepositoryByName (@Path("user") user: String, @Path("repoName") repoName: String) : Response <GitHubRepositories>
    @GET("users/{user}")
    suspend fun getGitHubUserOwnerInfo (@Path("user") user: String) : Response<GitHubUser>
}