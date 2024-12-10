package com.aur3liux.mipolicia.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.ConfirmDialog
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LogOutRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.lGradient2
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetMenu
import com.aur3liux.mipolicia.view.cibernetica.PoliciaCiberneticaView
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
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
import kotlinx.coroutines.delay
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navC: NavController) {
    val context = LocalContext.current

    val showMenuPrincipal = rememberSaveable { mutableStateOf(false) }
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val locationDb = db.locationDao()
    val selectLocation = remember { mutableStateOf(LatLng(locationDb.getLocationData().latitud, locationDb.getLocationData().longitud)) }
    val selectDelito = remember { mutableStateOf(false) }
    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    val confirmCloseSession = rememberSaveable { mutableStateOf(false) }
    val onCloseSession = rememberSaveable { mutableStateOf(false) }
    //viewmodel
    val logoutViewModel: LogOutVM = viewModel(
        factory = LogOutVMFactory(logoutRepository = LogOutRepo())
    )

    //LiveData
    val closeSesionState = remember(logoutViewModel) {
        logoutViewModel.UserData
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
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, if(darkTheme) R.raw.dark_maps else R.raw.light_map)
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
        position = CameraPosition.fromLatLngZoom(selectLocation.value, 14f)
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            //BOTON PARA EL MENU PRINCIPAL DESPLEGABLE
            FloatingActionButton(
                modifier = Modifier
                    .size(60.dp),
                shape = CircleShape,
                onClick = {
                    showMenuPrincipal.value = true
                },
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            }
        }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                modifier = Modifier
                    .background(lGradient2)
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.banner_feet),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(30.dp))
            //Column encabezado titulos
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "MI POLICIA",
                    fontSize = 24.sp,
                    letterSpacing = 0.3.sp,
                    fontFamily = ToolBox.gmxFontRegular,
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "CAMPECHE",
                    fontSize = 14.sp,
                    letterSpacing = 0.2.sp,
                    fontFamily = ToolBox.gmxFontRegular,
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(modifier = Modifier.fillMaxWidth(0.9f).padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    //Reporte ciudadano
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .height(60.dp)
                            .clickable {

                            },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.Newspaper,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                        Text(
                            text = "Reporte ciudadano",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.inverseSurface,
                            lineHeight = 15.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    } // Row

                    //Marco legal
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .height(60.dp)
                            .clickable {
                                navC.navigate(Router.MARCOLEGAL_VIEW.route)
                            },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.Gavel,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                        Text(
                                text = "Marco legal",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.inverseSurface,
                            lineHeight = 15.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    } // Row
                }
            } //Column encabezado titulos

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(15.dp)
            ) {

                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center){
                        if(onCloseSession.value){
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(60.dp)
                                    //.padding(bottom = 100.dp)
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

                            //911
                            MenuCard(
                                menuOpc = MenuImg(Icons.Filled.Call, "911"),
                                modifier = Modifier
                                    .offset(x = 0.dp, y = 100.dp),
                                colorBack =MaterialTheme.colorScheme.surface,
                                colorTx = MaterialTheme.colorScheme.inverseSurface,
                                fSize = 25.sp,
                                shape = CircleShape,
                                w = 120.dp,
                                h = 120.dp
                            ) {
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
                    }
                } //Box para el boton 911 y el mapa
            }//Card
        } //Column


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

        if(showMenuPrincipal.value){
            BottomSheetMenu(
                onCloseSesion = {
                     confirmCloseSession.value = true
                },
                onDismiss = {
                    showMenuPrincipal.value = false
                },
                navC = navC)
        }
    } //Scaffold
}