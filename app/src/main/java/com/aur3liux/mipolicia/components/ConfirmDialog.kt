package com.aur3liux.mipolicia.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.btnPredColorButton

@Composable
fun ConfirmDialog(
    title: String,
    info: String,
    titleCancelar:String,
    titleAceptar:String,
    onAceptar: () -> Unit,
    onCancelar: () -> Unit,
    spaceBetweenElements: Dp = 18.dp) {

        Dialog(onDismissRequest = {
            onCancelar()
        }) {
            Surface(
                modifier = Modifier.fillMaxWidth(0.92f),
                color = Color.Transparent // dialog background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()) {

                    // text and buttons
                    Column(
                        modifier = Modifier
                            .padding(top = 30.dp) // this is the empty space at the top
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(percent = 10)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(height = 36.dp))

                        Text(
                            text = title,
                            fontSize = 24.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = TextAlign.Justify,
                            text = info,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            DialogConfirmButton(
                                buttonText = titleCancelar,
                                color = btnPredColorButton) {
                                onCancelar()
                            }

                            DialogConfirmButton(
                                buttonText = titleAceptar,
                                color = botonColor) {
                                onAceptar()
                            }
                        }//Row


                        Spacer(modifier = Modifier.height(height = spaceBetweenElements * 2))
                    }

                    //
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
                            .clickable {onCancelar() }
                            .padding(all = 16.dp)
                            .align(alignment = Alignment.TopCenter)
                    )
                }
            }
        }
}


@Composable
fun DialogConfirmButton(
    cornerRadiusPercent: Int = 26,
    buttonText: String,
    color: Color,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .padding(horizontal = 10.dp)
            .background(
                color = color,
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
            fontSize = 15.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}