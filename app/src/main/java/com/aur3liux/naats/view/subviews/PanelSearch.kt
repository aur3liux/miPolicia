package com.aur3liux.naats.view.subviews

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.naats.R
import com.aur3liux.naats.Router
import com.aur3liux.naats.components.RoundedButton
import com.aur3liux.naats.Store
import com.aur3liux.naats.ui.theme.dGradient1
import com.aur3liux.naats.ui.theme.dGradient2
import com.aur3liux.naats.ui.theme.lGradient1
import com.aur3liux.naats.ui.theme.lGradient2

@Composable
fun PanelSearch(
    navC: NavController,
    nombre: MutableState<String>,
    paterno: MutableState<String>,
    materno: MutableState<String>,
    licencia: MutableState<String>,
    rfc: MutableState<String>,
    curp: MutableState<String>,
    alias: MutableState<String>,
    telefono: MutableState<String>,
    placa: MutableState<String>,
    serie: MutableState<String>,
    delito: MutableState<String>,
    direccion: MutableState<String>,
    ubicacion: MutableState<String>,
    fecha_inicio: MutableState<String>,
    fecha_fin: MutableState<String>
) {
    var darkTheme = isSystemInDarkTheme()
    val color1 = if (darkTheme) lGradient1 else dGradient1
    val color2 = if (darkTheme) lGradient2 else dGradient2

    val personaExacta = remember { mutableStateOf(false) }
    val vehiculoExacto = remember { mutableStateOf(false) }
    val eventoExacto = remember { mutableStateOf(false) }

    //Controladores para mostrar  u ocultar paneles de datos
    val showPanelPersonas = remember { mutableStateOf(false) }
    val showPanelVehiculos = remember { mutableStateOf(false) }
    val showPanelIPH = remember { mutableStateOf(false) }

    val preparingSearch = remember { mutableStateOf(false) }
    val onSearching = remember { mutableStateOf(mutableStateOf(false)) }

    Box(
        modifier = Modifier
            //.navigationBarsPadding()
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color1, // Start color
                        color2 // End color
                    )  // End color
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column( modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                painter = painterResource(id = R.drawable.logo_h),
                contentDescription = "background",
                contentScale = ContentScale.FillWidth
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    text = "Criterios de búsqueda",
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                //SECCION DE BUSQUEDA DE PERSONAS
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            showPanelPersonas.value = !showPanelPersonas.value
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = 10.dp),
                        imageVector = Icons.Filled.People,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .weight(5f),
                        text = "Pantalla temporal 1",
                        color = MaterialTheme.colorScheme.primary
                    )

                    Icon(
                        modifier = Modifier
                            .weight(3f)
                            .padding(end = 10.dp),
                        imageVector = if(showPanelPersonas.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                }
                if(showPanelPersonas.value){
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Nombre: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "${nombre.value} ${paterno.value} ${materno.value}",
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Alias: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = alias.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Teléfono: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = telefono.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Licencia: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = licencia.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "RFC: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = rfc.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "CURP: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = curp.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Búsqueda exacta",
                            color = MaterialTheme.colorScheme.primary
                        )

                        Switch(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            checked = personaExacta.value,
                            onCheckedChange = { personaExacta.value = it }
                        )
                    } //Row
                } //if Muestra u oculta panel con los datos de la persona

                Divider(modifier = Modifier.height(4.dp), color = color1)

                //SECCION DE BUSQUEDA DE VEHICULOS
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            showPanelVehiculos.value = !showPanelVehiculos.value
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = 10.dp),
                        imageVector = Icons.Filled.DirectionsCar,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .weight(5f),
                        text = "Pantalla temporal 2",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        modifier = Modifier
                            .weight(3f)
                            .padding(end = 10.dp),
                        imageVector = if(showPanelVehiculos.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                }
                if(showPanelVehiculos.value) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Placa: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = placa.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Serie: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = serie.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Búsqueda exacta",
                            color = MaterialTheme.colorScheme.primary
                        )

                        Switch(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            checked = vehiculoExacto.value,
                            onCheckedChange = { vehiculoExacto.value = it }
                        )
                    } //Row
                } //if Muestra u oculta panel con los datos del vehiculo

                Divider(modifier = Modifier.height(4.dp),  color = color1)

                //SECCION DE BUSQUEDA DE POR EVENTO
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            showPanelIPH.value = !showPanelIPH.value
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = 10.dp),
                        imageVector = Icons.Filled.DateRange,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .weight(5f),
                        text = "Pantalla temporal 3",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        modifier = Modifier
                            .weight(3f)
                            .padding(end = 10.dp),
                        imageVector = if(showPanelIPH.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                }
                if(showPanelIPH.value) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier

                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Delito: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = if(delito.value == Store.APP.txDelito) "" else delito.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Direccion: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = direccion.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Ubicacion: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = ubicacion.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Fecha de inicio: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = fecha_inicio.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Fecha fin: ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = fecha_fin.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } //Row

                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Búsqueda exacta",
                            color = MaterialTheme.colorScheme.primary
                        )

                        Switch(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            checked = eventoExacto.value,
                            onCheckedChange = { eventoExacto.value = it }
                        )
                    } //Row
                } //if Muestra u oculta panel con los datos IPH y RND

                Divider()

                RoundedButton(
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 30.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    text = "Ejecutar operación",
                    fSize = 20.sp,
                    textColor = Color.White,
                    backColor = MaterialTheme.colorScheme.inverseSurface,
                    estatus = onSearching.value,
                    onClick = {
                        navC.navigate(Router.SHOW_RESULT.route)
                        //preparingSearch.value = true
                    } //onClick
                )
                Spacer(modifier = Modifier.height(50.dp))

            }//Column
        } //Column
    }//Box
}