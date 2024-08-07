package com.aur3liux.naats.view.subviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun OptionsPersona(
    dialogOpen: MutableState<Boolean>,
    licencia: MutableState<Boolean>,
    rfc: MutableState<Boolean>,
    curp: MutableState<Boolean>,
    alias: MutableState<Boolean>,
    telefono: MutableState<Boolean>,
    onDismis: ()-> Unit) {

    if(dialogOpen.value) {
        Dialog(
            onDismissRequest = {
                dialogOpen.value = false
            }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Agregar campo de búsqueda",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 19.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    //AGREGAR CAMPO LICENCIA
                    Row(modifier = Modifier
                        .clickable {
                            licencia.value = !licencia.value
                        }
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = licencia.value,
                            onCheckedChange = {
                                licencia.value = !licencia.value
                            },
                            colors = CheckboxDefaults.colors()
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text("Licencia",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold)
                    }//Row LICENCIA

                    //Agregar CAMPO RFC
                    Row(modifier = Modifier
                        .clickable {
                            rfc.value = !rfc.value
                        }
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rfc.value,
                            onCheckedChange = {
                                rfc.value = !rfc.value
                            },
                            colors = CheckboxDefaults.colors()
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text("RFC",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold)
                    }//Row RFC

                    //Agregar CAMPO CURP
                    Row(modifier = Modifier
                        .clickable {
                            curp.value = !curp.value
                        }
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = curp.value,
                            onCheckedChange = {
                                curp.value = !curp.value
                            },
                            colors = CheckboxDefaults.colors()
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text("CURP",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold)
                    }//Row CURP

                    //Agregar CAMPO ALIAS
                    Row(modifier = Modifier
                        .clickable {
                            alias.value = !alias.value
                        }
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = alias.value,
                            onCheckedChange = {
                                alias.value = !alias.value
                            },
                            colors = CheckboxDefaults.colors()
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text("Alias",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold)
                    }//Row ALIAS

                    //Agregar CAMPO TELEFONO
                    Row(modifier = Modifier
                        .clickable {
                            telefono.value = !telefono.value
                        }
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = telefono.value,
                            onCheckedChange = {
                                telefono.value = !telefono.value
                            },
                            colors = CheckboxDefaults.colors()
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text("Teléfono",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold)
                    }//Row ALIAS

                    OutlinedButton(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        onClick = {
                            onDismis()
                        },
                        content = {
                            Text(text = "Cerrar",
                                textAlign = TextAlign.Start,
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold)
                        })
                }//Column
            } //Card
        }//Dialog
    }
}