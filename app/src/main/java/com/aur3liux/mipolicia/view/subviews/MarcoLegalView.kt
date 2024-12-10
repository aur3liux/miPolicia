package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.filled.Signpost
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.Vial

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MarcoLegalView(navC: NavController) {
    val openDocument = remember{ mutableStateOf(false) }


    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Marco legal",
                        fontSize = 15.sp,
                        letterSpacing = 0.3.sp,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navC.popBackStack() }
                            .size(30.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = Color.White)
                })
        }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Consulta en la ley de vialidad los artículos que regulas los siguientes temas:",
                fontSize = 12.sp,
                letterSpacing = 0.3.sp,
                textAlign = TextAlign.Justify,
                fontFamily = ToolBox.gmxFontRegular,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )


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
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("1"))
                }

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.CreditCard,
                        "Licencia de conducir"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("2"))
                }
            }//ROW PRIMERA FILA

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
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("3"))
                }

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.EventSeat,
                        "Cinturón de seguridad"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("4"))
                }
            }//SEGUNDA FILA

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
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("5"))
                }

                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.PhoneAndroid,
                        "Uso del celular"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("6"))
                }
            }//TERCERA FILA

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
                        "Exceso de velocidad"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("7"))
                }
                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.SportsMotorsports,
                        "Uso del cascos"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("8"))
                }
            }//CUARTA FILA

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
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("9"))
                }
                MenuCard(
                    menuOpc = MenuImg(
                        Icons.Filled.Signpost,
                        "Señales de vialidad"
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp),
                    colorBack = MaterialTheme.colorScheme.background,
                    fSize = 14.sp,
                    w = 150.dp,
                    h = 80.dp,
                    colorTx = MaterialTheme.colorScheme.primary
                ) {
                    navC.navigate(Router.DETALLES_MARCOLEGAL.detallesMarcoLegal("10"))
                }
            }//QUINTA FILA

            Spacer(modifier = Modifier.height(60.dp))
        } //Column
    }//Scaffold


}