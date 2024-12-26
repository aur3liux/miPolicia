package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
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
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.model.SectorResponse
import com.aur3liux.mipolicia.services.ConsultaSectorRepo
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.titleShapePrincipalColor
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.viewmodel.ConsultaSectorVM
import com.aur3liux.mipolicia.viewmodel.ConsultaSectorVMFactory
import kotlinx.coroutines.delay
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SectorView(navC: NavController) {
    val context = LocalContext.current
    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val locationDb = db.locationDao().getLocationData()
    val user = db.userDao().getUserData()

    val nombreSector = remember { mutableStateOf("") }
    val nombreResponsable = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val photoUrl = remember { mutableStateOf("") }

    val onProccessing = rememberSaveable { mutableStateOf(false) }
    val visible = remember { mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    val info = buildAnnotatedString {
        append(messageError.value)
    }

    //viewmodel
    val consultaSectorViewModel: ConsultaSectorVM = viewModel(
        factory = ConsultaSectorVMFactory(sectorRepository = ConsultaSectorRepo())
    )

    //LiveData
    val sectorState = remember(consultaSectorViewModel) {
        consultaSectorViewModel.SectorData
    }.observeAsState()

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        containerColor = shapePrincipalColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Datos del sector",
                        fontSize = 15.sp,
                        letterSpacing = 0.3.sp,
                        fontFamily = ToolBox.gmxFontRegular,
                        color = textShapePrincipalColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = shapePrincipalColor),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { navC.popBackStack() },
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = textShapePrincipalColor)})
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LaunchedEffect(key1 = true) {
                delay(300L)
                try {
                    consultaSectorViewModel.DoIntentoLlamada(context, locationDb.latitud, locationDb.longitud)
                    onProccessing.value = true
                }catch (e: IOException) {

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
                    Text(
                        text = "Buscando",
                        fontWeight = FontWeight.Bold,
                        lineHeight = 15.sp,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        text = "Usted se encuentra aproximadamente:",
                        fontWeight = FontWeight.Bold,
                        lineHeight = 15.sp,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        color = titleShapePrincipalColor
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        text = direccion.value,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 15.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    HorizontalDivider()

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 10.dp, 20.dp, 5.dp),
                        text = "En este sector puede pedir apoyo",
                        fontWeight = FontWeight.Bold,
                        lineHeight = 15.sp,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sector: ",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = nombreSector.value,
                            fontSize = 11.sp,
                            fontFamily = ToolBox.montseFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row nombre completo

                    //DATOS
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Responsable: ",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = nombreResponsable.value,
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row

                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = telefono.value,
                        fontSize = 12.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    //BOTONES
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //Whatsapp
                        Button(
                            modifier = Modifier.width(120.dp).height(40.dp),
                            onClick = {                                          try {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW, Uri.parse(
                                            String.format(
                                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                                telefono.value,
                                                "Mi nombres es ${user.nombre} ${user.apellidos} estoy en el sector ${nombreSector.value} y necesito su apoyo por favor..."
                                            )
                                        )
                                    )
                                )
                            } catch (ex: Exception) {
                                Toast
                                    .makeText(context, "No pudimos abrir whatsapp", Toast.LENGTH_SHORT)
                                    .show()
                            } },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp,
                                pressedElevation = 0.dp,
                                disabledElevation = 0.dp
                            ),
                            shape = RoundedCornerShape(10.dp),
                        ) {

                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(horizontal = 10.dp),
                                tint = Color(0xFF8BC34A),
                                imageVector = Icons.Filled.Whatsapp, contentDescription = ""
                            )
                        }//Button

                        //Llamada
                        Button(
                            modifier = Modifier.width(120.dp).height(40.dp),
                            onClick = {
                                val u = Uri.parse("tel:${telefono.value}")
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
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp,
                                pressedElevation = 0.dp,
                                disabledElevation = 0.dp
                            ),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(horizontal = 10.dp),
                                tint = Color(0xFF03A9F4),
                                imageVector = Icons.Filled.Call, contentDescription = ""
                            )
                        }//Button
                    } //Row

                    Spacer(modifier = Modifier.height(10.dp))

                    //Foto
                    Image(
                        painter = painterResource(id = R.drawable.agente),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(300.dp)
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 10.dp, 20.dp, 5.dp),
                        text = "Si está en una situación de emergencia póngase a salvo y llame al 911",
                        fontWeight = FontWeight.Bold,
                        lineHeight = 15.sp,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    /*   Image(
                 painter = rememberAsyncImagePainter("https://images.vexels.com/media/users/3/148636/isolated/lists/d43fb2408da613edbe2e664e01729c03-mapa-del-estado-de-campeche.png" ),
                 contentDescription = "",
                 modifier = Modifier
                     .wrapContentHeight()
                     .wrapContentWidth() )

                AsyncImage(
                     modifier = Modifier
                         .padding(top = 20.dp),
                        // .fillMaxWidth(),
                    // model = photoUrl.value,
                     model = "https://images.vexels.com/media/users/3/148636/isolated/lists/d43fb2408da613edbe2e664e01729c03-mapa-del-estado-de-campeche.png",
                     contentDescription = ""
                 )
                */
                } //Validamos que el usuario no sea nulo
        } //Column
    }//Scaffold

    if (showSheetError.value) {
        ErrorDialog(
            title = "Error en la consulta",
            info = info,
            context = context, onConfirmation = {
                showSheetError.value = false
                onProccessing.value = false
            }
        )
    }

    //LIVEDATA PARA CERRAR SESION
    if(onProccessing.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        sectorState.value?.let {
            when(sectorState.value){
                is SectorResponse.Succes -> {
                    Log.i("MIPOLICIA","SUCCES" )
                    val infoSector = sectorState.value as SectorResponse.Succes
                    nombreSector.value = infoSector.sectorData.nombreSector
                    nombreResponsable.value = infoSector.sectorData.nombreResponsable
                    direccion.value = infoSector.sectorData.address
                    telefono.value = infoSector.sectorData.phone
                    photoUrl.value = infoSector.sectorData.photo
                    onProccessing.value = false
                    visible.value = true
                    consultaSectorViewModel.resetConsultaSector()
                } //Succes
                is SectorResponse.Error -> {
                    Log.i("MI POLICIA","ERROR" )
                    onProccessing.value = false
                    val errorLogin = sectorState.value as SectorResponse.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onProccessing.value = false
                    navC.popBackStack()
                    consultaSectorViewModel.resetConsultaSector()
                }//Error
                else -> {
                    onProccessing.value = false
                }
            }//when
        }//observable let
    }//onProccesing

}