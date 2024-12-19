package com.aur3liux.mipolicia.view.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.components.RoundedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAcercaDe(
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.9f),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onDismiss() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.inverseSurface)

                Text(
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(start = 10.dp),
                    text = "Cerrar",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontWeight = FontWeight.Bold
                )
            } //Row

            HorizontalDivider()
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Gobierno del Estado De Campeche",
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
                textAlign = TextAlign.Center,
                text = "OK",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold)

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 15.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Aceptar",
                fSize = 20.sp,
                shape =   RoundedCornerShape(15.dp),
                backColor = botonColor,
                textColor = Color.White,
                onClick = {
                    onDismiss()
                }
            )

            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}