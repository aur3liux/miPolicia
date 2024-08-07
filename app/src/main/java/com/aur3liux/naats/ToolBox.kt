package com.aur3liux.naats

import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

object ToolBox {

    val gmxFontRegular = FontFamily(Font(R.font.gmx_regular))
    val montseFont = FontFamily(
        Font(R.font.montserrat_bold),
        Font(R.font.montserrat_medium),
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_italic)
    )

    //Eejecuta un efecto de sonido recibido como parámetro
    fun soundEffect(context: Context, soundFile: Int) {
        var mediaPlayer = MediaPlayer.create(context, soundFile)
        mediaPlayer.setVolume(0.5f, 0.5f)
        mediaPlayer.start()
    }

    //Verifica que el dispositivo tenga señal de internet
    fun testConectivity(context: Context): Boolean {
        val isconnected =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(isconnected)
    }

    private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): Boolean {
        val connected = connectivityManager.allNetworks.any { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        }
        return if (connected) true else false
    }


    //Formatea la fecha tomada desde el dispositivo
    fun getLocalDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(Date())
        return current
    }


    //Le damos formato a la fecha del sistema cuando se baja desde una BD
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)
        val part = formatted.split(" ")
        //val d = part.get(0)
        val h = part.get(1)
        val hora = "${h}.000"
        return hora
    }
}