package com.aur3liux.encuestaprofe.view.components.radiobutton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


@Composable
fun LabelledRadioButton(
    modifier: Modifier = Modifier,
    label: String,
    sizeF: TextUnit,
    sizeFS: TextUnit,
    selected: Boolean,
    sColor: Color,
    uColor: Color,
    w: Dp = 250.dp,
    vPadding : Dp = 3.dp,
    onClick: (() -> Unit)?) {

    var shadow = remember{ mutableStateOf(0.dp) }
    Card(
        modifier = Modifier
            .padding(horizontal = vPadding)
            .height(50.dp)
            .width(w).clickable {
                onClick?.let { it()

                }
            },
        //elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 8.dp else 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                Color(0xFFF44336)
            } else {
                Color(0xFFBBB7B7)
            },
            contentColor = if (selected)
                MaterialTheme.colorScheme.scrim
            else
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(
            topStartPercent = 10,
            topEndPercent = 10,
            bottomStartPercent = 10,
            bottomEndPercent = 10
        )
    ) {
        Box(
            Modifier
                .height(50.dp)
                .width(w),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label,
                color = if (selected) sColor else uColor,
                fontSize = if (selected) sizeFS else sizeF)
        }//Box
    }//Card
}