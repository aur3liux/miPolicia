package com.aur3liux.mipolicia.view.dialogs


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapDialog(
    latitud : Double,
    longitud : Double,
    onConfirmation: () -> Unit,
    spaceBetweenElements: Dp = 18.dp) {

    val selectLocation = remember { mutableStateOf(LatLng(latitud, longitud)) }

        Dialog(onDismissRequest = {
            onConfirmation()
        }) {
            Surface(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent // dialog background
            ) {

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(latitud,longitud), 17f)
                }
                val propertiesMap = remember {
                    mutableStateOf(
                        MapProperties(
                            isMyLocationEnabled = true,
                            mapType = MapType.NORMAL)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .height(700.dp)
                        .fillMaxWidth(0.6f)
                        .background(
                            color = shapePrincipalColor,
                            shape = RoundedCornerShape(percent = 10)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = "Marque en el mapa el lugar donde sucedió el evento",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        textAlign = TextAlign.Center,
                        color = textShapePrincipalColor
                    )

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.65f),
                        contentDescription = "Seleccione",
                        onMapClick = { location ->
                            selectLocation.value = LatLng(location.latitude, location.longitude)
                        },
                        properties = propertiesMap.value,
                        cameraPositionState = cameraPositionState) {
                        Marker(
                            state = MarkerState(position = selectLocation.value),
                            title = "Mi policía",
                            snippet = "¿Aqui sucedió su reporte?"
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    DialogButtonMap(
                        buttonText = "Aplicar") {
                        onConfirmation()
                    }

                    Spacer(modifier = Modifier.height(height = spaceBetweenElements * 2))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .background(color = Color.White, shape = CircleShape)
                            .border(
                                width = 2.dp,
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surface
                            )
                            .clickable { onConfirmation() }
                            .padding(all = 16.dp)
                    )
                }
            }
        }
}


@Composable
fun DialogButtonMap(
    cornerRadiusPercent: Int = 26,
    buttonText: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .background(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(percent = cornerRadiusPercent)
            )
            .clickable {
                onDismiss()
            }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}