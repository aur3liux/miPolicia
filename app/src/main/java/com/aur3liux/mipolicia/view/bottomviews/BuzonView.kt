package com.aur3liux.mipolicia.view.bottomviews

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.NavigateBefore
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetInfo
import com.aur3liux.mipolicia.components.InfoDialog
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.AvisosData
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.DownloadAvisosRepo
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.viewmodel.AvisosVM
import com.aur3liux.mipolicia.viewmodel.DownloadAvisosVMFactory
import org.json.JSONObject
import com.aur3liux.mipolicia.R


data class notificationData(val id:  Int,  val content: String)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuzonView(navC: NavController) {
    val context = LocalContext.current
    var listAvisos = mutableListOf<AvisosData>()
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    listAvisos = db.avisosDao().getAvisosList().toMutableList()

    val showDialogHelp = remember { mutableStateOf(false) }
    val msgQueEsEsto = buildAnnotatedString {
        append("Estos avisos le llegan a través de notificaciones a su teléfono, pero si no los pudo atender en su momento aqui los puede recuperar y darles la atención correspondiente.")
    }

    val showSheetInfo = remember { mutableStateOf(false) }
    val messageInfo = remember { mutableStateOf("") }
    val titleInfo = remember { mutableStateOf("") }

    var isDonwloading = remember { mutableStateOf(true) }

    val jsonObj = JSONObject()
    jsonObj.put("username", db.userDao().getUserData().email)
    jsonObj.put("token_access", db.userDao().getUserData().tokenAccess)
    jsonObj.put("ref", 0)

    //VIEWMODEL
    val buzonVieModel: AvisosVM = viewModel(
        factory = DownloadAvisosVMFactory(downloadRepository = DownloadAvisosRepo())
    )
    val buzonState = remember(buzonVieModel) {
        buzonVieModel.Avisos
    }.observeAsState()

    //ERROR
    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

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
                        .clickable {
                            showDialogHelp.value = true
                        },
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
                text = "Buzón de avisos",
                fontSize = 17.sp,
                fontFamily = ToolBox.quatroSlabFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            if(isDonwloading.value){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Transparent),
                    color = cronosColor,
                    strokeWidth = 4.dp
                )
            }else {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (listAvisos != null) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 10.dp),
                            imageVector = Icons.Filled.Email, contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp),
                            text = "Asunto",
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
                        itemsIndexed(listAvisos) { pos, aviso ->
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .fillMaxSize()
                                    .height(60.dp)
                                    .clickable {
                                        ToolBox.soundEffect(context, R.raw.tap)
                                        titleInfo.value = "Folio ${aviso.folio}"
                                        messageInfo.value = aviso.descripcion
                                        showSheetInfo.value = true
                                    }
                            ) {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 10.dp),
                                        text = ToolBox.getDateFromStr(aviso.fecha),
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 15.sp,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Text(
                                        modifier = Modifier
                                            .padding(end = 20.dp),
                                        text = ToolBox.getTimeFromStr(aviso.fecha),
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 15.sp,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(0.3f)
                                            .padding(start = 10.dp),
                                        text = "Asunto: ",
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 15.sp,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        modifier = Modifier
                                            .weight(0.6f),
                                        text = aviso.folio.toString(),
                                        fontFamily = ToolBox.quatroSlabFont,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 15.sp,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .size(25.dp)
                                            .weight(0.2f),
                                        imageVector = Icons.Filled.KeyboardArrowDown,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
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
                        text = "No hay registros que mostrar",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }//Card
        }
        }//Column
    }//Scaffold
    if(showDialogHelp.value){
        InfoDialog(
            title = "Avisos",
            info = msgQueEsEsto,
            context = context,
            onConfirmation = {
                showDialogHelp.value = false
            })
    }

    if(showSheetInfo.value){
        BottomSheetInfo(
            title = titleInfo.value,
            text = messageInfo.value,
            onDismiss = {
                showSheetInfo.value = false
            })
    }

    if(isDonwloading.value) {
        buzonVieModel.downloadAvisos(context, jsonObj)
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        buzonState.value?.let {
            when(buzonState.value){
                is RequestResponse.Succes -> {
                    Log.i("NAATS","SUCCES" )
                    isDonwloading.value = false
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("NAATS","ERROR" )
                    isDonwloading.value = false
                    val errorLogin = buzonState.value as RequestResponse.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    buzonVieModel.resetDownloadAvisos()
                }//Error
                else -> {
                    isDonwloading.value = false
                }
            }//when
        }//observable let
    }//onProccesing

}