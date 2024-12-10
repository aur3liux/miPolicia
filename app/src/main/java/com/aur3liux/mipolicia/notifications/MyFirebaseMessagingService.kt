package com.aur3liux.naats.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("NEW_TOKEN", "Refreshed token: $token")
        //Aqui guardamos el token en BD.
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationHandler = NotificationHandler(this)
        notificationHandler.showSimpleNotification(message.notification!!.title!!, message.notification!!.body!!)
        //Quien envia la notificacion
        Log.d("PUSH", "From: ${message.from}")
        //Título de la notificacion
        Log.i("PUSH", "Message: ${message.notification!!.title}")
        // Verifica si el mensaje contiene datos en el payload
        if (message.data.isNotEmpty()) {
            Log.d("PUSH", "Message data payload: ${message.data}")
            //Se procesan los datos en un hilo
        }

        // Muestra el contenido de la notificacion completa
        message.notification?.let {
            Log.d("PUSH", "Message Notification Body: ${it.body}")
        }

    }
}