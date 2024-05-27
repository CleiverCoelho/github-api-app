package com.plcoding.roomguideandroid

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import android.Manifest
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.plcoding.roomguideandroid.data.PushNotificationService
import com.plcoding.roomguideandroid.ui.GitHubViewModel
import com.plcoding.roomguideandroid.ui.RepositoriesScreen
import com.plcoding.roomguideandroid.ui.RepositoryInfoScreen
import com.plcoding.roomguideandroid.ui.theme.RoomGuideAndroidTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean -> if (isGranted) { } else { } }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Do your task on permission granted
            val createPush = PushNotificationService()
        } else {
            // Directly ask for the permission
             requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            RoomGuideAndroidTheme {

                val gitHubViewModel : GitHubViewModel = koinViewModel()
                val gitHubUiState by gitHubViewModel.uiState.collectAsState()
                // receive custom data due to pushNotification redirecting
                var customData = intent.extras?.getString("customData")

                if(customData != null) {
                    val userAndRepo = customData.split("/")
                    Log.i("USERREPO EXTRA", "$customData")
                    val user = userAndRepo[0].trim()
                    val repositorieName = userAndRepo[1].trim()
                    gitHubViewModel.getGitHubUserInfo(user)
                    gitHubViewModel.getGitHubRepositories(user, repositorieName)
                    intent.removeExtra("customData")
                }

                if(!gitHubUiState.isRepositoryInfoOn) {
                    RepositoriesScreen(gitHubViewModel, gitHubUiState)
                } else {
                    RepositoryInfoScreen(gitHubViewModel, gitHubUiState)
                }
            }
        }
    }
}
