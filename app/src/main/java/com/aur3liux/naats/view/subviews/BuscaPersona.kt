package com.aur3liux.naats.view.subviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import com.aur3liux.naats.components.TextFieldData

@Composable
fun BuscaPersona(
    nombre: MutableState<String>,
    paterno: MutableState<String>,
    materno: MutableState<String>,
    licencia: MutableState<String>,
    rfc: MutableState<String>,
    curp: MutableState<String>,
    alias: MutableState<String>,
    telefono: MutableState<String>,
    personaExacta: MutableState<Boolean>,
    color: Color) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column {

            Text(
                text = "Datos de la persona",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    //NOMBRE A BUSCAR
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = nombre,
                        textLabel = "Nombre",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 60,
                        //trailingIcon = { Icon(Icons.Filled.People, contentDescription = "") },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
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

                    //APELLIDO PATERNO
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = paterno,
                        textLabel = "Paterno",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 60,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
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

                    //APELLIDO MATERNO
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = materno,
                        textLabel = "Materno",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 60,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
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

                    // LICENCIA
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = licencia,
                        textLabel = "Licencia",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 12,
                        trailingIcon = { Icon(Icons.Filled.CreditCard, contentDescription = "") },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters,
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

                    // RFC
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = rfc,
                        textLabel = "RFC",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 13,
                        trailingIcon = {
                            Icon(
                                Icons.Filled.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters,
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


                    // CURP
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = curp,
                        textLabel = "CURP",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 18,
                        trailingIcon = { Icon(Icons.Filled.AccountBox, contentDescription = "") },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters,
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


                    // ALIAS
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = alias,
                        textLabel = "Alias",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 80,
                        trailingIcon = { Icon(Icons.Filled.PersonPin, contentDescription = "") },
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences,
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


                    // TELEFONO
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = telefono,
                        textLabel = "Teléfono",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 10,
                        trailingIcon = { Icon(Icons.Filled.Phone, contentDescription = "") },
                        keyboardType = KeyboardType.Phone,
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