package com.aur3liux.mipolicia.view.dialogs


import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aur3liux.mipolicia.components.ReporteCard

@SuppressLint("SuspiciousIndentation")
@Composable
fun MenuReporteDialog(
    reporteTx: MutableState<String>,
    index: MutableState<Int>,
    onConfirmation: () -> Unit) {

    //Respuestas que se muestran en el desplegable
    var reporteOpcionesArray: List<String> = listOf(
        "Fallas de semáforos",
        "Vialidad en mal estado",
        "Alcantarilla sin tapa",
        "Alcantarilla obstruida ",
        "Falla de alumbrado público",
        "Afectación de servicios básicos",
        "Contaminación del suelo, aire y agua",
        "Animal muerto",
        "Maltrato de animales",
        "Vehículo abandonado",
        "Solicitud de rondín",
        "Abuso de autoridad",
        "Venta de droga",
        "Venta de alcohol de manera clandestina",
        "Solicitar una red vecinal"
    )

    Dialog(onDismissRequest = {
        onConfirmation()
    }) {
        // text and buttons
        Column(
            modifier = Modifier
                .padding(top = 30.dp) // this is the empty space at the top
                // .verticalScroll(state = rememberScrollState())
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(percent = 10)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(0.8f)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    text = "Tipo de reporte",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary)

                Icon(
                    modifier = Modifier
                        .weight(0.2f)
                        .size(30.dp)
                        .clickable { onConfirmation() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary)
            } //Row

            LazyColumn(){
                itemsIndexed(reporteOpcionesArray){pos, reporte ->
                    ReporteCard(
                        reporte = reporte,
                        modifier = Modifier
                            .padding(1.dp),
                        onClick = {
                            onConfirmation()
                            when (pos) {
                                0 -> {
                                    reporteTx.value = "Fallas de semáforo"
                                    index.value = 1
                                }

                                1 -> {
                                    reporteTx.value = "Vialidad en mal estado"
                                    index.value = 2
                                }

                                2 -> {
                                    reporteTx.value = "Alcantarilla sin tapa"
                                    index.value = 3
                                }

                                3 -> {
                                    reporteTx.value = "Alcantarilla obstruida"
                                    index.value = 4
                                }

                                4 -> {
                                    reporteTx.value = "Falla de alumbrado público"
                                    index.value = 5
                                }

                                5 -> {
                                    reporteTx.value = "Afectación de servicios básicos (agua, drenajes, combustible, etc.)"
                                    index.value = 6
                                }

                                6 -> {
                                    reporteTx.value = "Contaminación del suelo, aire y agua"
                                    index.value = 7
                                }

                                7 -> {
                                    reporteTx.value = "Animal muerto"
                                    index.value = 8
                                }

                                8 -> {
                                    reporteTx.value = "Maltrato de animales"
                                    index.value = 9
                                }

                                9 -> {
                                    reporteTx.value = "Vehículo abandonado"
                                    index.value = 10
                                }

                                10 -> {
                                    reporteTx.value = "Solicitud de rondín"
                                    index.value = 11
                                }

                                11 -> {
                                    reporteTx.value = "Abuso de autoridad"
                                    index.value = 12
                                }

                                12 ->{
                                    reporteTx.value = "Venta de droga"
                                    index.value = 13
                                }

                                13 -> {
                                    reporteTx.value = "Venta de alcohol de manera clandestina"
                                   index.value = 14
                                }

                                14 -> {
                                    reporteTx.value =  "Solicitar una red vecinal"
                                    index.value = 15
                                }
                            }
                        })
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

