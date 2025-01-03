package com.aur3liux.mipolicia.view.subviews

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.CustomDatePickerDialog
import com.aur3liux.mipolicia.components.DialWithDialogExample
import com.aur3liux.mipolicia.components.MenuCard
import com.aur3liux.mipolicia.components.MenuImg
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.ReporteRepo
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.auth.checkDataInputRegister
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetImage
import com.aur3liux.mipolicia.view.dialogs.AddEvienciaDialog
import com.aur3liux.mipolicia.view.dialogs.ConfirmDialog
import com.aur3liux.mipolicia.view.dialogs.DescripcionDialog
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.view.dialogs.MapDialog
import com.aur3liux.mipolicia.view.dialogs.MenuReporteDialog
import com.aur3liux.mipolicia.viewmodel.ReportVM
import com.aur3liux.mipolicia.viewmodel.ReportVMFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReporteCiudadano(navC: NavController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDb::class.java,
        Store.DB.NAME
    )
        .allowMainThreadQueries()
        .build()

    val locationDb = db.locationDao().getLocationData()
    val selectLocation = remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val showMenuReportesDialog = rememberSaveable { mutableStateOf(false) }
    val showMapReporte = rememberSaveable { mutableStateOf(false) }
    val showDescripcionDialog = rememberSaveable { mutableStateOf(false) }
    val txDescription = rememberSaveable { mutableStateOf("") }
    val txReporte = rememberSaveable { mutableStateOf(Store.APP.txReportes) }
    val txUbicacion = rememberSaveable { mutableStateOf(Store.APP.txUbicacion) }
    val txFechaEvento = rememberSaveable { mutableStateOf("") }
    val currentTimeEvento = rememberSaveable { mutableStateOf("") }
    val txHoraEvento = rememberSaveable { mutableStateOf("") }
    val indexReporte = rememberSaveable { mutableStateOf(0) }

    val onProccessing = rememberSaveable { mutableStateOf(false) }
    val onPrepareSend = remember{ mutableStateOf(false) }
    val onConfirmSentReport = remember{ mutableStateOf(false) }
    val onAddEvicencia = remember{ mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val showSheetImage = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    val info = buildAnnotatedString {
        append(messageError.value)
    }

    //CARGAR imagenes
    var imageUri1 = remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    var chekImg1 = remember { mutableStateOf(false) }
    var imageUri2 = remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    var chekImg2 = remember { mutableStateOf(false) }
    var indexEvidencia = remember { mutableStateOf(0) }

    val showTimePicker = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }

    //viewmodel
    val reporteViewModel: ReportVM = viewModel(
        factory = ReportVMFactory(reportRepository = ReporteRepo())
    )

    //LiveData
    val reportState = remember(reporteViewModel) {
        reporteViewModel.ReportData
    }.observeAsState()

    //ABRIR LA CAMARA
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
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
    val uriPhoto = remember{
        mutableStateOf(context.getUri(context, file))
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = {
                if (it) {
                    if(indexEvidencia.value == 1) {
                        imageUri1.value = uriPhoto.value
                        chekImg1.value = true
                    }
                    if(indexEvidencia.value == 2) {
                        imageUri2.value = uriPhoto.value
                        chekImg2.value = true
                    }
                }
            })

    val permissionLauncherCamera =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()) {
            if (it) {
                cameraLauncher.launch(uriPhoto.value)
            } else {
                Toast.makeText(context, "Sin permiso para usar la cámara", Toast.LENGTH_SHORT).show()
            }
    }

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Datos del reporte",
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
                        imageVector = Icons.Filled.Close,
                        contentDescription = "", tint = textShapePrincipalColor)
                })
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {

            Spacer(modifier = Modifier.height(10.dp))

            //TIPO DE REPORTE
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 10.dp),
                    readOnly = true,
                    label = {Text(text = "Tipo de reporte")},
                    value = txReporte.value,
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    onValueChange = {},
                    placeholder = { Text(text = "Tipo de reporte") },
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
                            showMenuReportesDialog.value = !showMenuReportesDialog.value
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
                            imageVector = Icons.Filled.Add,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                } // Card
            }

            Spacer(modifier = Modifier.height(10.dp))

            //UBICACION DE REPORTE
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 10.dp),
                    readOnly = true,
                    value = txUbicacion.value,
                    label = {Text("Ubicación del problema")},
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
                Card(
                    modifier = Modifier
                        .weight(0.1f)
                        .clickable {
                            showMapReporte.value = !showMapReporte.value
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
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                } // Card
            }

            Spacer(modifier = Modifier.height(10.dp))

            //DESCRIPCION DE LOS HECHOS
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 10.dp),
                    readOnly = true,
                    maxLines = 1,
                    label = {Text(text = "Descripción de motivos")},
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

            //FECHA DE LOS EVENTOS
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 10.dp),
                    readOnly = true,
                    value = txFechaEvento.value,
                    label = {Text(text = "Fecha")},
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    onValueChange = {},
                    placeholder = { Text(text = "Fecha del problema") },
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
                            showDatePicker.value = true
                        },
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )) {
                    Box(modifier = Modifier
                        .size(40.dp),
                        contentAlignment = Alignment.Center){
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                } // Card
            }

            Spacer(modifier = Modifier.height(10.dp))

            //HORA DE LOS EVENTOS
            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 10.dp),
                    readOnly = true,
                    value = txHoraEvento.value,
                    label = {Text(text = "Hora")},
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    onValueChange = {},
                    placeholder = { Text(text = "Hora del problema") },
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
                            showTimePicker.value = true
                        },
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )) {
                    Box(modifier = Modifier
                        .size(40.dp),
                        contentAlignment = Alignment.Center){
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.AccessTime,
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
                text = "Evidencias (Opcionales)",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            //CONTROL PARA VER LAS EVIDENCIAS
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween) {
                if (!onProccessing.value) {
                    MenuCard(
                        menuOpc = MenuImg(
                            if (chekImg1.value) Icons.Filled.Check else Icons.Filled.Image,
                            "Imagen 1"
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
                            "Imagen 2"
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
                    currentTimeEvento.value = ToolBox.mergeDateTime(txFechaEvento.value, txHoraEvento.value)
                    onConfirmSentReport.value = true
                } //onClick
            )

            Box(modifier = Modifier
                .offset(x = -130.dp, y = 170.dp),
                contentAlignment = Alignment.Center) {
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

        //DIALOGO PARA CONFIRMAR CIERRE DE SESION
        if(onConfirmSentReport.value){
            ConfirmDialog(
                title = "Confirmación",
                info = "Confirme que desea enviar el reporte",
                titleAceptar = "Si",
                titleCancelar = "No",
                onAceptar = {
                    onConfirmSentReport.value = false
                    onPrepareSend.value = true
                },
                onCancelar = {
                    onConfirmSentReport.value = false
                }
            )
        }

    }//Scaffold



    //CATALOGO - LISTADO DE OPCIONES DE REPORTE
    if(showMenuReportesDialog.value){
        MenuReporteDialog(
            reporteTx = txReporte,
            index = indexReporte,
            onConfirmation = { showMenuReportesDialog.value = false})
    }//show menu horizontal


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


    if(onAddEvicencia.value) {
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
            onCancelar = { onAddEvicencia.value = false})
    } //if Dialog add evidencia

    if(showMapReporte.value){
        MapDialog(
            latitud = locationDb.latitud,
            longitud = locationDb.longitud,
            selectLocation = selectLocation,
            onConfirmation = {
                showMapReporte.value = false
                if(selectLocation.value.latitude != 0.0)
                    txUbicacion.value = "Ubicación marcada"
            }
        )
    }

    if (showSheetImage.value) {
        if(indexEvidencia.value == 1) {
            BottomSheetImage(imageUri = imageUri1.value) {
                showSheetImage.value = false
            }
        }
        if(indexEvidencia.value == 2) {
            BottomSheetImage(imageUri = imageUri2.value) {
                showSheetImage.value = false
            }
        }
    }


    if(onPrepareSend.value) {
        var testInput = checkDataInputReport(
            latiEvent = selectLocation.value.latitude,
            lonEvent = selectLocation.value.longitude,
            tipoReporte = indexReporte.value,
            fechaHora = currentTimeEvento.value
        )
        if(testInput.length > 0){
            onPrepareSend.value = false
            showSheetError.value = true
            messageError.value = testInput
        }else {
            onPrepareSend.value = false
            if(ToolBox.testConectivity(context)){
                // delayed.value = false
                LaunchedEffect(key1 = true) {
                    val jsonObj = JSONObject()
                    jsonObj.put("event_latitude", selectLocation.value.latitude)
                    jsonObj.put("event_longitude", selectLocation.value.longitude)
                    jsonObj.put("description", txDescription.value)
                    jsonObj.put("citizen_report_type_id", indexReporte.value)
                    jsonObj.put("user_latitude", locationDb.latitud)
                    jsonObj.put("user_longitude", locationDb.longitud)
                    jsonObj.put("event_at", "")
                    if (chekImg1.value) {
                        val bitmap1 = BitmapFactory.decodeStream(
                            context
                                .getContentResolver().openInputStream(imageUri1.value!!)
                        );
                        jsonObj.put(
                            "photo_evidence_1",
                            "data:image/png;base64,${ToolBox.BitmaptoBase64(bitmap1)}"
                        )
                    }
                    if (chekImg2.value) {
                        val bitmap2 = BitmapFactory.decodeStream(
                            context
                                .getContentResolver().openInputStream(imageUri2.value!!)
                        );
                        jsonObj.put(
                            "photo_evidence_1",
                            "data:image/png;base64,${ToolBox.BitmaptoBase64(bitmap2)}"
                        )
                    }
                    reporteViewModel.DoSentReport(context, jsonObj)
                    onProccessing.value = true
                }

                // delayed.value = true
                //enabledInput.value = false
            }else {
                onPrepareSend.value = false
                showSheetError.value = true
                messageError.value = "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
            }
        } //Validacion de datos no vacíos
    }  //onPrepareSend


    // date picker component
    if (showDatePicker.value) {
       // CustomDatePicker(title = "Fecha del evento", date = txFechaEvento)
        CustomDatePickerDialog(
            onAccept = {
                showDatePicker.value = false // cierra el calendario
                if (it != null) {
                    txFechaEvento.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate().toString()
                }
            },
            onCancel = {
                showDatePicker.value = false
            }
        )
    }

    // time picker component
    if (showTimePicker.value) {
        DialWithDialogExample(
            txTime = txHoraEvento,
            onConfirm = {showTimePicker.value = false}) {
        }
    }

    if (showSheetError.value) {
        ErrorDialog(
            title = "Error de envío",
            info = info,
            context = context, onConfirmation = {
                onPrepareSend.value = false
                showSheetError.value = false
                onProccessing.value = false
            }
        )
    }


    //LIVEDATA PARA ENVIAR EL REPORTE
    if(onProccessing.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        reportState.value?.let {
            when(reportState.value){
                is RequestResponse.Succes -> {
                    Log.i("MIPOLICIA","SUCCES" )
                    onProccessing.value = false
                    navC.popBackStack()
                    Toast.makeText(context, "Reporte enviado", Toast.LENGTH_SHORT).show()
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("MIPOLICIA","ERROR" )
                    onProccessing.value = false
                    val errorReport = reportState.value as RequestResponse.Error
                    messageError.value = "${errorReport.errorMessage}"
                    showSheetError.value = true
                    onProccessing.value = false
                    reporteViewModel.resetSentReport()
                }//Error
                else -> {
                    onProccessing.value = false
                }
            }//when
        }//observable let
    }//onProccesing

}  //Composable



/********************/
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir /* directory */
    )
}

fun Context.getUri(context: Context, file: File): Uri{
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.aur3liux.mipolicia.provider", file
    )
}

fun checkDataInputReport(
    latiEvent: Double,
    lonEvent: Double,
    tipoReporte: Int,
    fechaHora: String): String{
    var valueReturn = ""
    if(latiEvent == 0.0 || lonEvent == 0.0)
        valueReturn = "Debe seleccionar en el mapa el lugar donde ocurrió el evento del reporte"
    else if(tipoReporte == 0)
        valueReturn = "Indique que tipo de problema desea reportar"
    else if(fechaHora.isNullOrBlank())
        valueReturn = "Debe selecionar la fecha y hora aproximada en que sucedió."
    return valueReturn
}