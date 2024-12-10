package com.aur3liux.naats.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CustomDatePicker(
    title: String,
    date: MutableState<String>
) {
    //val date = remember { mutableStateOf(LocalDate.now())}
    val isOpen = remember { mutableStateOf(false)}
    Row(verticalAlignment = Alignment.CenterVertically) {

        OutlinedTextField(
            readOnly = true,
            value = date.value.format(DateTimeFormatter.ISO_DATE),
            label = { Text(title) },
            onValueChange = {})

        IconButton(
            onClick = { isOpen.value = true } // show de dialog
        ) {
            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Calendar")
        }
    }

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false // cierra el calendario
                if (it != null) { // Actualiza la fecha
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate().toString()
                }
            },
            onCancel = {
                isOpen.value = false //Cierra el calendario
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = state)
    }
}