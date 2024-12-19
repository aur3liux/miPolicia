package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.ReporteCard
import com.aur3liux.mipolicia.components.ReporteImg
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.dialogs.DescripcionDialog
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReporteCiudadano(navC: NavController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val user = db.userDao().getUserData()

    val locationDb = db.locationDao()
    val selectLocation = remember { mutableStateOf(LatLng(locationDb.getLocationData().latitud, locationDb.getLocationData().longitud)) }
    val showMenuReportes = rememberSaveable { mutableStateOf(false) }
    val showDescripcionDialog = rememberSaveable { mutableStateOf(false) }

    val propertiesMap = remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL,
                // mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, if(darkTheme) R.raw.dark_maps else R.raw.light_map)
                // mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, if(darkTheme) R.raw.dark_maps else R.raw.light_map)
            )
        )
    }

    val settingsMap = remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectLocation.value, 14f)
    }

    val onTapCard = remember{ mutableStateOf(false) }
    val txReporte = remember{ mutableStateOf(Store.APP.txReportes) }
    val txDescripcion = rememberSaveable{ mutableStateOf("") }

    //Respuestas que se muestran en el desplegable
    var reporteOpcionesArray: List<ReporteImg> = listOf(
        ReporteImg(R.drawable.ic_semaforo, "Semáforos", true),
        ReporteImg(R.drawable.ic_calle, "Vialidad en mal estado", false),
        ReporteImg(R.drawable.ic_alcantarilla_abierta, "Alcantarilla sin tapa", false),
        ReporteImg(R.drawable.ic_alcantarilla_tapada, "Alcantarilla obstruida ", false),
        ReporteImg(R.drawable.ic_alumbrado, "Alumbrado", false),
        ReporteImg(R.drawable.ic_suministros, "Suministros básicos", false),
        ReporteImg(R.drawable.ic_contaminacion, "Contaminación", false),
        ReporteImg(R.drawable.ic_rondines, "Animal muerto", false)
    )

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reporte ciudadano",
                        fontSize = 15.sp,
                        letterSpacing = 0.3.sp,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = textShapePrincipalColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = shapePrincipalColor),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navC.popBackStack() }
                            .size(30.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = textShapePrincipalColor)
                })
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier
                    .clickable { showMenuReportes.value = !showMenuReportes.value }
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 5.dp),
                        text = txReporte.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        lineHeight = 17.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = if(showMenuReportes.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            Spacer(modifier = Modifier.height(20.dp))

            if(showMenuReportes.value){
                LazyRow(){
                    itemsIndexed(reporteOpcionesArray){pos, reporte ->
                        ReporteCard(
                            reporte = reporte,
                            modifier = Modifier
                                .padding(3.dp),
                            onClick = {
                                showMenuReportes.value = false
                                onTapCard.value = true
                                when (pos) {
                                    0 -> {
                                        txReporte.value = "Fallas de semáforo"
                                    }

                                    1 -> {
                                        txReporte.value = "Vialidad en mal estado"
                                    }

                                    2 -> {
                                        txReporte.value = "Alcantarilla sin tapa"
                                    }

                                    3 -> {
                                        txReporte.value = "Alcantarilla obstruida"
                                    }

                                    4 -> {
                                        txReporte.value = "Falla de alumbrado público"
                                    }

                                    5 -> {
                                        txReporte.value = "Afectación de servicios básicos (agua, drenajes, combustible, etc.)"
                                    }

                                    6 -> {
                                        txReporte.value = "Contaminación del suelo, aire y agua"
                                    }

                                    7 -> {
                                        txReporte.value = "Animal muerto"
                                    }
                                }
                                //}
                            })
                    }
                }
            }//show menu horizontal

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .clickable { showDescripcionDialog.value = !showDescripcionDialog.value }
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp),
                    text = "Breve descripción del problema",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    lineHeight = 17.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 5.dp),
                text = "Marque en el mapa el lugar del problema, si no lo indica tomaremos su ubicación actual.",
                fontWeight = FontWeight.Bold,
                lineHeight = 15.sp,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            GoogleMap(
                modifier = Modifier
                    .weight(0.5f)
                    .border(1.dp, Color.Black),
                contentDescription = "",
                properties = propertiesMap.value,
                uiSettings = settingsMap.value,
                cameraPositionState = cameraPositionState
            ) {} //Maps

            Box(modifier = Modifier
                .offset(x = -130.dp, y = 170.dp),
                contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_gob),
                    contentDescription = "",
                    tint = Color.Gray
                )
            }
        } //Column
        if(showDescripcionDialog.value){
            DescripcionDialog(descripcion = txDescripcion,
                context = context,
                onConfirmation = { showDescripcionDialog.value = false })
        }
    }//Scaffold

}