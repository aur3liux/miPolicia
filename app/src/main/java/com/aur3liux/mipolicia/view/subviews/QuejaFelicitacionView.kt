package com.aur3liux.mipolicia.view.subviews

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.encuestaprofe.view.components.radiobutton.DataRadioButton
import com.aur3liux.encuestaprofe.view.components.radiobutton.RadioGroupH
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetImage
import com.aur3liux.mipolicia.view.dialogs.AddEvienciaDialog
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.view.dialogs.DescripcionDialog
import com.aur3liux.mipolicia.view.dialogs.MapDialog
import com.aur3liux.mipolicia.view.dialogs.MenuReporteDialog
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuejaFelicitacionView(navC: NavController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val user = db.userDao().getUserData()

    val locationDb = db.locationDao().getLocationData()
    val selectLocation =
        remember { mutableStateOf(LatLng(locationDb.latitud, locationDb.longitud)) }
    val showMenuReportesDialog = rememberSaveable { mutableStateOf(false) }
    val showMapReporte = rememberSaveable { mutableStateOf(false) }
    val showDescripcionDialog = rememberSaveable { mutableStateOf(false) }
    val txDescription = rememberSaveable { mutableStateOf("") }
    val txAgente = rememberSaveable { mutableStateOf(Store.APP.txNombreAgente) }
    val txPatrulla = rememberSaveable { mutableStateOf(Store.APP.txNumeroUnidad) }
    val indexReporte = rememberSaveable { mutableStateOf(0) }

    val onProccessing = rememberSaveable { mutableStateOf(false) }
    val onConfirmSentReport = remember { mutableStateOf(false) }
    val onAddEvicencia = remember { mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val showSheetImage = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    //CARGAR imagenes
    var imageUri1 = remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    var chekImg1 = remember { mutableStateOf(false) }
    var imageUri2 = remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    var chekImg2 = remember { mutableStateOf(false) }
    var indexEvidencia = remember { mutableStateOf(0) }

    //ABRIR LA CAMARA
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            if (indexEvidencia.value == 1) {
                imageUri1.value = uri
                chekImg1.value = true
            }
            if (indexEvidencia.value == 2) {
                imageUri2.value = uri
                chekImg2.value = true
            }
        } //if uri != null
    }

    //ABRIR LA CAMARA PARA TOMAR UNA FOTOGRAFIA
    val file = context.createImageFile()
    val uriPhoto = remember {
        mutableStateOf(context.getUri(context, file))
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = {
                if (it) {
                    if (indexEvidencia.value == 1) {
                        imageUri1.value = uriPhoto.value
                        chekImg1.value = true
                    }
                    if (indexEvidencia.value == 2) {
                        imageUri2.value = uriPhoto.value
                        chekImg2.value = true
                    }
                }
            })

    val permissionLauncherCamera =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                cameraLauncher.launch(uriPhoto.value)
            } else {
                Toast.makeText(context, "Sin permiso para usar la cámara", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quejas o felicitaciones",
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
                            .clickable { navC.popBackStack() }
                            .size(30.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = textShapePrincipalColor)
                })
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    //.weight(0.3f)
                    .padding(top = 30.dp),
                text = "¿Usted deser enviar?",
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )


            //AGENTE
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                label = { Text(text = "Agente") },
                value = txAgente.value,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                onValueChange = {},
                placeholder = { Text(text = "Nombre del agente") },
                shape = RoundedCornerShape(percent = 10),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                    focusedPlaceholderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            //PATRULLA
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                value = txPatrulla.value,
                label = { Text(text = "Numero de la unidad") },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                onValueChange = {},
                placeholder = { Text(text = "Ubicación del problema") },
                shape = RoundedCornerShape(percent = 10),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                    focusedPlaceholderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            //DESCRIPCION DE LOS HECHOS
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                //TIPO DE REPORTE
                OutlinedTextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 10.dp),
                    readOnly = true,
                    maxLines = 1,
                    label = {Text(text = "Describa brevemente los motivos")},
                    value = txDescription.value,
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    onValueChange = {},
                    placeholder = { Text(text = "Motivos") },
                    shape = RoundedCornerShape(percent = 10),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedPlaceholderColor = Color.Gray
                    )
                )

                Card(
                    modifier = Modifier
                        .weight(0.1f)
                        .clickable {
                            showDescripcionDialog.value = !showDescripcionDialog.value
                        },
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )
                ) {
                    Box(modifier = Modifier
                        .size(40.dp),
                        contentAlignment = Alignment.Center){
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.EditNote,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                } // Card
            }


            Spacer(modifier = Modifier.height(10.dp))

            //EVIDENCIAS
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                text = "Evidencias (Opcional)",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            //CONTROL PARA VER LAS EVIDENCIAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (!onProccessing.value) {
                    MenuCard(
                        menuOpc = MenuImg(
                            if (chekImg1.value) Icons.Filled.Check else Icons.Filled.Image,
                            "Imagen"
                        ),
                        fSize = 12.sp,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(8.dp),
                        h = 60.dp,
                        w = 60.dp,
                        colorBack = if (chekImg1.value) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
                        colorTx = MaterialTheme.colorScheme.surface,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        indexEvidencia.value = 1
                        if (chekImg1.value) showSheetImage.value = true
                        else onAddEvicencia.value = true
                    } //Eviddencia 1

                    MenuCard(
                        menuOpc = MenuImg(
                            if (chekImg2.value) Icons.Filled.Check else Icons.Filled.Image,
                            "Imagen"
                        ),
                        fSize = 12.sp,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(8.dp),
                        h = 60.dp,
                        w = 60.dp,
                        colorBack = if (chekImg2.value) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
                        colorTx = MaterialTheme.colorScheme.surface,
                        colorTint = MaterialTheme.colorScheme.background
                    ) {
                        indexEvidencia.value = 2
                        if (chekImg2.value) showSheetImage.value = true
                        else onAddEvicencia.value = true
                    } //Evidencia 2
                }//if
            }//Row evidencias

            Spacer(modifier = Modifier.height(15.dp))

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                text = "Enviar reporte",
                fSize = 15.sp,
                shape = RoundedCornerShape(15),
                textColor = Color.White,
                backColor = MaterialTheme.colorScheme.surface,
                estatus = onConfirmSentReport,
                onClick = {
                    onConfirmSentReport.value = !onConfirmSentReport.value
                } //onClick
            )

            Box(
                modifier = Modifier
                    .offset(x = -130.dp, y = 170.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_gob),
                    contentDescription = "",
                    tint = Color.Gray
                )
            }
        } //Column
    } //Scaffold

    if (showMapReporte.value) {
        MapDialog(
            latitud = locationDb.latitud,
            longitud = locationDb.longitud,
            onConfirmation = { showMapReporte.value = false })
    }

    if (showMenuReportesDialog.value) {
        MenuReporteDialog(
            reporteTx = txAgente,
            index = indexReporte,
            onConfirmation = { showMenuReportesDialog.value = false })
    }//show menu horizontal


    if (showSheetImage.value) {
        if (indexEvidencia.value == 1) {
            BottomSheetImage(imageUri = imageUri1.value) {
                showSheetImage.value = false
            }
        }
        if (indexEvidencia.value == 2) {
            BottomSheetImage(imageUri = imageUri2.value) {
                showSheetImage.value = false
            }
        }
    }

    if (onAddEvicencia.value) {
        AddEvienciaDialog(
            title = "Evidencia",
            openGallery = {
                launcher.launch("image/*")
                onAddEvicencia.value = false
            },
            openCamera = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    uriPhoto.value = context.getUri(context, context.createImageFile())
                    cameraLauncher.launch(uriPhoto.value)
                } else {
                    // Request a permission
                    permissionLauncherCamera.launch(Manifest.permission.CAMERA)
                }
                onAddEvicencia.value = false
            },
            onCancelar = { onAddEvicencia.value = false })
    }


    //DIALOGO PARA ENVIAR LA QUEJA O FELICITACION
    if(onConfirmSentReport.value){
        ConfirmDialog(
            title = "Confirmación",
            info = "Confirme que desea enviar el reporte",
            titleAceptar = "Si",
            titleCancelar = "No",
            onAceptar = {
                onConfirmSentReport.value = false
            },
            onCancelar = {
                onConfirmSentReport.value = false
            }
        )
    }

    //DIALOGO PARA ESCRIBIR LOS MOTIVOS DE LA QUEJA
    if(showDescripcionDialog.value){
        DescripcionDialog(
            descripcion = txDescription,
            context = context,
            onConfirmation = {
                showDescripcionDialog.value = false
            }
        )
    }

}
