package com.aur3liux.naats.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PoliticaPrivacidadTurned(hasRead: MutableState<Boolean>) {
    if(!hasRead.value) {
        Row(modifier = Modifier
            .height(35.dp)
            .clickable {
                hasRead.value = !hasRead.value
            }
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Checkbox(
                modifier = Modifier.padding(horizontal = 0.dp),
                checked = hasRead.value,
                onCheckedChange = {
                    hasRead.value = !hasRead.value
                },
                colors = CheckboxDefaults.colors()
            )
            Text(
                text = "Acepto la politica de privacidad",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal
            )
        }
    }else {
        Text("Política de privacidad leida y aceptada",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)
    }
}