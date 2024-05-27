package com.plcoding.roomguideandroid.ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.plcoding.roomguideandroid.data.repository.GitHubRepository
import com.plcoding.roomguideandroid.model.GitHubRepositories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GitHubViewModel(private val gitHubRepository: GitHubRepository, private val firebaseAnalytics: FirebaseAnalytics): ViewModel() {
    private val _uiState = MutableStateFlow(GitHubUiState())
    val uiState: StateFlow<GitHubUiState> = _uiState.asStateFlow()

    init {
        getGitHubUserInfo("CleiverCoelho")
        getGitHubRepositories("CleiverCoelho", null)
    }

    fun logCustomEvent(eventName: String, parameterKey: String, parameterValue: String) {
        val bundle = Bundle().apply {
            putString(parameterKey, parameterValue)
        }
        firebaseAnalytics.logEvent(eventName, bundle)
        Log.i("CUSTOM EVENT", "parameterKey: $parameterKey, parameterValue: $parameterValue")
    }
    fun updateLoadingStatus() {
        _uiState.update { currentState ->
            currentState.copy(
                requestLoading = true
            )
        }
    }

    fun updateRepositoryInfoLayer(repoInfoOpened : GitHubRepositories?, isOpen: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isRepositoryInfoOn = isOpen,
                currentRepositoryInfoOpened = repoInfoOpened,
            )
        }
    }

    fun getGitHubUserInfo(user : String) {
        updateLoadingStatus()
        viewModelScope.launch {
            val userOwnerLogin = gitHubRepository.getGitHubOwnerInfo(user)
            _uiState.update { currentState ->
                currentState.copy(
                    requestLoading = !currentState.requestLoading,
                    userOwnerLogin = userOwnerLogin
                )
            }
        }
    }

    fun getGitHubRepositories(user : String, repositoryName : String?) {
        updateLoadingStatus()
        // update repositorie layer in case it comes from a notification
        viewModelScope.launch {
            if(repositoryName != null) {
                val repository = gitHubRepository.getGitHubRepositoryByName(user, repositoryName)
                updateRepositoryInfoLayer(repository, true)
            }
            val repositories = gitHubRepository.getGitHubRepositories(user)
            _uiState.update { currentState ->
                currentState.copy(
                    requestLoading = !currentState.requestLoading,
                    repositories = repositories
                )
            }
        }
    }
}