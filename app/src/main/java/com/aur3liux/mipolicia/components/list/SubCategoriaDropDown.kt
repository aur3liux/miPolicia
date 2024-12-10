package com.aur3liux.naats.components.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SubCategoriaDropDown(
    dialogOpen: MutableState<Boolean>,
    title: String,
    position: MutableState<Int>,
    items: MutableList<String>,
    respuestaSelect: MutableState<String>,
    onDismis: ()-> Unit) {

        var lista: MutableList<String> = ArrayList()
        var listaSecciones: MutableList<String> = ArrayList()
        var context = LocalContext.current
        listaSecciones = items.toMutableList()

        val busqueda = remember { mutableStateOf("") }

        if(dialogOpen.value) {
            Dialog(
                onDismissRequest = {
                    Log.i("CANCELACION", "Respuesta no elegida")
                    dialogOpen.value = false
                }) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF9F2241))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 19.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        contentPadding = PaddingValues(3.dp),
                        verticalArrangement = Arrangement.Center) {
                        itemsIndexed(listaSecciones) { posicion, dato ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .clickable {
                                        position.value = posicion
                                        respuestaSelect.value = dato.toString()
                                        onDismis()
                                    }
                                    .height(70.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart) {
                                Text(
                                    dato,
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .fillMaxWidth(),
                                    fontSize = 17.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                            }//Box
                            Divider(color = Color.Black, modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp))
                        }
                    }//LazyColumn
                }//Column
            }//Dialog
        }
}