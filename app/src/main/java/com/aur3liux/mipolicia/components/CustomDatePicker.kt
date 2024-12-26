package com.aur3liux.mipolicia.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
      /*  colors =  DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = Color.Blue,
            weekdayContentColor = Color.Blue,
            subheadContentColor = Color.Blue,
            navigationContentColor = Color.Blue,
            yearContentColor = Color.Blue,

            disabledSelectedYearContentColor = Color.Blue,
            disabledSelectedDayContentColor = Color.Blue,
            currentYearContentColor = Color.Blue,
            selectedYearContentColor = Color.Blue,
            dayContentColor = Color.Blue,
            todayContentColor = Color.Blue,
            selectedDayContentColor = Color.Blue,
            dayInSelectionRangeContentColor = Color.Blue
            ),*/
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