package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MarcoLegalView(navC: NavController) {

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reglamento de vialidad",
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(modifier =  Modifier
                .weight(0.2f),
                contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Guía rápida de la ley de vialidad",
                        fontSize = 14.sp,
                        letterSpacing = 0.3.sp,
                        textAlign = TextAlign.Justify,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable { navC.navigate(Router.REGLAMENTO_TRANSITO.route) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Gavel,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = ""
                        )
                        Text(
                            text = "Reglamento y ley de vialidad",
                            fontSize = 10.sp,
                            fontFamily = ToolBox.gmxFontRegular,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }//Row lectura completa  de vialidad
                } //Column
            } //Box

            Box(modifier =  Modifier
                .weight(0.8f)){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    //PRIMERA FILA
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.DirectionsWalk,
                                "Peatones"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("1"))
                        }

                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.CreditCard,
                                "Licencia de conducir"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("2"))
                        }
                    }//ROW PRIMERA FILA

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    //SEGUNDA FILA
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.CardMembership,
                                "Permiso para conducir"
                            ),
                                shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("3"))
                        }

                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.EventSeat,
                                "Cinturón de seguridad"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("4"))
                        }
                    }//SEGUNDA FILA

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    //TERCERA FILA
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.CarRepair,
                                "Estacionarse"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("5"))
                        }

                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.PhoneAndroid,
                                "Uso del celular"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("6"))
                        }
                    }//TERCERA FILA

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    //CUARTA FILA
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.TrackChanges,
                                "Regular la velocidad"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("7"))
                        }
                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.SportsMotorsports,
                                "Motociclistas"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("8"))
                        }
                    }//CUARTA FILA

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    //QUINTA FILA
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.LightMode,
                                "Manejo de luces"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("9"))
                        }
                        MenuCard(
                            menuOpc = MenuImg(
                                Icons.Filled.InvertColors,
                                "Polarizado"
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp),
                            colorBack = MaterialTheme.colorScheme.onSurface,
                            fSize = 14.sp,
                            w = 60.dp,
                            h = 60.dp,
                            colorTx = MaterialTheme.colorScheme.primary
                        ) {
                            navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("10"))
                        }
                    }//QUINTA FILA
                } //Contenedor de los botones
            } //Box menu

            Spacer(modifier = Modifier.height(60.dp))
        } //Column
    }//Scaffold


}