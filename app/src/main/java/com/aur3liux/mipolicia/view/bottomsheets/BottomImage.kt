package com.aur3liux.mipolicia.view.bottomsheets


/* VENTANA TIPO BOTTOM QUE MUESTRA UNA IMAGEN */

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.ui.theme.botonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetImage(
    imageUri: Uri?,
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

            AsyncImage(
                 model = imageUri,
                 contentDescription = null,
                 modifier = Modifier
                     .padding(4.dp)
                     .fillMaxSize(),
                 contentScale = ContentScale.Crop,
             )

            HorizontalDivider()
            Spacer(modifier = Modifier.height(30.dp))

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 15.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Cerrar",
                fSize = 20.sp,
                shape = RoundedCornerShape(15.dp),
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