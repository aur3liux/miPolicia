package com.aur3liux.mipolicia.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class ReporteImg(
    @DrawableRes val imageResId: Int,
    val nameResId: String,
    val estatus: Boolean
)

@Composable
fun ReporteCard(
    reporte: ReporteImg,
    modifier: Modifier, onClick: () -> Unit) {
    val onTapCard = remember { mutableStateOf(false) }
    val colorCard = remember { mutableStateOf(Color(0xFFBBB7B7)) }
    Card(modifier = modifier.clickable {
            onClick()
            onTapCard.value = true
        },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            //containerColor = if(reporte.estatus) colorCard.value else Color.Red,
            containerColor = colorCard.value,
        )) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .width(160.dp)
                .height(90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = reporte.imageResId),
                contentDescription = "",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = reporte.nameResId,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        } // Column

        if(onTapCard.value) {
            LaunchedEffect(key1 = true) {
                colorCard.value = Color(0xFF240404)
                delay(100)
                colorCard.value = Color(0xFFBBB7B7)
                onTapCard.value = false
            }
        }
    }// Card

}