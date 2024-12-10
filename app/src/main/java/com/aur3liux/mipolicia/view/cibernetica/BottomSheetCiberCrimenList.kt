package com.aur3liux.mipolicia.view.bottomsheets

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
fun BottomSheetDelitosSheet(
    navC: NavController,
    onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val context = LocalContext.current

    //CATEGORIA DELITOS
    val delitosList = mutableListOf(
        DelitoInfo(1, "Abuso de confianza"),
        DelitoInfo(2,"Abuso sexual"),
        DelitoInfo(3,"Acoso sexual"),
        DelitoInfo(4,"Allanamiento de morada"),
        DelitoInfo(5,"Alteración y daños al ambiente"),
        DelitoInfo(6,"Amenazas"),
        DelitoInfo(7,"Calumnias"),
        DelitoInfo(8,"Daño en propiedad ajena"),
        DelitoInfo(9,"Venta ilícita de bebidas alcohólicas"),
        DelitoInfo(10,"Delitos de odio"),
        DelitoInfo(11,"Delitos en contra de animales"),
        DelitoInfo(12,"Delitos en contra de obligación alimentaria"),
        DelitoInfo(13,"Despojo"),
        DelitoInfo(14,"Extorsión"),
        DelitoInfo(15,"Fraude"),
        DelitoInfo(16,"Hostigamiento sexual"),
        DelitoInfo(17,"Lesiones"),
        DelitoInfo(18,"Robo"),
        DelitoInfo(19,"Violación"), //18
        DelitoInfo(20,"Violencia familiar")
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
                text = "Como primer paso seleccione de la lista el delito del que usted considera ha sido víctima.",
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Justify,
                lineHeight = 20.0.sp,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
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
                                        onDismiss()
                                        //ACTUALIZAMOS LOS DATOS DE LA PREDENUNCIA HASTA EL PRIMER PASO
                                        db.predenunciaTmpDao().updatePredenunciaTmp(
                                                PredenunciaTmpData(
                                                    0,
                                                    data.id,
                                                    data.delito,
                                                    "",
                                                    0,
                                                    "",
                                                    loc.latitud,
                                                    loc.longitud
                                                )
                                            )
                                        when (data.id) {
                                            8, 17, 18, 19 -> {
                                                navC.navigate(
                                                    Router.SUBCATEGORIA_DELITOS.createRoute(
                                                        data.id
                                                    )
                                                )
                                            }
                                            else -> {
                                                navC.navigate(Router.PREDENUNCIA.route)
                                            }
                                        }
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

                                Icon(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .weight(0.1f)
                                        .padding(end = 10.dp),
                                    imageVector = Icons.Filled.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
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
