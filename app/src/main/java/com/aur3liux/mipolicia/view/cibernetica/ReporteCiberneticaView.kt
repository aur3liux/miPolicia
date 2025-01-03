package com.aur3liux.mipolicia.view.cibernetica

/****
 * ESTA PANTALLA ES LA QUE PERMITE EL USUARIO CONFIGURA UN REPORTE
 * ANTE UNA SITUACION EN DONDE FUE VICTIMA DE ALGUN DELITO CIBERNETICO
 ****/

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import coil.compose.AsyncImage
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetError
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.components.InfoDialog
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.lGradient1
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReporteCiberneticaView(navC: NavController) {
    val ciberDelito = rememberSaveable { mutableStateOf(Store.APP.txCiberDelito) }
    val indexCiberDelito = rememberSaveable { mutableStateOf(0) }
    val descripcion = rememberSaveable { mutableStateOf("") }
    val showCiberDelitos = remember { mutableStateOf(false) }
    val confirmClose = rememberSaveable { mutableStateOf(false) }
    val enabledInput = remember { mutableStateOf(true) }
    val showDialogHelp = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val currentPredTmp = db.predenunciaTmpDao().getPredenunciaData()
    val currentUser = db.userDao().getUserData()

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    var imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri.value = uri
        }
    AsyncImage(
        model = imageUri,
        contentDescription = null,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight().width(100.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
    )


    val onPrepareSend = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }
    val onCancel = remember{ mutableStateOf(false) }

    val msgAyuda = buildAnnotatedString {
        append("La dirección es aproximada, si no corresponde a la ubicación donde sucedió el evento puedes indicarla en el mapa o si necesitas agregar mas información, cuando ratifiques tu denuncia podrás hacerlo con ayuda de asesores de la FGECAM.")
    }

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(title = { Text(text = "Cuéntanos tu caso", fontSize = 15.sp, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lGradient1),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { confirmClose.value = true }
                            .size(20.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = Color.White)
                },
                actions = {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { /*showDialogHelp.value = true */ },
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


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                //CIBERATAQUE
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showCiberDelitos.value = true }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(horizontal = 10.dp),
                        text = ciberDelito.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Icon(
                        modifier = Modifier
                            .weight(0.2f)
                            .size(40.dp),
                        imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                        contentDescription = "")
                } //Row CIBERATAQUE

                Spacer(modifier = Modifier.height(10.dp))

                //DESCRIPCION
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    text = "Relata brevemente los hechos ${descripcion.value.length}/400",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 30.dp),
                    shape = RoundedCornerShape(5.dp),
                    value = descripcion.value,
                    maxLines = 10,
                    singleLine = false,
                    onValueChange = {
                        if (it.length <= 400)
                            descripcion.value = it
                        else {
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedTextColor = MaterialTheme.colorScheme.inverseSurface, //color de texto
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        focusedPlaceholderColor = Color.Gray
                    )
                )

                //FOTO
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    text = "Puedes agregar hasta 3 imágenes de evidencia:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showCiberDelitos.value = true }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {

                    //Imagen 1
                    MenuCard(
                        menuOpc = MenuImg(Icons.Filled.Web, "Imagen 1*"),
                        modifier = Modifier
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.inverseSurface,
                        colorTx = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(20.dp),
                        fSize = 12.sp,
                        w = 100.dp,
                        h = 80.dp
                    ) {
                        ToolBox.soundEffect(context, R.raw.tap)
                    }

                    //Imagen 2
                    MenuCard(
                        menuOpc = MenuImg(Icons.Filled.Web, "Imagen 2*"),
                        modifier = Modifier
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.inverseSurface,
                        colorTx = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(20.dp),
                        fSize = 12.sp,
                        w = 100.dp,
                        h = 80.dp
                    ) {
                        ToolBox.soundEffect(context, R.raw.tap)
                        launcher.launch("image/*")
                    }

                    //Imagen 3
                    MenuCard(
                        menuOpc = MenuImg(Icons.Filled.Web, "Imagen 3*"),
                        modifier = Modifier
                            .padding(8.dp),
                        colorBack = MaterialTheme.colorScheme.inverseSurface,
                        colorTx = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(20.dp),
                        fSize = 12.sp,
                        w = 100.dp,
                        h = 80.dp
                    ) {
                        ToolBox.soundEffect(context, R.raw.tap)
                    }
                } //Row


                //BOTON PARA ENVIAR
                RoundedButton(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .fillMaxWidth(0.8f)
                        .height(40.dp),
                    text = "Enviar reporte",
                    fSize = 15.sp,
                    shape =   RoundedCornerShape(15.dp),
                    textColor = Color.White,
                    backColor = botonColor,
                    estatus = onProccesing,
                    onClick = {
                        onPrepareSend.value = true
                    } //onClick
                )
            }//Column
            Spacer(modifier = Modifier.height(20.dp))
        }//Column
    }//Column

    if(onPrepareSend.value) {
        if(ToolBox.testConectivity(context)){
            onPrepareSend.value = false
            onProccesing.value = true
            val jsonObj = JSONObject()
            jsonObj.put("username", currentUser.email)
            jsonObj.put("token_access", currentUser.tokenAccess)
            jsonObj.put("crime_id", currentPredTmp.indexDelito)
            jsonObj.put("description", descripcion.value)
            jsonObj.put("subcrime_id", currentPredTmp.indexSubdelito)
            jsonObj.put("latitude", currentPredTmp.latitud)
            jsonObj.put("longitude", currentPredTmp.longitud)
            jsonObj.put("township", "Campeche")
            jsonObj.put("place", "San Francisco de Campeche")
            jsonObj.put("complaint_type", 1)
            //predViewModel.DoSendPredenuncia(context, jsonObj)
            enabledInput.value = false
        }else {
            onPrepareSend.value = false
            showSheetError.value = true
            messageError.value = "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
        }
    }


    if(confirmClose.value){
        ConfirmDialog(
            title = "Confirmación",
            info = "Confirme que desa salir.",
            titleAceptar = "Si",
            titleCancelar = "No",
            onAceptar = {
                confirmClose.value = false
                navC.navigate(Router.HOME.route)
            },
            onCancelar = {
                confirmClose.value = false
            })
    }


    if(showCiberDelitos.value){
        BottomSheetCiberCrimenList(ciberDelito = ciberDelito, indexCiberDelito = indexCiberDelito) {
            showCiberDelitos.value = false
        }
    }

    if (showSheetError.value) {
        BottomSheetError(
            text = messageError.value,
            title = "Error") {
            showSheetError.value = false
            onProccesing.value = false
        }
    }

    if(showDialogHelp.value){
        InfoDialog(
            title = "Ayuda",
            info = msgAyuda,
            context = context,
            onConfirmation = {
                showDialogHelp.value = false
            })
    }

}