package com.aur3liux.naats.view

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.naats.R
import com.aur3liux.naats.Router
import com.aur3liux.naats.Store
import com.aur3liux.naats.localdatabase.AppDb
import kotlinx.coroutines.delay

@Composable
fun LoadView(navC: NavController) {
    val scale = remember { Animatable(0f) }
    val sesionState = remember { mutableStateOf(false) }
    val context = LocalContext.current
    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val dbAuth = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()
    val sesionData = dbAuth.sesionDao()

    LaunchedEffect(key1 = true) {
        sesionState.value = sesionData.getSessionStatus()
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(7f).getInterpolation(it)
                })
        )

        delay(500L)
        if(!sesionState.value){
            navC.navigate(Router.LOGIN.route) {
                popUpTo(navC.graph.id) { inclusive = true }
            }
        }else{
            navC.navigate(Router.HOME.route) {
                popUpTo(navC.graph.id) { inclusive = true }
            }
        }
    } //LaunchEffect

    //Contenido del splash Screen
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .scale(scale.value)
                .padding(top = 20.dp),
            text = "FGECAM",
            fontSize = 21.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
    }
}