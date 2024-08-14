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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.naats.R
import com.aur3liux.naats.Router
import com.aur3liux.naats.components.RoundedButton
import com.aur3liux.naats.Store
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.ui.theme.dGradient1
import com.aur3liux.naats.ui.theme.dGradient2
import com.aur3liux.naats.ui.theme.lGradient1
import com.aur3liux.naats.ui.theme.lGradient2
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CreaDenunciaView(
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
    val context = LocalContext.current

    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()
    val locationDb = db.locationDao()


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
            val singapore = LatLng(locationDb.getLocationData().latitud, locationDb.getLocationData().longitud)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(singapore, 14f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState) {
                Marker(
                    state = MarkerState(position = singapore),
                    title = "Tu ubicación actual",
                    snippet = "Puedes presionar una ubicación en el mapa para marcar donde ocurrió el evento"
                )
            }
        } //Column
    }//Box
}