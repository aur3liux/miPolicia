package com.aur3liux.mipolicia.view

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.TokenPushData
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import java.text.BreakIterator
import java.text.StringCharacterIterator

@Composable
fun LoadView(navC: NavController) {
    val scale = remember { Animatable(0f) }
    val sesionState = remember { mutableStateOf(0) }
    val context = LocalContext.current

    val text = "Mi policía"
    val breakIterator = remember{BreakIterator.getCharacterInstance()}
    val mainText = remember { mutableStateOf("") }

    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val dbAuth = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()
    val sesionData = dbAuth.sesionDao()


    LaunchedEffect(key1 = true) {
       val checkSessionState = sesionData.getSessionStatus()
        if (checkSessionState == null || checkSessionState == 0){
            sesionState.value = -1
        }else sesionState.value = checkSessionState

        if(dbAuth.tokenPushDao().getTokenPushNotif() == null)
            getNotificationToken(dbAuth)

        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(12f).getInterpolation(it)
                })
        )

        delay(300L)

        //*** Texto animado
        breakIterator.text = StringCharacterIterator(text)
        var nextIndex = breakIterator.next()

        while (nextIndex != BreakIterator.DONE){
            mainText.value = text.substring(0, nextIndex).toString()
            nextIndex = breakIterator.next()
            delay(65L)
        }
        //***

       if(sesionState.value < 0){ //Debe iniciar sesion
            navC.navigate(Router.LOGIN.route) {
                popUpTo(navC.graph.id) { inclusive = true }
            }
        }
        if(sesionState.value == 1){ //DEBE DESCARGAR EXPEDIENTE
            navC.navigate(Router.HOME.route) {
                popUpTo(navC.graph.id) { inclusive = true }
            }
        }
    } //LaunchEffect

    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()) {

        //Contenido del splash Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_policia_campeche),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .scale(scale.value),
                contentScale = ContentScale.Crop
            )
            Text(text = mainText.value,
                fontSize = 24.sp,
                fontFamily = ToolBox.quatroSlabFont,
                fontWeight = FontWeight.Bold,
                color = botonColor)
        }//Column
    }//Surface
}

//PARA LAS NOTIFICACIONES PUSH
fun getNotificationToken(db: AppDb){
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.i("MIPOLICIA", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result

        // Log and toast
        val msg ="Token del dispositivo ${token}"
        Log.i(Store.APP.name, msg)
        db.tokenPushDao().insertToken(TokenPushData(0, token))

    })
}