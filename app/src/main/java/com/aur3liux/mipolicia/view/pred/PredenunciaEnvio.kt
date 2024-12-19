package com.aur3liux.mipolicia.view.pred

/****
 * ESTA PANTALLA ES LA QUE PERMITE AL USUARIO DECIDIR SI ENVÍA
 * LA PREDENUNCIA, TIENE LA OPCION PARA CANCELAR EL ENVIO
 * ADEMAS DE QUE PUEDE AMPLIAR LA INFORMACION SI ASI LO DESEA DEL
 * EVENTO QUE QUIERE REPORTAR
 ****/

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetError
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.components.InfoDialog
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.MyPredenunciaData
import com.aur3liux.mipolicia.model.RequestPredenuncia
import com.aur3liux.mipolicia.services.PredenunciaRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.btnPredColorButton
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.ui.theme.lGradient1
import com.aur3liux.mipolicia.viewmodel.PredenunciaVM
import com.aur3liux.mipolicia.viewmodel.PredenunciaVMFactory
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.io.IOException
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PredenunciaEnvio(navC: NavController) {
    val descripcion = rememberSaveable { mutableStateOf("") }
    val addressEvent = rememberSaveable { mutableStateOf("") }

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

    val locationDb = db.locationDao()
    val currentPredTmp = db.predenunciaTmpDao().getPredenunciaData()
    val currentUser = db.userDao().getUserData()

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    val geocoder = Geocoder(context, Locale.getDefault())

    //viewmodel
    val predViewModel: PredenunciaVM = viewModel(
        factory = PredenunciaVMFactory(predenunciaRepository = PredenunciaRepo())
    )

    val predState = remember(predViewModel) {predViewModel.PredenunciaMetaData}.observeAsState() //LiveData
    val onPrepareSend = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }
    val onCancel = remember{ mutableStateOf(false) }

    val msgAyuda = buildAnnotatedString {
        append("La dirección es aproximada, si no corresponde a la ubicación donde sucedió el evento puedes indicarla en el mapa o si necesitas agregar mas información, cuando ratifiques tu denuncia podrás hacerlo con ayuda de asesores de la FGECAM.")
    }

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(title = { Text(text = "Predenuncia", fontSize = 15.sp, color = Color.White) },
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
        }) {

        val visible = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            delay(300L)
            try {
               val addresses = geocoder.getFromLocation(locationDb.getLocationData().latitud, locationDb.getLocationData().longitud, 1)
               if (addresses?.isNotEmpty() == true) {
                   val address = addresses[0]
                   addressEvent.value = "${address.getAddressLine(0)}, ${address.locality}"
                   visible.value = true
               }
           }catch (e: IOException) {
               addressEvent.value = "No pudimos recuperar la direccion de tu ubicación, en la ratificación de su declaración podrá proporcionarla."
                visible.value = true
           }

        } //LaunchEffect


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 20.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = "Predenuncia lista para envío",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(10.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {

                    Spacer(modifier = Modifier.height(20.dp))

                    if(visible.value){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp, 20.dp, 5.dp),
                            text = "Direccion aproximada detectada",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }else{
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.Transparent),
                            color = cronosColor,
                            strokeWidth = 4.dp
                        )
                    }//else


                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        text = addressEvent.value,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    HorizontalDivider()

                    //Delito
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            text = "Delito:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = " ${currentPredTmp.delito} ${currentPredTmp.subDelito.lowercase()}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(10.dp))

                    //DESCRIPCION
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
                        label = {
                            Text(
                                text = "Relate brevemente los hechos ocurridos",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic
                            )
                        },
                       // placeholder = { Text(text = "Breve descripcion de los hechos") },
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

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {

                        //BOTON CANCELAR LA PREDENUNCIA
                        RoundedButton(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                                .weight(0.5f)
                                .height(40.dp),
                            text = "Cancelar",
                            fSize = 15.sp,
                            shape =   RoundedCornerShape(15.dp),
                            textColor = Color.White,
                            backColor = btnPredColorButton,
                            estatus = onCancel,
                            onClick = {
                                confirmClose.value = true
                            } //onClick
                        )

                        //BOTON PARA ENVIAR
                        RoundedButton(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                                .weight(0.5f)
                                .height(40.dp),
                            text = "Enviar",
                            fSize = 15.sp,
                            shape =   RoundedCornerShape(15.dp),
                            textColor = Color.White,
                            backColor = botonColor,
                            estatus = onProccesing,
                            onClick = {
                                onPrepareSend.value = true
                            } //onClick
                        )
                    }
                }//Column dentro del Card
                Spacer(modifier = Modifier.height(20.dp))
            }//Column
        }//Card
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
            predViewModel.DoSendPredenuncia(context, jsonObj)
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
            info = "Está a punto de salir del proceso, si desea salir y cancelar la predenuncia presione Salir.",
            titleAceptar = "Salir",
            titleCancelar = "Permanecer",
            onAceptar = {
                confirmClose.value = false
                navC.navigate(Router.HOME.route)
            },
            onCancelar = {
                confirmClose.value = false
            })
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

    if(onProccesing.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        predState.value?.let {
            when(predState.value){
                is RequestPredenuncia.Succes -> {
                    Log.i("NAATS","SUCCES" )
                    val mData = predState.value as RequestPredenuncia.Succes
                    onProccesing.value = false
                    db.myPredenunciaDao().insertPredenuncia(MyPredenunciaData(
                        folio = mData.metadata.folio,
                        estatus = mData.metadata.estatus,
                        delito = currentPredTmp.delito,
                        subDelito = currentPredTmp.subDelito,
                        modulo = mData.metadata.modulo,
                        ciudad = "Campeche",
                        descripcion = descripcion.value,
                        fecha = ToolBox.getLocalDate(),
                        hora = ToolBox.getCurrentTime(),
                        fechaModif = ToolBox.getLocalDate(),
                        horaModif = ToolBox.getCurrentTime() )
                    )
                   // db.predenunciaTmp().clearPredenunciasTmp()
                    navC.navigate(Router.FINISH_PREDENUNCIA.finishPredenuncia(folio = mData.metadata.folio)) {
                        popUpTo(navC.graph.id) { inclusive = true }
                    }
                } //Succes
                is RequestPredenuncia.Error -> {
                    Log.i("NAATS","ERROR" )
                    onProccesing.value = false
                    val errorLogin = predState.value as RequestPredenuncia.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onProccesing.value = false
                    predViewModel.resetPredenuncia()
                    enabledInput.value = true
                }//Error
                else -> {
                    onProccesing.value = false
                    enabledInput.value = true
                }
            }//when
        }//observable let
    }//onProccesing
}