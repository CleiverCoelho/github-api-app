package com.plcoding.roomguideandroid.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.plcoding.roomguideandroid.data.ApiService
import com.plcoding.roomguideandroid.data.HttpClient
import com.plcoding.roomguideandroid.data.repository.GitHubRepository
import com.plcoding.roomguideandroid.data.repository.GitHubRepositoryImpl
import com.plcoding.roomguideandroid.ui.GitHubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val GitHubModule = module {

    single { HttpClient().api }

    single { FirebaseAnalytics.getInstance(get()) }

    single<GitHubRepository> { GitHubRepositoryImpl(get()) }

    //ViewModel koin definition
    viewModel { GitHubViewModel(get(), get()) }

}

