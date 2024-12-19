package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LogOutRepo
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.viewmodel.LogOutVM
import com.aur3liux.mipolicia.viewmodel.LogOutVMFactory
import kotlinx.coroutines.delay
import java.io.IOException
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SectorView(navC: NavController) {
    val context = LocalContext.current
    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val locationDb = db.locationDao()
    val geocoder = Geocoder(context, Locale.getDefault())
    val addressEvent = rememberSaveable { mutableStateOf("") }

    val confirmCloseSession = rememberSaveable { mutableStateOf(false) }
    val onCloseSession = rememberSaveable { mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    //viewmodel
    val logoutViewModel: LogOutVM = viewModel(
        factory = LogOutVMFactory(logoutRepository = LogOutRepo())
    )

    //LiveData
    val closeSesionState = remember(logoutViewModel) {
        logoutViewModel.UserData
    }.observeAsState()

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Responsable del sector",
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
                        contentDescription = "", tint = Color.White)})
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            val visible = remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                delay(300L)
                try {
                    val addresses = geocoder.getFromLocation(locationDb.getLocationData().latitud, locationDb.getLocationData().longitud, 1)
                    if (addresses?.isNotEmpty() == true) {
                        val address = "${addresses[0].locality}, ${addresses[0].thoroughfare}, ${addresses[0].subLocality}"
                        addressEvent.value = address //"${address.getAddressLine(0)}, ${address.locality}"
                        visible.value = true
                    }
                }catch (e: IOException) {
                    addressEvent.value = "No pudimos recuperar la direccion de tu ubicación."
                    visible.value = true
                }
            } //LaunchEffect

            if (!visible.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Transparent),
                    color = cronosColor,
                    strokeWidth = 4.dp
                )
            } else {
                //Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.4f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp, 20.dp, 5.dp),
                            text = "Usted se encuentra en los alrededores de las siguiente dirección:",
                            fontWeight = FontWeight.Bold,
                            lineHeight = 15.sp,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            text = addressEvent.value,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            lineHeight = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp, 20.dp, 5.dp),
                            text = "Datos del sector:",
                            fontWeight = FontWeight.Bold,
                            lineHeight = 15.sp,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        )
                        //NOMBRE COMPLETO
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                                text = "Sector: ",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "CENTRO MURALLAS" ,
                                fontSize = 11.sp,
                                fontFamily = ToolBox.montseFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        } //Row nombre completo

                        HorizontalDivider()

                        //EMAIL
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                                text = "Responsable: ",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Policenio Justino",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        } //Row EMAIL

                        /*HorizontalDivider()
                        //Turno
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                                text = "Turno: ",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text =  "Matutino",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        } //Row turno
                        */

                        HorizontalDivider()

                        //TELEFONO
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                                text = "Teléfono: ",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "981111111",
                                fontSize = 12.sp,
                                fontFamily = ToolBox.quatroSlabFont,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                modifier = Modifier.padding(horizontal = 10.dp).clickable {  },
                                imageVector = Icons.Filled.Whatsapp, contentDescription = "")
                            Icon(
                                modifier = Modifier.padding(horizontal = 10.dp).clickable {  },
                                imageVector = Icons.Filled.Call, contentDescription = "")
                        }
                        HorizontalDivider()
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp, 20.dp, 5.dp),
                            text = "Si está en una situación de emergencia póngase a salvo y llame al 911",
                            fontWeight = FontWeight.Bold,
                            lineHeight = 15.sp,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )

                        //Foto
                        Image(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.agente),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth
                        )
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(20.dp))


                    }//Card
                //} //Column
            } //Validamos que el usuario no sea nulo
        } //Column
    }//Scaffold

    //DIALOGO PARA CONFIRMAR CIERRE DE SESION
    if(confirmCloseSession.value){
        ConfirmDialog(
            title = "Confirmación",
            info = "Confirme que desea cerrar la sesion en éste dispositivo.",
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
                    Log.i("MI POLICIA","ERROR" )
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

}