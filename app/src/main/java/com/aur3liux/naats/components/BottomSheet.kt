package com.aur3liux.naats.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aur3liux.naats.ui.theme.botonColor
import com.aur3liux.naats.ui.theme.cronosShape
import com.aur3liux.naats.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    title: String,
    text: String,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = Color(0xFFBCA986),
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(cronosShape),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(70.dp)
                        .clickable { onDismiss() },
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "")

                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
                textAlign = TextAlign.Center,
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold)

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 15.dp)
                    .fillMaxWidth()
                    .height(70.dp),
                text = "Aceptar",
                fSize = 20.sp,
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