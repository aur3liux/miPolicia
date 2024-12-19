package com.aur3liux.mipolicia.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialogButton(
    cornerRadiusPercent: Int = 26,
    buttonText: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(percent = cornerRadiusPercent)
            )
            .clickable {
                onDismiss()
            }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            color = MaterialTheme.colorScheme.background,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}