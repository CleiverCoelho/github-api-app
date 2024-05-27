package com.plcoding.roomguideandroid.data.repository

import android.util.Log
import com.google.gson.Gson
import com.plcoding.roomguideandroid.data.ApiService
import com.plcoding.roomguideandroid.model.GitHubUserOwner
import com.plcoding.roomguideandroid.model.GitHubRepositories
import com.plcoding.roomguideandroid.model.GitHubUser
import retrofit2.Response

class GitHubRepositoryImpl(private val apiService: ApiService) : GitHubRepository {
    override suspend fun getGitHubOwnerInfo(user: String) : GitHubUser? {
        return try {
            val result = apiService.getGitHubUserOwnerInfo(user)
            Log.i("USERINFO", "${ result.body() }")
            result.body()
        } catch (e : Exception) {
            Log.d("EXCEPTION: ", "${e.message}")
            return null
        }
    }

    override suspend fun getGitHubRepositories(user: String) : List<GitHubRepositories>? {
        return try {
            val result = apiService.getGitHubRepositories(user)
            Log.i("RESULT>>", "$result")
            return result.body()
        } catch (e : Exception) {
            Log.d("EXCEPTION: ", "${e.message}")
            return null
        }
    }

    override suspend fun getGitHubRepositoryByName(user: String, repositoryName: String) : GitHubRepositories? {
        return try {
            val result = apiService.getGitHubRepositoryByName(user, repositoryName)
            Log.i("RESULT>>", "$result")
            return result.body()
        } catch (e : Exception) {
            Log.d("EXCEPTION: ", "${e.message}")
            return null
        }
    }
}