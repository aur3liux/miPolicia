package com.aur3liux.mipolicia

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

object ToolBox {

    val gmxFontRegular = FontFamily(Font(R.font.gmx_regular))
    val montseFont = FontFamily(
        Font(R.font.montserrat_bold),
        Font(R.font.montserrat_medium),
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_italic)
    )

    val quatroSlabFont = FontFamily(
        Font(R.font.quatros_semibold, weight = FontWeight.SemiBold),
        Font(R.font.quatros_bold, weight = FontWeight.Bold),
        Font(R.font.quatros_italic, weight = FontWeight.Light),
        Font(R.font.quatros_medium, weight = FontWeight.Medium),
        Font(R.font.quatros_regular, weight = FontWeight.Normal)
    )

    fun validatePassword(passwordInput: String): Int {
        var evaluatePassword = 0

        if (!passwordInput.matches(".*[0-9].*".toRegex())) {
            evaluatePassword = 1 //Al menos un digito
        } else if (!passwordInput.matches(".*[a-z].*".toRegex())) {
            evaluatePassword = 2 //Al menos una minúscula
        } else if (!passwordInput.matches(".*[A-Z].*".toRegex())) {
            evaluatePassword = 3 //Al menos una mayúscula
        } else if (!passwordInput.matches(".*[@#\$%^&+=].*".toRegex())) {
            evaluatePassword = 4 //Al menos un caracter especial
        }else if (!passwordInput.matches("\\S+\$".toRegex())) {
            evaluatePassword = 5 //No espacios en blanco
        } else if (!passwordInput.matches(".{8,25}".toRegex())) {
            evaluatePassword = 6 //Mino 6 maximo 25 caracteres
        }
        return evaluatePassword
    }

    fun isValidCurp(curp: String): Boolean {
        val curpRegex = "([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)\$".toRegex()
        return curp.matches(curpRegex)
    }

    //Retorna verdadero si el numero de teléfono cumple con las reglas
    fun isPhoneNumberValid(phone: String): Boolean {
        val phoneRegex = "^\\d{10}$"
        return phoneRegex.toRegex().matches(phone)
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(email)
    }


    //Eejecuta un efecto de sonido recibido como parámetro
    fun soundEffect(context: Context, soundFile: Int) {
        var mediaPlayer = MediaPlayer.create(context, soundFile)
        mediaPlayer.setVolume(0.2f, 0.2f)
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

    fun isNetworkAvailable(ct: Context): Boolean {
        val connectivityManager =
            ct.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

    fun isOnlineNet(): Boolean {
        try {
            val p = Runtime.getRuntime().exec("ping -c 1 www.google.es")

            val `val` = p.waitFor()
            val reachable = (`val` == 0)
            return reachable
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return false
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

    fun mergeDateTime(date: String, time: String): String {
        if(date.isBlank() || time.isBlank())
            return ""
        else {
            val clearDate  = date.replace("-", "")
            val clearTime = time.replace(":", "")
            return "$clearDate $clearTime"
        }
   }


    //Recibe un string on fecha
    fun getDateFromStr(d:String):String {
        return d.substring(0, 10)
    }

    //Recibe un string on fecha
    fun getTimeFromStr(d:String):String {
        return d.substring(11, 19)
    }

    // convierte una Imagen bitmap a Base64
    fun BitmaptoBase64(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}