package com.aur3liux.mipolicia.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aur3liux.mipolicia.ui.theme.titleShapePrincipalColor


@Composable
fun RoundedButtonH(modifier: Modifier = Modifier,
                   text: String,
                   w: Dp = 250.dp,
                   h: Dp = 80.dp,
                   icon: ImageVector,
                   fSize: TextUnit = 14.sp,
                   backColor: Color = Color.Transparent,
                   textColor: Color = MaterialTheme.colorScheme.primary,
                   colorTint: Color = MaterialTheme.colorScheme.primary,
                   displayProgressBar: Boolean = false,
                   onClick: () -> Unit){
    if (!displayProgressBar){
        OutlinedButton(
            modifier = modifier
                .width(w)
                .height(h),
            colors = ButtonDefaults.buttonColors(containerColor = backColor),
            onClick = {
                if(!displayProgressBar)
                    onClick() },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 5.dp),
                    imageVector = icon,
                    contentDescription = "",
                    tint = colorTint
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(h)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    text = text,
                    color = textColor,
                    fontSize = fSize,
                    lineHeight = 13.sp,
                    textAlign = TextAlign.Center
                )
            } // Row

        }//Button
    }
    else {
        Box(
            modifier = modifier
                .width(w)
                .height(h), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Color.Blue,
                strokeWidth = 3.dp
            )
        }
    }
}
