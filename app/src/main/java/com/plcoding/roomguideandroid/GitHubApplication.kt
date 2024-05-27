package com.plcoding.roomguideandroid

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.plcoding.roomguideandroid.di.GitHubModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitHubApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase Analytics
        FirebaseAnalytics.getInstance(this)
        startKoin {
            androidContext(this@GitHubApplication)
            modules(GitHubModule)
        }
    }
}