package com.aur3liux.mipolicia.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MenuCardH(menuOpc: MenuImg,
             modifier: Modifier,
             colorTint: Color,
             colorTx:Color,
             colorBack: Color,
             shape: Shape,
             fSize: TextUnit = 20.sp,
             w: Dp,
             h: Dp,
             onClick: () -> Unit) {
    val onTapCard = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clickable {
                onClick()
                onTapCard.value = true
            },
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorBack,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .width(w)
                .height(h),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = menuOpc.imageRes,
                contentDescription = "",
                tint = colorTint
            )
            Text(
                text = menuOpc.texto,
                fontSize = fSize,
                color = colorTx,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        } // Column

        if (onTapCard.value) {
            LaunchedEffect(key1 = true) {
                delay(100)
                onTapCard.value = false
            }
        }
    }// Card

}