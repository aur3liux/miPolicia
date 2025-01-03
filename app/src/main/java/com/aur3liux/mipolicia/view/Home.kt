package com.aur3liux.mipolicia.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LogOutRepo
import com.aur3liux.mipolicia.services.IntentollamadaRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.titleShapePrincipalColor
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetAcercaDe
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetMenu
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.view.dialogs.SelectQuejaFelicitacionDialog
import com.aur3liux.mipolicia.viewmodel.IntentoLlamadaVM
import com.aur3liux.mipolicia.viewmodel.IntentoLlamadaVMFactory
import com.aur3liux.mipolicia.viewmodel.LogOutVM
import com.aur3liux.mipolicia.viewmodel.LogOutVMFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navC: NavController) {
    val context = LocalContext.current

    val showMenuPrincipal = rememberSaveable { mutableStateOf(false) }
    val showAcercaDe = rememberSaveable { mutableStateOf(false) }
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val user = db.userDao().getUserData()

    val locationDb = db.locationDao()
    val selectLocation = remember { mutableStateOf(LatLng(locationDb.getLocationData().latitud, locationDb.getLocationData().longitud)) }
    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    val confirmCallZazil = rememberSaveable { mutableStateOf(false) }

    val onConfirmQuejaReporte = rememberSaveable { mutableStateOf(false) }
    val confirmCloseSession = rememberSaveable { mutableStateOf(false) }
    val onCloseSession = rememberSaveable { mutableStateOf(false) }
    val onPrepareCall = rememberSaveable { mutableStateOf(false) }
    val onRegistraLlamada = rememberSaveable { mutableStateOf(false) }


    //viewmodel CERRAR SESION
    val logoutViewModel: LogOutVM = viewModel(
        factory = LogOutVMFactory(logoutRepository = LogOutRepo())
    )

    //LiveData
    val closeSesionState = remember(logoutViewModel) {
        logoutViewModel.UserData
    }.observeAsState()

    //viewmodel REGISTRAR INTENTO LLAMADA
    val intentoLlamadaViewModel: IntentoLlamadaVM = viewModel(
        factory = IntentoLlamadaVMFactory(itentoRepository = IntentollamadaRepo())
    )

    //LiveData
    val intentoState = remember(intentoLlamadaViewModel) {
        intentoLlamadaViewModel.CallData
    }.observeAsState()

    val info = buildAnnotatedString {
        append(messageError.value)
    }

    var darkTheme = isSystemInDarkTheme()

    val propertiesMap = remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_maps_origin )
               // mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, if(darkTheme) R.raw.dark_maps else R.raw.light_map)
            )
        )
    }

    val settingsMap = remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectLocation.value, 15f)
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = shapePrincipalColor,
        floatingActionButtonPosition = FabPosition.Start) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(60.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.mipolicia_logo_app),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MI POLICIA",
                        fontSize = 24.sp,
                        letterSpacing = 0.3.sp,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = titleShapePrincipalColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Gobierno del estado de Campeche",
                        fontSize = 10.sp,
                        letterSpacing = 0.2.sp,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = titleShapePrincipalColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                } //Column encabezado titulos
            } //Row encabezado titulos

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(15.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter){
                    if(onCloseSession.value){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.Transparent),
                            color = botonColor,
                            strokeWidth = 4.dp
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .padding(bottom = 100.dp),
                            text = "Solicitando",
                            fontSize = 10.sp,
                            fontFamily = ToolBox.montseFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //if onCloseSession
                    else {
                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(1.dp, Color.Black),
                            contentDescription = "",
                            properties = propertiesMap.value,
                            uiSettings = settingsMap.value,
                            cameraPositionState = cameraPositionState
                        ) {} //Maps

                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = 0.dp, y = -60.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(modifier = Modifier
                                .offset(x = 0.dp, y = -20.dp)
                                .fillMaxWidth(0.9f),
                                contentAlignment = Alignment.BottomCenter) {
                                Button(
                                    onClick = {
                                        confirmCallZazil.value = true
                                              },
                                    modifier = Modifier
                                        .size(100.dp)
                                        .border(
                                            width = 3.dp,
                                            color = Color(0xFF6D1A32),
                                            shape = CircleShape
                                        )
                                        .shadow(
                                            elevation = 10.dp,
                                            shape = CircleShape,
                                            ambientColor = Color.Red,
                                            spotColor = Color.Red,
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF6D1A32)
                                    )
                                ) {
                                    Text(
                                        text = "#Zazil",
                                        fontSize = 15.sp,
                                        color = textShapePrincipalColor,
                                        lineHeight = 15.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                MenuCard(
                                    menuOpc = MenuImg(
                                        Icons.Filled.ShareLocation,
                                        ""
                                    ),
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .offset(x = 0.dp, y = -40.dp)
                                        .padding(8.dp),
                                    colorBack = titleShapePrincipalColor,
                                    iconSize = 30.dp,
                                    fSize = 12.sp,
                                    w = 50.dp,
                                    h = 50.dp,
                                    colorTx = textShapePrincipalColor,
                                    colorTint = shapePrincipalColor
                                ) {
                                    navC.navigate(Router.SECTOR_VIEW.route)
                                }
                                    //Boton para llamadas al 911
                                    Column(
                                        modifier = Modifier
                                            .size(140.dp)
                                            .border(
                                                width = 3.dp,
                                                color = Color(0xFFC50000),
                                                shape = CircleShape
                                            )
                                            .shadow(
                                                elevation = 10.dp,
                                                shape = CircleShape,
                                                ambientColor = Color.Red,
                                                spotColor = Color.Red,
                                            )
                                            .combinedClickable(
                                                onClick = {},
                                                onLongClick = {
                                                    //Redirigimos a la llamada
                                                    confirmCallZazil.value = false
                                                    ToolBox.soundEffect(context, R.raw.tap)
                                                    val u = Uri.parse("tel:911")
                                                    val i = Intent(Intent.ACTION_DIAL, u)
                                                    try {
                                                        context.startActivity(i)
                                                    } catch (s: SecurityException) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "No se pudo realizar la llamada",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }
                                                }
                                            )
                                            .background(Color(0xFFC50000))
                                            .clip(CircleShape),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(30.dp),
                                            imageVector = Icons.Filled.Call,
                                            contentDescription = "",
                                            tint = textShapePrincipalColor
                                        )
                                        Text(
                                            text = "911",
                                            fontSize = 30.sp,
                                            color = textShapePrincipalColor,
                                            lineHeight = 15.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold
                                        )
                                    } //Column
                                MenuCard(
                                    menuOpc = MenuImg(
                                        Icons.Filled.Menu,
                                        ""
                                    ),
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .offset(x = 0.dp, y = -40.dp)
                                        .padding(8.dp),
                                    colorBack = titleShapePrincipalColor,
                                    iconSize = 30.dp,
                                    fSize = 12.sp,
                                    w = 50.dp,
                                    h = 50.dp,
                                    colorTx = textShapePrincipalColor,
                                    colorTint = shapePrincipalColor
                                ) {
                                    showMenuPrincipal.value = true
                                }
                            } //Row
                                Text(
                                    text = "Presione un instante para llamar al 911",
                                    fontSize = 10.sp,
                                    color = textShapePrincipalColor,
                                    lineHeight = 15.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                        }//Column
                    } //else
                } //Box para el boton 911 y el mapa
            }//Card

            Spacer(modifier = Modifier.height(200.dp))
        } //Column principal


        if (showSheetError.value) {
            ErrorDialog(
                title = "Error de sesión",
                info = info,
                context = context, onConfirmation = {
                    showSheetError.value = false
                }
            )
        }

        //DIALOGO PARA CONFIRMAR CIERRE DE SESION
        if(confirmCloseSession.value){
            ConfirmDialog(
                title = "Cerrar sesión",
                info = "¿Desea cerrar la sesión en éste dispositivo?",
                titleAceptar = "Si",
                titleCancelar = "No",
                onAceptar = {
                    logoutViewModel.DoLogOutUser(context)
                    confirmCloseSession.value = false
                    onCloseSession.value = true
                },
                onCancelar = {
                    confirmCloseSession.value = false
                }
            )
        }

        //LIVEDATA PARA CERRAR SESION
        if(onCloseSession.value) {
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            closeSesionState.value?.let {
                when(closeSesionState.value){
                    is RequestResponse.Succes -> {
                        Log.i("MIPOLICIA","SUCCES" )
                        onCloseSession.value = false
                        navC.navigate(Router.LOGIN.route) {
                            popUpTo(navC.graph.id) { inclusive = true }
                        }
                    } //Succes
                    is RequestResponse.Error -> {
                        Log.i("MIPOLICIA","ERROR" )
                        onCloseSession.value = false
                        val errorLogin = closeSesionState.value as RequestResponse.Error
                        messageError.value = "${errorLogin.errorMessage}"
                        showSheetError.value = true
                        onCloseSession.value = false
                        logoutViewModel.resetLogOut()
                    }//Error
                    else -> {
                        onCloseSession.value = false
                    }
                }//when
            }//observable let
        }//onProccesing

        //LIVEDATA PARA REGISTRAR LA LLAMADA
        if(onRegistraLlamada.value) {
            intentoState.value?.let {
                when(intentoState.value){
                    is RequestResponse.Succes -> {
                        Log.i("MIPOLICIA","INTENTO LLAMADA" )
                        onRegistraLlamada.value = false
                        intentoLlamadaViewModel.resetIntentoLlamada()
                    } //Succes
                    is RequestResponse.Error -> {
                        Log.i("MIPOLICIA","ERROR INTENTO LLAMADA" )
                        onRegistraLlamada.value = false
                        intentoLlamadaViewModel.resetIntentoLlamada()
                    }//Error
                    else -> {
                        intentoLlamadaViewModel.resetIntentoLlamada()
                        onRegistraLlamada.value = false
                    }
                }//when
            }//observable let
        }//onProccesing

        if(showAcercaDe.value){
            BottomSheetAcercaDe(){
                showAcercaDe.value = false
            }
        }

        if(showMenuPrincipal.value){
            BottomSheetMenu(
                onConfirmQuejaReporte = onConfirmQuejaReporte,
                onDismiss = {
                    showMenuPrincipal.value = false
                },
                navC = navC)
        }

        //DIALOGO PARA CONFIRMAR SI QUIERE ENVIAR QUEJA O FELICITACION
        if(onConfirmQuejaReporte.value){
            SelectQuejaFelicitacionDialog(
                title = "Elija opción",
                sendQueja = {
                    onConfirmQuejaReporte.value = false
                    navC.navigate(Router.QUEJAS_FELICITACIONES.route)
                },
                sendFelicitacion = {
                    onConfirmQuejaReporte.value = false
                    navC.navigate(Router.QUEJAS_FELICITACIONES.route)
                },
                onCancelar = { onConfirmQuejaReporte.value = false})
        } //  if queja o felicitacion dialog

        if(confirmCallZazil.value){
            ConfirmDialog(
                title = "Confirmación",
                info = "Para activar el código #Zazil llame al 911 y mencione la palabra 'Zazil' y una unidad de la policía, la más cercana, acudirá de inmediato a su reporte.. \n\nConfirme que desea dirigirse a la aplicación de llamada de su dispositivo.",
                titleAceptar = "Si",
                titleCancelar = "No",
                onAceptar = {
                    //Registramos el intento de la llamada
                    onPrepareCall.value = true

                    //Redirigimos a la llamada
                    confirmCallZazil.value = false
                    ToolBox.soundEffect(context, R.raw.tap)
                    val u = Uri.parse("tel:911")
                    val i = Intent(Intent.ACTION_DIAL, u)
                    try {
                        context.startActivity(i)
                    } catch (s: SecurityException) {
                        Toast
                            .makeText(
                                context,
                                "No se pudo realizar la llamada",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                },
                onCancelar = {
                    confirmCallZazil.value = false
                })
        }

    } //Scaffold

    if (onPrepareCall.value) {
        if (ToolBox.testConectivity(context)) {
            onPrepareCall.value = false
            onRegistraLlamada.value = true
            val jsonObj = JSONObject()
            jsonObj.put("phone", user.telefono)
            jsonObj.put("latitude", selectLocation.value.latitude)
            jsonObj.put("longitude", selectLocation.value.longitude)
            jsonObj.put("sector", 0)
            jsonObj.put("device", user.device)
            intentoLlamadaViewModel.DoIntentoLlamada(context, jsonObj)
        } else {
            onPrepareCall.value = false
        }
    }

}
