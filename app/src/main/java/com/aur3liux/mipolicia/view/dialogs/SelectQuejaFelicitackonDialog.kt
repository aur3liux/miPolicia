package com.aur3liux.mipolicia.view.dialogs

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import com.aur3liux.mipolicia.components.MenuCardH
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor


@Composable
fun SelectQuejaFelicitacionDialog(
    title: String,
    sendQueja: () -> Unit,
    sendFelicitacion: () -> Unit,
    onCancelar: () -> Unit,
    spaceBetweenElements: Dp = 15.dp) {

        Dialog(onDismissRequest = {
            onCancelar()
        }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent // dialog background
            ) {
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
                            text = "¿Que desea enviar?",
                            fontFamily = ToolBox.quatroSlabFont,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))
                         //Imagen
                            MenuCardH(
                                menuOpc = MenuImg(
                                    Icons.Filled.Close,
                                    "Una queja hacia un policía"),
                                modifier = Modifier
                                    .padding(8.dp),
                                shape = RoundedCornerShape(15.dp),
                                fSize = 12.sp,
                                h = 50.dp,
                                w = 220.dp,
                                colorTint = MaterialTheme.colorScheme.surfaceVariant,
                                colorBack = shapePrincipalColor,
                                colorTx = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                sendQueja()
                            }

                            //Camara
                            MenuCardH(
                                menuOpc = MenuImg(Icons.Filled.Check,
                                    "Quiero felicitar a un policía"),
                                modifier = Modifier
                                    .padding(8.dp),
                                shape = RoundedCornerShape(15.dp),
                                fSize = 12.sp,
                                h = 50.dp,
                                w = 220.dp,
                                colorTint = MaterialTheme.colorScheme.surfaceVariant,
                                colorBack = MaterialTheme.colorScheme.inverseSurface,
                                colorTx = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                sendFelicitacion()
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

