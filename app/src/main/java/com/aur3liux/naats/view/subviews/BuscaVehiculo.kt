package com.aur3liux.naats.view.subviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aur3liux.naats.components.TextFieldData

@Composable
fun BuscaVehiculo(
    placa: MutableState<String>,
    serie: MutableState<String>,
    color: Color
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = "Datos del vehículo",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary)

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = color),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //PLACA
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = placa,
                        textLabel = "Placa",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 80,
                        trailingIcon = { Icon(Icons.Filled.Tag, contentDescription = "") },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.None,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            onDone = {
                                keyboardController!!.hide()
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    //SERIE
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = serie,
                        textLabel = "Serie",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 80,
                        trailingIcon = {
                            Icon(
                                Icons.Filled.AirportShuttle,
                                contentDescription = ""
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.None,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            onDone = {
                                keyboardController!!.hide()
                            }
                        ),
                        imeAction = ImeAction.Next
                    )
                }//Column
            } //card
        }
    }//Box
}