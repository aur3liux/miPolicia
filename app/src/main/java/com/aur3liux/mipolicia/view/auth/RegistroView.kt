package com.aur3liux.mipolicia.view.auth


import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetError
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.components.TextFieldData
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.RegistroRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.dGradient1
import com.aur3liux.mipolicia.ui.theme.dGradient2
import com.aur3liux.mipolicia.ui.theme.lGradient1
import com.aur3liux.mipolicia.ui.theme.lGradient2
import com.aur3liux.mipolicia.viewmodel.RegistroVM
import com.aur3liux.mipolicia.viewmodel.RegistroVMFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.json.JSONObject

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegistroFinishView(navC: NavController) {
    val textoBotonLogin = remember{ mutableStateOf("Iniciar sesión") }

    //-- DATOS DE ENTRADA
    val fNacimiento = rememberSaveable{ mutableStateOf("") }
    val sexo = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordCnofirm = rememberSaveable{ mutableStateOf("") }
    val telefono = rememberSaveable{ mutableStateOf("") }
    val email = rememberSaveable{ mutableStateOf("") }
    val nombre = rememberSaveable{ mutableStateOf("") }
    val paterno = rememberSaveable{ mutableStateOf("") }
    val materno = rememberSaveable{ mutableStateOf("") }
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

    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val dbAuth = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val currentUser = dbAuth.userDao().getUserData()

    //viewmodel
    val registroViewModel: RegistroVM = viewModel(
        factory = RegistroVMFactory(registroRepository = RegistroRepo())
    )

    val registroSesionState = remember(registroViewModel) {registroViewModel.RegistroData}.observeAsState() //LiveData

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
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

        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp),
                text = "Ingresa tus datos",
                fontSize = 14.sp,
                fontFamily = ToolBox.montseFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            //CORREO
            TextFieldData(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
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

            //CONTRASEÑA
            TextFieldData(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
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


            //NOMBRE
            TextFieldData(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                textFieldValue = nombre,
                textLabel = "Nombre",
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

            //PATERNO
            TextFieldData(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                textFieldValue = paterno,
                textLabel = "Paterno",
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


            //MATERNO
            TextFieldData(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                textFieldValue = materno,
                textLabel = "Materno",
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
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

            Spacer(modifier = Modifier.height(7.dp))

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Crear cuenta",
                fSize = 15.sp,
                textColor = Color.White,
                backColor = botonColor,
                estatus = onProccesing ,
                onClick = {
                    prepareCreateAccount.value = true
                } //onClick
            )

            if(onProccesing.value) {
                Text(text = "Creando cuenta", style = MaterialTheme.typography.bodyLarge)
            }
        }//Column

        if(prepareCreateAccount.value) {
            var testInput = checkDataInputRegister(
                password.value,
                passwordCnofirm.value,
                email.value,
                telefono.value,
                locationPermission.hasPermission)
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
                    jsonObj.put("curp", currentUser.curp)
                    jsonObj.put("nombre", currentUser.nombre)
                    jsonObj.put("paterno", currentUser.paterno)
                    jsonObj.put("materno", currentUser.materno)
                    jsonObj.put("sexo", currentUser.sexo)
                    jsonObj.put("telefono", telefono.value)
                    jsonObj.put("pushNotificationChannel", dbAuth.tokenPushDao().getTokenPushNotif())
                    jsonObj.put("ocupacion", "Pendiente")
                    jsonObj.put("nacionalidad", "Pendiente")
                    jsonObj.put("escolaridad", "Pendiente")
                    jsonObj.put("telefonoCasa", "Pendiente")

                    registroViewModel.DoRegistroUser(context, jsonObj)
                    enabledInput.value = false

                }else {
                    prepareCreateAccount.value = false
                    showSheetError.value = true
                    messageError.value = "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
                }

            }//condicion para validar credenciales no vacías
        }

        if (showSheetError.value) {
            BottomSheetError(
                text = messageError.value,
                title = "Error al procesar los datos") {
                showSheetError.value = false
                onProccesing.value = false
            }
        }
    } //Scaffold

    if(onProccesing.value) {
        textoBotonLogin.value = "Creando cuenta"
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        registroSesionState.value?.let {
            when(registroSesionState.value){
                is RequestResponse.Succes -> {
                    Log.i("NAATS","SUCCES" )
                    onProccesing.value = false
                    registroViewModel.resetRegistro()
                    navC.navigate(Router.PANTALLA_INICIAL.route) {
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
                    dbAuth.userDao().deleteAllUser()
                    navC.popBackStack()
                }//Error
                else -> {
                    onProccesing.value = false
                    enabledInput.value = true
                }
            }//when
        }//observable let
    }//onProccesing
}

fun checkDataInputRegister(password: String,
                           confirm: String,
                           correo: String,
                           telefono: String,
                           permisssionLocation: Boolean): String{
    var valueReturn = ""
    if(password.isBlank())
        valueReturn = "La contraseña es obligatoria"
    else if(password != confirm)
        valueReturn = "La contraseña y su confirmación no coinciden"
    else if(correo.isBlank() || telefono.isBlank())
        valueReturn = "Todos los datos son obligatorios"
    else if(!permisssionLocation)
        valueReturn = "Debe otorgar el permiso de localización"
    else if(!ToolBox.isEmailValid(correo))
        valueReturn = "El correo electrónico no es válido"
    else if(!ToolBox.isPhoneNumberValid(telefono))
        valueReturn = "El número de teléfono no es válido"
    return valueReturn
}
