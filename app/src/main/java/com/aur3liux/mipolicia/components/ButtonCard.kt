package com.aur3liux.mipolicia.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ButtonCard(menuOpc: MenuImg,
             modifier: Modifier,
             colorTx:Color,
             colorBack: Color,
             colorTint: Color = colorTx,
             iconSize : Dp = 30.dp,
             shape: Shape,
             fSize: TextUnit = 20.sp,
             w: Dp,
             h: Dp,
             onClick: () -> Unit) {
    val onTapCard = remember { mutableStateOf(false) }
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
        ),
        modifier = modifier
            .clickable {
                onClick()
                onTapCard.value = true
            }
            .shadow(
                elevation = 10.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Yellow
            )
            .border(2.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))

    ) {
        Column(modifier = Modifier
            .background(colorBack)
            .clip(CircleShape)
            .width(w - 20.dp)
            .height(h - 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                modifier = Modifier.size(iconSize),
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
                    fontWeight = FontWeight.Bold
                )
            } //Column
        } // Card


    if (onTapCard.value) {
        LaunchedEffect(key1 = true) {
            delay(100)
            onTapCard.value = false
        }
    }
}