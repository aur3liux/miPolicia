package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.Art
import com.aur3liux.mipolicia.localdatabase.Vial
import com.aur3liux.mipolicia.view.PdfViewer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetallesMarcoLegal(
    navC: NavController,
    seccion: String) {
    val topic = remember {
        mutableStateOf("")
    }
    val openDocument = remember{ mutableStateOf(false) }
    var lstAux = Art("", buildAnnotatedString { append(" ")}
        )
    var currentList = remember { mutableListOf(lstAux) }
    val seccionContenido = seccion.toInt()
    when (seccionContenido) {
        1 -> {
            topic.value = "Peatones"
            currentList = Vial.peatones.contentArt.toMutableList()}
        2 -> {
            topic.value = "Licencia para conducir"
            currentList = Vial.licencias.contentArt.toMutableList()}
        3 -> {
            topic.value = "Permisos para conducir"
            currentList = Vial.permisos.contentArt.toMutableList()}
        4 -> {
            topic.value = "Uso de cinturón de seguridad"
            currentList = Vial.cinturon.contentArt.toMutableList()}
        5 -> {
            topic.value = "Estacionarse"
            currentList = Vial.estacionar.contentArt.toMutableList()}
        6 -> {
            topic.value = "Uso de móviles"
            currentList = Vial.moviles.contentArt.toMutableList()}
        7 -> {
            topic.value = "Exceso de velocidad"
            currentList = Vial.velocidad.contentArt.toMutableList()}
        8 -> {
            topic.value = "Uso de casos motociclistas"
            currentList = Vial.cascos.contentArt.toMutableList()}
        9 -> {
            topic.value = "Manejo de luces"
            currentList = Vial.luces.contentArt.toMutableList()}
        10 -> {
            topic.value = "Señales de tránsito"
            currentList = Vial.senales.contentArt.toMutableList()}
    }

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
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {navC.navigate(Router.AVISO_PRIVACIDAD.route) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                        ){
                        Icon(
                            imageVector = Icons.Filled.Gavel,
                            tint = MaterialTheme.colorScheme.inverseSurface,
                            contentDescription = "")
                        Text(
                            text = "Ley de vialidad",
                            fontSize = 10.sp,
                            fontFamily = ToolBox.gmxFontRegular,
                            color = MaterialTheme.colorScheme.inverseSurface,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )

                    }//card
                })
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = topic.value,
                fontSize = 21.sp,
                letterSpacing = 0.3.sp,
                fontFamily = ToolBox.gmxFontRegular,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp),
                contentPadding = PaddingValues(3.dp),
                verticalArrangement = Arrangement.Center) {
                itemsIndexed(currentList) { posicion, info ->

                    Text(
                        info.articulo,
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 10.dp)
                            .fillMaxWidth(),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        info.descripcion,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )

                    Divider(color = Color.Black, modifier = Modifier
                        .fillMaxWidth())
                }
            }//LazyColumn

            Spacer(modifier = Modifier.height(60.dp))

        } //Column
    }//Scaffold


}