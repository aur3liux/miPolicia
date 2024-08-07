package com.aur3liux.naats.view.subviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Streetview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aur3liux.naats.components.CustomDatePicker
import com.aur3liux.naats.components.TextFieldData
import com.aur3liux.naats.ui.theme.dGradient1
import com.aur3liux.naats.ui.theme.dGradient2
import com.aur3liux.naats.ui.theme.lGradient1
import com.aur3liux.naats.ui.theme.lGradient2

@Composable
fun BuscaEvento(
    delito: MutableState<String>,
    direccion: MutableState<String>,
    ubicacion: MutableState<String>,
    fecha_inicio: MutableState<String>,
    fecha_fin: MutableState<String>,
    color: Color
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val openDelitos = rememberSaveable{ mutableStateOf(false) }
    val indexDelito = rememberSaveable{ mutableStateOf(0) }

    var darkTheme = isSystemInDarkTheme()
    val color1 = if(darkTheme) lGradient1 else dGradient1
    val color2 = if(darkTheme) lGradient2 else dGradient2

    //MUNICIPIOS
    var listaDelitos = mutableListOf<String>(
        "Robo",
        "Extorsión",
        "Lesiones",
        "Escándalo",
        "Falta administrativa",
        "Distribución de drogas",
        "Portación de armas",
        "Homicidio",
        "Abuso de autoridad",
        "Violación sexual"
    )

    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color1, // Start color
                        color2
                    )  // End color
                )
            ),
        contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = "Datos para buscar en IPH y RND",
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

                    //DELITO
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(50.dp)
                            .clickable {
                                openDelitos.value = true
                            },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clickable {
                                    openDelitos.value = true
                                },
                            text = delito.value,
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "")
                    }

                    //DIRECCION
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = direccion,
                        textLabel = "Direccion",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 80,
                        trailingIcon = { Icon(Icons.Filled.Streetview, contentDescription = "") },
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

                    //UBICACION
                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textFieldValue = ubicacion,
                        textLabel = "Ubicación",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 80,
                        trailingIcon = { Icon(Icons.Filled.LocationCity, contentDescription = "") },
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
                    Spacer(Modifier.height(15.dp))
                    CustomDatePicker("Fecha de inicio", date = fecha_inicio)
                    CustomDatePicker("Fecha final", date = fecha_fin)

                }//Column

                if (openDelitos.value) {
                    DropDownOptions(
                        dialogOpen = openDelitos,
                        title = "Selecciona una respuesta",
                        position = indexDelito,
                        items = listaDelitos,
                        respuestaSelect = delito,
                        onDismis = {
                            openDelitos.value = false
                        })
                } //Abre el cuadro de dialogo para municipios
            } //card
        }
    }
}