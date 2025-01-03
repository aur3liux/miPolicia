package com.aur3liux.mipolicia.components

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialogExample(
    txTime: MutableState<String>,
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) }
    ) {
        TimePicker(
            state = timePickerState
        )
        txTime.value = timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
        Log.i("TIME", timePickerState.hour.toString() + ":" + timePickerState.minute.toString())
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary,
                ),
                onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = {
                    onConfirm()
                }) {
                Text("OK")
            }
        },
        text = {
            content()
        }
    )
}