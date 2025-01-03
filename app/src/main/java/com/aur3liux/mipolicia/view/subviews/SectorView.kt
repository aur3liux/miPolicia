package com.aur3liux.mipolicia.view.subviews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import coil.compose.AsyncImage
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.model.SectorResponse
import com.aur3liux.mipolicia.services.ConsultaSectorRepo
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.view.dialogs.PhotoDialog
import com.aur3liux.mipolicia.viewmodel.ConsultaSectorVM
import com.aur3liux.mipolicia.viewmodel.ConsultaSectorVMFactory
import kotlinx.coroutines.delay
import java.io.IOException

data class ComandanteCard(
    val urlImg: String,
    val nombre: String
)

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
    val nombreDirector = remember { mutableStateOf("") }
    val nombreResponsableA = remember { mutableStateOf("") }
    val nombreResponsableB = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val telefonoDir = remember { mutableStateOf("") }
    val photoDirector = remember { mutableStateOf("") }
    val photoUrlA = remember { mutableStateOf("") }
    val photoUrlB = remember { mutableStateOf("") }

    val onProccessing = rememberSaveable { mutableStateOf(false) }
    val visible = remember { mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    val showPhotoDialog = remember { mutableStateOf(false) }
    val currentPhoto = remember { mutableStateOf("") }
    val currentName = remember { mutableStateOf("") }

    val info = buildAnnotatedString {
        append(messageError.value)
    }

    //Lista de opciones
    val listaComandantes = ArrayList<ComandanteCard>()

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
                        imageVector = Icons.Filled.Close,
                        contentDescription = "", tint = textShapePrincipalColor)})
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = true) {
                delay(300L)
                try {
                    consultaSectorViewModel.DoIntentoLlamada(context, locationDb.latitud, locationDb.longitud, user.device)
                    onProccessing.value = true
                }catch (e: IOException) {

                }
            } //LaunchEffect

                if (!visible.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(70.dp)
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
                    listaComandantes.add(ComandanteCard(urlImg = photoDirector.value, nombre = nombreDirector.value))
                    listaComandantes.add(ComandanteCard(urlImg = photoUrlA.value, nombre = nombreResponsableA.value))
                    listaComandantes.add(ComandanteCard(urlImg = photoUrlB.value, nombre = nombreResponsableB.value))

                    Spacer(modifier = Modifier.height(80.dp))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            text = "Su ubicación aproximada",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold)

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = direccion.value,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            color = MaterialTheme.colorScheme.primary)
                    }
                    HorizontalDivider()

                    Row(modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Sector ",
                            fontWeight = FontWeight.Bold,
                            lineHeight = 15.sp,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = nombreSector.value,
                            fontSize = 14.sp,
                            fontFamily = ToolBox.montseFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        text = "Responsables del sector",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold)

                    //RESPONSABLES
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        itemsIndexed(listaComandantes) { posicion, comandante ->
                            Column(modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {

                                AsyncImage(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.FillWidth,
                                    model = comandante.urlImg,
                                    contentDescription = "")

                                Text(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(60.dp),
                                    text = comandante.nombre,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = ToolBox.quatroSlabFont,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 15.sp)
                            }
                        }
                    }//LazyRo

                    HorizontalDivider()

                    Text(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        text = "Teléfono de atención 24 hrs.",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

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
                            modifier = Modifier
                                .width(120.dp)
                                .height(40.dp),
                            onClick = {
                                try {
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
                            modifier = Modifier
                                .width(120.dp)
                                .height(40.dp),
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
                } //Validamos que el usuario no sea nulo
            Spacer(modifier = Modifier.height(40.dp))
        } //Column
    }//Scaffold

    if (showPhotoDialog.value) {
        PhotoDialog(
            nombre = currentName.value,
            photoUrl = currentPhoto.value,
            onConfirmation = {
                showPhotoDialog.value = false
         })
    }

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
                    nombreDirector.value = infoSector.sectorData.nombreDirector
                    nombreResponsableA.value = infoSector.sectorData.nombreResponsable_A
                    nombreResponsableB.value = infoSector.sectorData.nombreResponsable_B
                    direccion.value = infoSector.sectorData.address
                    telefonoDir.value = infoSector.sectorData.phone_do
                    telefono.value = infoSector.sectorData.phone
                    photoDirector.value = infoSector.sectorData.photo_Director
                    photoUrlA.value = infoSector.sectorData.photo_A
                    photoUrlB.value = infoSector.sectorData.photo_B
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