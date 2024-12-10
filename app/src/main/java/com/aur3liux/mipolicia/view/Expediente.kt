package com.aur3liux.naats.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.naats.Router
import com.aur3liux.naats.localdatabase.Store
import com.aur3liux.naats.ToolBox
import com.aur3liux.naats.components.InfoDialog
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.localdatabase.MyPredenunciaData


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Expediente(navC: NavController) {
    var listMyPredenuncias = mutableListOf<MyPredenunciaData>()
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME)
            .allowMainThreadQueries()
            .build()

    listMyPredenuncias = db.myPredenunciaDao().getPredenunciaList().toMutableList()

    val showDialogHelp = remember { mutableStateOf(false) }
    val msgQueEsEsto = buildAnnotatedString {
        append("Cada vez que haya una modificación en alguno de sus expedientes le llegará una notificación a su teléfono para su conocimiento. En esta sección podrá consultar los detalles de sus casos. También puede hablar con un asesor mediante whatsapp en la pestaña ")
        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
            append("FGECAM")
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color(0xFF9F2241)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9F2241)
                ),
                title = {
                    Text(
                        modifier = Modifier.clickable { navC.popBackStack() },
                        text = "Naats",
                        fontSize = 14.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                navC.popBackStack()
                            }
                            .size(40.dp),
                        tint = Color.White,
                        imageVector = Icons.Filled.NavigateBefore,
                        contentDescription = "")
                },                actions = {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { showDialogHelp.value = true },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation =  10.dp,
                        )){
                        Box(modifier = Modifier
                            .size(40.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Filled.QuestionMark,
                                contentDescription = "")
                        }//Box
                    }//card
                })
        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 20.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = "Tu listado de casos",
                fontSize = 17.sp,
                fontFamily = ToolBox.quatroSlabFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (listMyPredenuncias != null && listMyPredenuncias.size > 0) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 20.dp),
                            text = "Folio",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier
                                .weight(0.5f),
                            text = "Delito",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }//Row
                    HorizontalDivider(modifier = Modifier.height(3.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        itemsIndexed(listMyPredenuncias) { pos, predenuncia ->
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .fillMaxSize()
                                    .height(60.dp)
                                    .clickable {
                                        navC.navigate(
                                            Router.DETALLES_PREDENUNCIA.detallesPredenuncia(
                                                predenuncia.folio
                                            )
                                        )
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(0.2f)
                                            .padding(start = 20.dp),
                                        text = predenuncia.folio,
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        modifier = Modifier
                                            .weight(0.6f)
                                            .padding(start = 10.dp),
                                        text = predenuncia.delito,
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 15.sp,
                                        fontSize = 14.sp
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .size(25.dp)
                                            .weight(0.2f),
                                        imageVector = Icons.Filled.NavigateNext,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.inverseSurface
                                    )
                                }//Row
                            }//Card
                            HorizontalDivider()
                        }//itemIndexed
                    }//LazyColumn
                } else {

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 40.dp),
                        text = "No hay registros de expedientes para mostrar.",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }//Card
            Spacer(modifier = Modifier.height(40.dp))
        }//Column
    } //Scaffold

    if(showDialogHelp.value){
        InfoDialog(
            title = "Expedientes",
            info = msgQueEsEsto,
            context = context,
            onConfirmation = {
                showDialogHelp.value = false
            })
    }
}