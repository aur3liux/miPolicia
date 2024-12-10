package com.aur3liux.mipolicia.view.pred

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetError
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.ui.theme.lGradient1
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aur3liux.mipolicia.components.InfoDialog
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.ConsultaFolioRepo
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.viewmodel.ConsultaFolioVM
import com.aur3liux.mipolicia.viewmodel.ConsultaFolioVMFactory
import kotlinx.coroutines.withContext
import org.json.JSONObject

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesPredenucia(navC: NavController, folio: String) {
    val context = LocalContext.current
    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val showDialogHelp = remember { mutableStateOf(false) }
    val predenuncia = db.myPredenunciaDao().getPredenunciaData(folio)

    val onProccesing = remember{ mutableStateOf(true) }
    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    //
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val msgAyuda = buildAnnotatedString {
        append("Te mostramos información resumida de tu caso. \nSi necesita más información puede acudir a la FGECAM y ampliar su información a través del ")
        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
            append("código QR.")
        }
    }

    val jsonObj = JSONObject()
    jsonObj.put("username", db.userDao().getUserData().email)
    jsonObj.put("token_access", db.userDao().getUserData().tokenAccess)
    jsonObj.put("folio", folio)

    //viewmodel
    val consultaFolioViewModel: ConsultaFolioVM = viewModel(
        factory = ConsultaFolioVMFactory(folioRepository = ConsultaFolioRepo())
    )

    val folioState = remember(consultaFolioViewModel) {consultaFolioViewModel.FolioData}.observeAsState() //LiveData


    DisposableEffect(Unit) {
        onDispose {
            bitmap.value = null // Limpiamos el bitmap existente
        }
    }

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(title = {
                Text(
                    modifier = Modifier.clickable { navC.popBackStack() },
                    text = "Detalle del folio",
                    fontSize = 15.sp,
                    fontFamily = ToolBox.montseFont,
                    fontWeight = FontWeight.Normal,
                    color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lGradient1),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navC.popBackStack() }
                            .size(20.dp),
                        imageVector = Icons.Filled.Close,
                        contentDescription = "", tint = Color.White)
                },
                actions = {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { showDialogHelp.value = true},
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
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            //bitmap.value = null // Limpiamos el bitmap existente
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    val generatedBitmap = generateQRCode(context, folio)
                    bitmap.value = generatedBitmap
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            if(onProccesing.value){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Transparent),
                    color = cronosColor,
                    strokeWidth = 4.dp
                )
            }else{
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    //FOLIO
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Folio",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.folio}",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    } //FOLIO

                    HorizontalDivider()

                    //ESTATUS
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Estatus:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.estatus}",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    } //FOLIO

                    HorizontalDivider()

                    //DELITO
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Delito:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.delito}",
                            lineHeight = 15.sp,
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row DELITO

                    HorizontalDivider()

                    //SUBDELITO
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Subdelito:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.subDelito}",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row subdelito

                    HorizontalDivider()

                    //FECHA Y HORA
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Fecha y hora:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.fecha}  ${predenuncia.hora} hrs.",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 15.sp
                        )
                    } //Row DESCRIPCION

                    HorizontalDivider()

                    //FECHA Y HORA
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Fecha y hora:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.fecha}  ${predenuncia.hora} hrs.",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 15.sp
                        )
                    } //Row DESCRIPCION

                    //ULTIMA ACTUALZACION
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Actualizacion:",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${predenuncia.fechaModif}  ${predenuncia.horaModif} hrs.",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 15.sp
                        )
                    } //Row DESCRIPCION

                    Spacer(modifier = Modifier.height(16.dp))

                    //Muestra el codigo QR
                    if (bitmap.value != null) {
                        Box(modifier = Modifier
                            .fillMaxWidth(), contentAlignment = Alignment.Center){
                            Card(
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(200.dp),
                                    bitmap = bitmap.value!!.asImageBitmap(),
                                    contentDescription = "Folio",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }//Card
            }
        } //Column


        if (showSheetError.value) {
            BottomSheetError(
                text = messageError.value,
                title = "Error de descarga") {
                showSheetError.value = false
                onProccesing.value = false
            }
        }

    }//Scaffold

    if(showDialogHelp.value){
        InfoDialog(
            title = "Ayuda",
            info = msgAyuda,
            context = context,
            onConfirmation = {
                showDialogHelp.value = false
            })
    }

    if(onProccesing.value) {
        consultaFolioViewModel.DoConsultaFolio(context, jsonObj)
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        folioState.value?.let {
            when(folioState.value){
                is RequestResponse.Succes -> {
                    Log.i("CRONOS","SUCCES" )
                    onProccesing.value = false
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("CRONOS","ERROR" )
                    onProccesing.value = false
                    val errorLogin = folioState.value as RequestResponse.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onProccesing.value = false
                    consultaFolioViewModel.resetConsultaFolio()
                }//Error
                else -> {
                    onProccesing.value = false
                }
            }//when
        }//observable let

    }//onProccesing

} //Composable

suspend fun generateQRCode(
    context: android.content.Context,
    text: String
): Bitmap? {
    return withContext(Dispatchers.Default) {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                    )
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}


