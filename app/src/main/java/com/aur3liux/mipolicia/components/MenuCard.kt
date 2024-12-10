package com.aur3liux.naats.components

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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
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
             onClick: () -> Unit) {
    val onTapCard = remember { mutableStateOf(false) }

    val scale = remember { Animatable(0f) }
    var isVisible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        delay(300L)
        isVisible.value = true
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter =
        slideInVertically(
            initialOffsetY = { -30 }
        ) + expandVertically(expandFrom = Alignment.Top) +
                scaleIn(transformOrigin = TransformOrigin(0.5f, 0f)) +
                fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
    ) {
        Card(
            modifier = modifier
                .clickable {
                    onClick()
                    onTapCard.value = true
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorBack,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .width(90.dp)
                    .height(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = menuOpc.imageRes,
                    contentDescription = "",
                    tint = colorTx
                )
                Text(
                    text = menuOpc.texto,
                    fontSize = 12.sp,
                    color = colorTx
                )
            } // Column

            if (onTapCard.value) {
                LaunchedEffect(key1 = true) {
                    delay(100)
                    onTapCard.value = false
                }
            }
        }// Card
    }//AnimateVisivility
}