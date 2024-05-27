package com.plcoding.roomguideandroid.data

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.plcoding.roomguideandroid.MainActivity
import com.plcoding.roomguideandroid.R


class PushNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!.body

        // to receive any custom aditional data
        // val customData = remoteMessage.data["custom_key"]

        val CHANNEL_ID = "TESTE_NOTIFICATION"
        val channel : NotificationChannel = NotificationChannel(CHANNEL_ID, title, NotificationManager.IMPORTANCE_HIGH)
        val requestCode = System.currentTimeMillis().toInt()

        // creates intent for activity redirecting
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("customData", title)
        // only calls main activity when the notification is clicked
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        // link the channel app with the device
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        // build the notification parameters
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setChannelId(CHANNEL_ID)
            .setContentText(text)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            // launch notification overlay was missing last time
            .setSmallIcon(R.drawable.dti_digital_logo)
            .setAutoCancel(true)

        // send the notification through the channel
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("PERMISSION", "DENIED")
            return
        }
        // the notification goes through the channel
        NotificationManagerCompat.from(this).notify(1, notification.build());
    }
}