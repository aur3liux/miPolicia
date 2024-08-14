package com.aur3liux.naats.view

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aur3liux.naats.Router
import com.aur3liux.naats.ToolBox
import com.aur3liux.naats.components.BottomSheet
import com.aur3liux.naats.components.PermissionLocationTurned
import com.aur3liux.naats.components.PoliticaPrivacidadTurned
import com.aur3liux.naats.components.RoundedButton
import com.aur3liux.naats.components.TextFieldData
import com.aur3liux.naats.model.RequestCurp
import com.aur3liux.naats.model.RequestResponse
import com.aur3liux.naats.services.CurpRepo
import com.aur3liux.naats.services.RegistroRepo
import com.aur3liux.naats.ui.theme.botonColor
import com.aur3liux.naats.ui.theme.dGradient1
import com.aur3liux.naats.ui.theme.dGradient2
import com.aur3liux.naats.ui.theme.lGradient1
import com.aur3liux.naats.ui.theme.lGradient2
import com.aur3liux.naats.viewmodel.ConsultaCurpVM
import com.aur3liux.naats.viewmodel.ConsultaCurpVMFactory
import com.aur3liux.naats.viewmodel.RegistroVM
import com.aur3liux.naats.viewmodel.RegistroVMFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.json.JSONObject

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AccesoRegistro(navC: NavController) {
    val textoBotonLogin = remember{ mutableStateOf("Iniciar sesión") }

    //-- DATOS DE ENTRADA
    val curp = rememberSaveable{ mutableStateOf("GOCA731205HCCNCR00") }
    val nombre = rememberSaveable{ mutableStateOf("") }
    val paterno = rememberSaveable{ mutableStateOf("") }
    val materno = rememberSaveable{ mutableStateOf("") }
    val fNacimiento = rememberSaveable{ mutableStateOf("") }
    val sexo = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordCnofirm = rememberSaveable{ mutableStateOf("") }
    val telefono = rememberSaveable{ mutableStateOf("") }
    val email = rememberSaveable{ mutableStateOf("") }
    val municipio = rememberSaveable{ mutableStateOf("") }
    val colonia = rememberSaveable{ mutableStateOf("") }
    val calleNumero = rememberSaveable{ mutableStateOf("") }
    val context = LocalContext.current

    //---------
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordConfirmVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val prepareCreateAccount = remember{ mutableStateOf(false) }
    val consultandoCurpData = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }

    var darkTheme = isSystemInDarkTheme()
    val color1 = if(darkTheme) lGradient1 else dGradient1
    val color2 = if(darkTheme) lGradient2 else dGradient2

    val showSheetError = remember { mutableStateOf(false) }
    val enabledInput = remember { mutableStateOf(true) }
    val messageError = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val hasReadPoliticaPrivacidad = rememberSaveable{ mutableStateOf(false) }
    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    //viewmodel
    val registroViewModel: RegistroVM = viewModel(
        factory = RegistroVMFactory(registroRepository = RegistroRepo())
    )

    val registroSesionState = remember(registroViewModel) {registroViewModel.RegistroData}.observeAsState() //LiveData

    val consultaCurpViewModel: ConsultaCurpVM = viewModel(
        factory = ConsultaCurpVMFactory(curpRepository = CurpRepo()))
    val curpData = remember(consultaCurpViewModel) {consultaCurpViewModel.CurpData}.observeAsState() //LiveData


    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(title = { Text(text = "Nueva cuenta", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = lGradient1),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { navC.popBackStack() }
                            .size(30.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "", tint = Color.White)})
        }) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color1, // Start color
                            color2
                        )  // End color
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Ingresa tus datos para crear una cuenta",
                    fontSize = 14.sp,
                    fontFamily = ToolBox.montseFont,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Box {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = curp.value,
                            onValueChange = {
                                if(it.length <= 18)
                                    curp.value = it
                                else {}
                            },
                            label = { Text(text = "CURP *")},
                            placeholder = { Text(text = "CURP *")},
                            enabled = enabledInput.value,
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center),
                            trailingIcon = {
                                Icon(
                                    modifier = Modifier.clickable {
                                        if(curp.value.isBlank()){
                                            messageError.value = "La CURP es un dato obligatorio"
                                            showSheetError.value = true
                                        }else{
                                            consultandoCurpData.value = true
                                            consultaCurpViewModel.DoConsultaCurp(context, curp.value)
                                        }
                                    },
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "")},
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Go
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.primary, //color de texto
                                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                                focusedPlaceholderColor = Color.Gray)
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Nombre: ${nombre.value}",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Apellidos:: ${paterno.value} ${materno.value}",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Sexo: ${sexo.value}",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )

                        //CONTRASEÑA
                        TextFieldData(
                            modifier = Modifier.fillMaxWidth(),
                            textFieldValue = password,
                            textLabel = "Contraseña",
                            txColor = Color.Black,
                            maxChar = 25,
                            enabled = enabledInput.value,
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.None,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            imeAction = ImeAction.Done,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        passwordVisibility = !passwordVisibility
                                    }) {
                                    Icon(
                                        imageVector = if(passwordVisibility) {
                                            Icons.Default.Visibility
                                        } else {
                                            Icons.Default.VisibilityOff },
                                        contentDescription = ""
                                    )
                                } },
                            visualTransformation = if(passwordVisibility) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            }
                        )

                        //CONTRASEÑA CONFIRMACION
                        TextFieldData(
                            modifier = Modifier.fillMaxWidth(),
                            textFieldValue = passwordCnofirm,
                            textLabel = "Confirmar contraseña",
                            txColor = Color.Black,
                            maxChar = 25,
                            enabled = enabledInput.value,
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.None,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            imeAction = ImeAction.Done,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        passwordConfirmVisibility = !passwordConfirmVisibility
                                    }) {
                                    Icon(
                                        imageVector = if(passwordConfirmVisibility) {
                                            Icons.Default.Visibility
                                        } else {
                                            Icons.Default.VisibilityOff },
                                        contentDescription = ""
                                    )
                                } },
                            visualTransformation = if(passwordConfirmVisibility) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            }
                        )

                        //CORREO
                        TextFieldData(
                            modifier = Modifier.fillMaxWidth(),
                            textFieldValue = email,
                            textLabel = "Correo electrónico",
                            txColor = MaterialTheme.colorScheme.primary,
                            maxChar = 80,
                            enabled = enabledInput.value,
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.None,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                },
                                onDone = {
                                    keyboardController!!.hide()
                                }
                            ),
                            imeAction = ImeAction.Next
                        )

                        //TELEFONO
                        TextFieldData(
                            modifier = Modifier.fillMaxWidth(),
                            textFieldValue = telefono,
                            textLabel = "Teléfono",
                            txColor = MaterialTheme.colorScheme.primary,
                            maxChar = 10,
                            enabled = enabledInput.value,
                            keyboardType = KeyboardType.Phone,
                            capitalization = KeyboardCapitalization.None,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                },
                                onDone = {
                                    keyboardController!!.hide()
                                }
                            ),
                            imeAction = ImeAction.Next
                        )
                    }//Column
                } //Row

                Spacer(modifier = Modifier.height(7.dp))

                Divider()

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(35.dp)
                        .fillMaxWidth()
                        .clickable {navC.navigate(Router.AVISO_PRIVACIDAD.route)},
                    textAlign = TextAlign.Center,
                    text = "Ver política de privacidad",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary)


                PermissionLocationTurned(locationPermission = locationPermission)
                PoliticaPrivacidadTurned(hasRead = hasReadPoliticaPrivacidad )

                RoundedButton(
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    text = "Crear cuenta",
                    fSize = 20.sp,
                    textColor = Color.White,
                    backColor = botonColor,
                    estatus = onProccesing ,
                    onClick = {
                        prepareCreateAccount.value = true
                    } //onClick
                )

                if(onProccesing.value) {
                    Text(text = "Iniciando sesión", style = MaterialTheme.typography.bodyLarge)
                }

                if(onProccesing.value) {
                    Text(text = "Iniciando sesión", style = MaterialTheme.typography.bodyLarge)
                }

            }//Column

            if(prepareCreateAccount.value) {
                var testInput = checkDataInputRegister(curp.value,
                    nombre.value, paterno.value,
                    sexo.value,
                    password.value,
                    passwordCnofirm.value,
                    locationPermission.hasPermission,
                    hasReadPoliticaPrivacidad.value)
                if(testInput.length > 0){
                    prepareCreateAccount.value = false
                    showSheetError.value = true
                    messageError.value = testInput
                }else {
                    if(ToolBox.testConectivity(context)){
                        prepareCreateAccount.value = false
                        textoBotonLogin.value = "Iniciando"
                        onProccesing.value = true
                        val jsonObj = JSONObject()
                        jsonObj.put("email", email.value)
                        jsonObj.put("password", password.value)
                        jsonObj.put("curp", curp.value)
                        jsonObj.put("nombre", nombre.value)
                        jsonObj.put("paterno", paterno.value)
                        jsonObj.put("materno", materno.value)
                        jsonObj.put("telefono", telefono.value)
                        jsonObj.put("fNacimiento", fNacimiento.value)
                        jsonObj.put("sexo", sexo.value)
                        registroViewModel.DoRegistroUser(context, jsonObj)
                        enabledInput.value = false

                    }else {
                        prepareCreateAccount.value = false
                        showSheetError.value = true
                        messageError.value = "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
                    }
                }//condicionm para validar credenciales no vacías
            }

            if (showSheetError.value) {
                BottomSheet(
                    text = messageError.value,
                    title = "Error al procesar los datos") {
                    showSheetError.value = false
                    onProccesing.value = false
                }
            }
        }//Surface
    } //Scaffold

    if(consultandoCurpData.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        curpData.value?.let {
            when(curpData.value){
                is RequestCurp.Succes -> {
                    val resultCurp = curpData.value as RequestCurp.Succes
                    Log.i("NAATS","SUCCES" )
                    consultandoCurpData.value = false
                    nombre.value = resultCurp.data.nombre
                    paterno.value = resultCurp.data.paterno
                    materno.value = resultCurp.data.materno
                    sexo.value = resultCurp.data.sexo
                    fNacimiento.value = resultCurp.data.fNacimiento
                } //Succes
                is RequestCurp.Error -> {
                    Log.i("NAATS","ERROR" )
                    onProccesing.value = false
                    val errorLogin = curpData.value as RequestCurp.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onProccesing.value = false
                    consultaCurpViewModel.resetCurp()
                    enabledInput.value = true
                }//Error
                else -> {
                    onProccesing.value = false
                    enabledInput.value = true
                }
            }//when
        }//observable let

    }//onProccesing


    if(onProccesing.value) {
        textoBotonLogin.value = "Creando cuenta"
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        registroSesionState.value?.let {
            when(registroSesionState.value){
                is RequestResponse.Succes -> {
                    Log.i("NAATS","SUCCES" )
                    onProccesing.value = false
                    registroViewModel.resetRegistro()
                    navC.navigate(Router.CREAR_DENUNCIA_VIEW.route) {
                        popUpTo(navC.graph.id) { inclusive = true }
                    }
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("NAATS","ERROR" )
                    onProccesing.value = false
                    val errorLogin = registroSesionState.value as RequestResponse.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onProccesing.value = false
                    registroViewModel.resetRegistro()
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

fun checkDataInputRegister(curpData: String,
                           nombre: String,
                           paterno: String,
                           sexo: String,
                           password: String,
                           confirm: String,
                           permisssionLocation: Boolean,
                           readPrivacy: Boolean): String{
    var valueReturn = ""
    if(curpData.isBlank() || password.isBlank()||paterno.isBlank()||nombre.isBlank() || sexo.isBlank())
        valueReturn = "Los datos son obligatirios, por favor capture los datos que faltan"
    else if(password != confirm)
        valueReturn = "La contraseña y su confirmación no coinciden"
    else if(!permisssionLocation)
        valueReturn = "Debe otorgar el permiso de localización"
    else if(!readPrivacy)
        valueReturn = "Es importante que indique que ha leido la política de privacidad"
    return valueReturn
}