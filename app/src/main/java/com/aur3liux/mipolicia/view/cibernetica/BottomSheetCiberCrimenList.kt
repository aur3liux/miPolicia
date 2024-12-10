package com.aur3liux.mipolicia.view.cibernetica

/****
 * ESTA PANTALLA ES EL PRIMER PUNTO AL INCIIAR EL PROCESO DE
 * ENVÍO DE UNA PREDENUNCIA, SE ENCARGA DE MOSTRAR UNA LISTA
 * CON LOS DELITOS QUE EL USUARIO PUEDE DENUNCIAR
 ****/

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.PredenunciaTmpData

data class DelitoInfo(val id:Int, val delito: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCiberCrimenList(
    ciberDelito: MutableState<String>,
    indexCiberDelito: MutableState<Int>,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val context = LocalContext.current

    //CATEGORIA DELITOS
    val delitosList = mutableListOf(
        DelitoInfo(1, "Ciberbullyng"),
        DelitoInfo(2,"Cibergrooming"),
        DelitoInfo(3,"Robo de identidad"),
        DelitoInfo(4,"Sexting"),
        DelitoInfo(5,"Pornografía infantil"),
        DelitoInfo(6,"Engaño telefónico"),
        DelitoInfo(7,"Ciberacoso"),
        DelitoInfo(8,"Me robaron mis cuentas de redes sociales"),
        DelitoInfo(9,"Venta en línea de estupefacientes"),
        DelitoInfo(10,"Ventas fraudulentas en linea"),
        DelitoInfo(11,"Sextorción"),
        DelitoInfo(12,"Ciberterrorismo")
    )

    val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val loc = db.locationDao().getLocationData()


    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.9f),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {

        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onDismiss() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.inverseSurface)

                Text(
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(start = 10.dp),
                    text = "Cancelar",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.inverseSurface,
                   fontWeight = FontWeight.Bold
                )
            } //Row

            HorizontalDivider()

            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = "Si no sabe a cuál categoría pertenece su problema puede consultar la sección de ayuda y corregir las veces que necesite antes de enviar el reporte",
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Justify,
                lineHeight = 20.0.sp,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.inverseSurface
            )

            Surface(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth(0.95f),
                shape = RoundedCornerShape(20.dp)) {

                    LazyColumn(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(top = 10.dp)
                            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        itemsIndexed(delitosList) { pos, data ->
                            Row(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        ciberDelito.value = data.delito
                                        indexCiberDelito.value = data.id
                                        onDismiss()

                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .weight(0.9f)
                                        .padding(start = 20.dp),
                                    fontSize = 15.sp,
                                    text = data.delito,
                                    fontFamily = ToolBox.quatroSlabFont,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 20.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            } //Row
                            HorizontalDivider(modifier = Modifier.height(1.dp), color = Color.Black)
                        }//itemIndexed
                    }//LazyColumn
                }//surface
            Spacer(modifier = Modifier.height(30.dp))
        }//Column
            Spacer(modifier = Modifier.height(70.dp))
    } //ModalSheetBottom
}//BottomSheet
