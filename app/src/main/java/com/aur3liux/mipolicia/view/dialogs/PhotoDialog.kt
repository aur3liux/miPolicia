package com.aur3liux.mipolicia.view.dialogs


import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.aur3liux.mipolicia.components.DialogButton
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
fun PhotoDialog(
    nombre : String,
    photoUrl : String,
    onConfirmation: () -> Unit,
    spaceBetweenElements: Dp = 18.dp) {

        Dialog(onDismissRequest = {
            onConfirmation()
        }) {
            Surface(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent // dialog background
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth()
                        .background(
                            color = shapePrincipalColor,
                            shape = RoundedCornerShape(percent = 10)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(height = spaceBetweenElements))
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        textAlign = TextAlign.Center,
                        color = textShapePrincipalColor
                    )

                    AsyncImage(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                        model = photoUrl,
                        contentDescription = ""
                    )


                    Spacer(modifier = Modifier.height(40.dp))
                    DialogButton(
                        buttonText = "Aceptar") {
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
