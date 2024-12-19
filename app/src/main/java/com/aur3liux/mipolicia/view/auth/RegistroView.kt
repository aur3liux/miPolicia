package com.aur3liux.mipolicia.view.auth


import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.buildAnnotatedString
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
import androidx.room.Room
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.components.TextFieldData
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.RegistroRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.viewmodel.RegistroVM
import com.aur3liux.mipolicia.viewmodel.RegistroVMFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.json.JSONObject

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegistroView(navC: NavController) {
    val textoBotonLogin = remember{ mutableStateOf("Iniciar sesión") }

    //-- DATOS DE ENTRADA
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordConfirm = rememberSaveable{ mutableStateOf("") }
    val telefono = rememberSaveable{ mutableStateOf("") }
    val email = rememberSaveable{ mutableStateOf("") }
    val nombre = rememberSaveable{ mutableStateOf("") }
    val apellidos = rememberSaveable{ mutableStateOf("") }
    val cPostal = rememberSaveable{ mutableStateOf("") }
    val municipio = rememberSaveable{ mutableStateOf("") }
    val colonia = rememberSaveable{ mutableStateOf("") }
    val calleNumero = rememberSaveable{ mutableStateOf("") }
    val context = LocalContext.current

    //---------
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordConfirmVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val prepareCreateAccount = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val enabledInput = remember { mutableStateOf(true) }
    val messageError = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val info = buildAnnotatedString {
        append(messageError.value)
    }

    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val dbAuth = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val hasRead = remember{ mutableStateOf(false) }

    //viewmodel
    val registroViewModel: RegistroVM = viewModel(
        factory = RegistroVMFactory(registroRepository = RegistroRepo())
    )

    val registroSesionState = remember(registroViewModel) {registroViewModel.RegistroData}.observeAsState() //LiveData

    Scaffold(contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi policía",
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
                        contentDescription = "", tint = Color.White)})
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Box(modifier = Modifier
                .background(shapePrincipalColor)
                .fillMaxWidth()
                .height(60.dp), contentAlignment = Alignment.Center){
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Ingresa tus datos",
                    fontSize = 17.sp,
                    letterSpacing = 0.3.sp,
                    fontFamily = ToolBox.gmxFontRegular,
                    color = textShapePrincipalColor,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //NOMBRE
            TextFieldData(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = nombre,
                textLabel = "Nombre *",
                txColor = MaterialTheme.colorScheme.primary,
                maxChar = 50,
                enabled = enabledInput.value,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
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

            //APELLIDOS
            TextFieldData(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = apellidos,
                textLabel = "Apellidos *",
                txColor = MaterialTheme.colorScheme.primary,
                maxChar = 50,
                enabled = enabledInput.value,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
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

            Row {
                //TELEFONO
                TextFieldData(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(start = 20.dp, end = 5.dp),
                    textFieldValue = telefono,
                    textLabel = "Teléfono *",
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

                //C Postal
                TextFieldData(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(start = 5.dp, end = 20.dp),
                    textFieldValue = cPostal,
                    textLabel = "C.Postal",
                    txColor = MaterialTheme.colorScheme.primary,
                    maxChar = 5,
                    enabled = enabledInput.value,
                    keyboardType = KeyboardType.Number,
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
            } //Row

            Spacer(modifier = Modifier.height(7.dp))

            //CORREO
            TextFieldData(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = email,
                textLabel = "Correo electrónico*",
                txColor = MaterialTheme.colorScheme.primary,
                maxChar = 60,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = password,
                textLabel = "Contraseña*",
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
                            imageVector = if (passwordVisibility) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = if(password.value.length > 0) checkPassword(password.value) else "Ingrese contraseña",
                fontSize = 10.sp,
                fontFamily = ToolBox.montseFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Light
            )

            //CONTRASEÑA CONFIRMACION
            TextFieldData(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = passwordConfirm,
                textLabel = "Confirmar contraseña*",
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
                            imageVector = if (passwordConfirmVisibility) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = if (passwordConfirmVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )


            Spacer(modifier = Modifier.height(20.dp))

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Crear cuenta",
                fSize = 15.sp,
                textColor = Color.White,
                backColor = botonColor,
                shape =   RoundedCornerShape(15.dp),
                estatus = onProccesing,
                onClick = {
                    if (hasRead.value)
                        prepareCreateAccount.value = true
                    else {
                        showSheetError.value = true
                        messageError.value = "Debe aceptar la política de privacidad"
                    }
                } //onClick
            )

            //AVISO DE PRIVACIDAD CHECK
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp)
                    .clickable {
                        hasRead.value = !hasRead.value
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Checkbox(
                    modifier = Modifier.size(20.dp),
                    checked = hasRead.value,
                    onCheckedChange = {
                        hasRead.value = !hasRead.value
                    },
                    colors = CheckboxDefaults.colors()
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "He leido y acepto la ",
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            //navC.navigate(Router.AVISO_PRIVACIDAD.route)
                        },
                    text = "política de privacidad",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = ToolBox.quatroSlabFont,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )

            } //Check politica privacidad

            Spacer(modifier = Modifier.height(60.dp))

            if (onProccesing.value) {
                Text(text = "Creando cuenta", style = MaterialTheme.typography.bodyLarge)
            }

        }//Column

        if(prepareCreateAccount.value) {
            var testInput = checkDataInputRegister(
                nombre.value,
                apellidos.value,
                password.value,
                passwordConfirm.value,
                telefono.value,
                email.value,
                locationPermission.hasPermission)
            if(testInput.length > 0){
                prepareCreateAccount.value = false
                showSheetError.value = true
                messageError.value = testInput
            }else {

                if(ToolBox.testConectivity(context)) {
                    prepareCreateAccount.value = false
                    textoBotonLogin.value = "Iniciando"
                    onProccesing.value = true
                    val jsonObj = JSONObject()
                    jsonObj.put("name", nombre.value)
                    jsonObj.put("lastname", apellidos.value)
                    jsonObj.put("phone", telefono.value)
                    jsonObj.put("address", "")//Municpio
                    jsonObj.put("address2", "")//Colonia
                    jsonObj.put("city", "")//Ciudad
                    jsonObj.put("postal_code", cPostal.value)
                    jsonObj.put("country", "MEXICO")
                    jsonObj.put("email", email.value)
                    jsonObj.put("password", password.value)
                    jsonObj.put("device", Build.MODEL)
                    jsonObj.put("notificationUserToken", dbAuth.tokenPushDao().getTokenPushNotif())
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
            ErrorDialog(
                title = "Error de registro",
                info = info,
                context = context, onConfirmation = {
                    showSheetError.value = false
                    onProccesing.value = false
                }
            )
        }
    } //Scaffold

    if(onProccesing.value) {
        textoBotonLogin.value = "Creando cuenta"
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        registroSesionState.value?.let {
            when(registroSesionState.value){
                is RequestResponse.Succes -> {
                    Log.i("MI POLICIA","SUCCES" )
                    onProccesing.value = false
                    registroViewModel.resetRegistro()
                    navC.navigate(Router.HOME.route) {
                        popUpTo(navC.graph.id) { inclusive = true }
                    }
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("MI POLICIA","ERROR" )
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

fun checkPassword(password: String): String{
    var valueReturn = ""
    when(ToolBox.validatePassword(password)){
        1 -> valueReturn = "Ingrese al menos un dígito"
        2 -> valueReturn = "Ingrese al menos una minúscula"
        3 -> valueReturn = "Ingrese al menos una mayúscula"
        4 -> valueReturn = "Ingrese al menos un caracter especial $#!?&."
        5 -> valueReturn = "No se aceptan espacios en blanco"
        6 -> valueReturn = "Mínimo 8 caracteres y máximo 25"
        else -> valueReturn = "Contraseña aceptable"
    }

    return valueReturn
}

fun checkDataInputRegister(
    nombre: String,
    apellidos: String,
    password: String,
    confirm: String,
    telefono: String,
    correo: String,
    permisssionLocation: Boolean): String{
    var valueReturn = ""
    if(nombre.isBlank() || apellidos.isBlank())
        valueReturn = "Nombre y apellidos son obligatorios."
    if(password.isBlank())
        valueReturn = "La contraseña es obligatoria"
    else if(password != confirm)
        valueReturn = "La contraseña y su confirmación no coinciden."
    else if(correo.isBlank() || telefono.isBlank())
        valueReturn = "El correo electrónico y el teléfono son obligatorios"
    else if(!permisssionLocation)
        valueReturn = "Debe otorgar el permiso de localización."
    else if(!ToolBox.isEmailValid(correo))
        valueReturn = "El correo electrónico no es válido."
    else if(!ToolBox.isPhoneNumberValid(telefono))
        valueReturn = "El número de teléfono no es válido."
    else {
        when(ToolBox.validatePassword(password)){
            1 -> valueReturn = "La contrasea debe tener al menos 1 dígito."
            2 -> valueReturn = "La contraseña debe tener al menos 1 minúscula."
            3 -> valueReturn = "La contraseña debe tener al menos 1 mayúscula."
            4 -> valueReturn = "La contraseña debe tener al menos 1 caracter especial."
            5 -> valueReturn = "No se aceptan espacios en blanco en la contraseña"
            6 -> valueReturn = "La contraseña debe tener al menos 8 caracteres y máximo 25"
            else -> valueReturn = ""
        }
    }
    return valueReturn
}