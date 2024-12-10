package com.aur3liux.mipolicia.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.mipolicia.Router
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PoliticaPrivacidadTurned(hasRead: MutableState<Boolean>, navC: NavController) {

    if(!hasRead.value) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
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
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal
            )

            Text(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable { navC.navigate(Router.AVISO_PRIVACIDAD.route) },
                text = "Leer",
                fontSize = 19.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.tertiary)

        }
    }else {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
            Text(
                "Política de privacidad leida y aceptada",
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }//row
    }
}