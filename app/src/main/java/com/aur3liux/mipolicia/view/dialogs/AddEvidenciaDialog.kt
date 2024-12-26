package com.aur3liux.mipolicia.view.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.HorizontalDivider
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
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.ui.theme.botonColor


@Composable
fun AddEvienciaDialog(
    title: String,
    openGallery: () -> Unit,
    openCamera: () -> Unit,
    onCancelar: () -> Unit,
    spaceBetweenElements: Dp = 15.dp) {

        Dialog(onDismissRequest = {
            onCancelar()
        }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent // dialog background
            ) {

                    // text and buttons
                    Column(
                        modifier = Modifier
                            .padding(top = 30.dp) // this is the empty space at the top
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(percent = 10)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(height = 36.dp))

                        Text(
                            text = title,
                            fontSize = 19.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = TextAlign.Justify,
                            text = "¿Desde donde desea cargar la imagen?",
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))
                         //Imagen
                            MenuCard(
                                menuOpc = MenuImg(
                                    Icons.Filled.Photo,
                                    "Galería de fotos"),
                                modifier = Modifier
                                    .padding(8.dp),
                                shape = RoundedCornerShape(15.dp),
                                fSize = 12.sp,
                                h = 50.dp,
                                w = 50.dp,
                                colorBack = MaterialTheme.colorScheme.inverseSurface,
                                colorTx = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                openGallery()
                            }

                            //Camara
                            MenuCard(
                                menuOpc = MenuImg(Icons.Filled.AddAPhoto, "Abrir la cámara"),
                                modifier = Modifier
                                    .padding(8.dp),
                                shape = RoundedCornerShape(15.dp),
                                fSize = 12.sp,
                                h = 50.dp,
                                w = 50.dp,
                                colorBack = MaterialTheme.colorScheme.inverseSurface,
                                colorTx = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                openCamera()
                            }

                        Spacer(modifier = Modifier.height(height = 10.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(height = 10.dp))
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable { onCancelar() },
                            text = "Cancelar" ,
                            textAlign = TextAlign.Justify,
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements * 2))
                    }//column

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd) {
                    Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .background(color = Color.White, shape = CircleShape)
                                .clickable { onCancelar() }
                                .border(
                                    width = 2.dp,
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.surface
                                )
                                .padding(all = 16.dp)

                        )
                }//box
            }//surface
        } //Dialog
}

