package com.aur3liux.mipolicia.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class MenuImg(
    val imageRes: ImageVector,
    val texto: String,
)

@Composable
fun MenuCard(menuOpc: MenuImg,
             modifier: Modifier,
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
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorBack,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .width(w)
                .height(h),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = menuOpc.imageRes,
                contentDescription = "",
                tint = colorTx
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