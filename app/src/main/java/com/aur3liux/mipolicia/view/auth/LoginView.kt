package com.aur3liux.mipolicia.view.auth

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
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
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.components.TextFieldData
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LoginRepo
import com.aur3liux.mipolicia.services.RenewRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.lGradient2
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.ui.theme.textShapePrincipalColor
import com.aur3liux.mipolicia.view.bottomsheets.BottomRecuperarPassword
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetMenu
import com.aur3liux.mipolicia.view.dialogs.ErrorDialog
import com.aur3liux.mipolicia.view.getNotificationToken
import com.aur3liux.mipolicia.viewmodel.LoginVM
import com.aur3liux.mipolicia.viewmodel.LoginVMFactory
import com.aur3liux.mipolicia.viewmodel.RenewVM
import com.aur3liux.mipolicia.viewmodel.RenewVMFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import org.json.JSONObject

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navC: NavController){

    val textoBotonLogin = remember{ mutableStateOf("Iniciar sesión") }

    //-- DATOS DE ENTRADA
    val email = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val getNewPassword = rememberSaveable{ mutableStateOf(false) }
    val context = LocalContext.current
    val localUriHandler = LocalUriHandler.current

    //---------
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val prepareLogin = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }

    val prepareNewPassword = remember{ mutableStateOf(false) }
    val onProccessingNewPassword = remember{ mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val enabledInput = remember { mutableStateOf(true) }
    val messageError = remember { mutableStateOf("") }

    val info = buildAnnotatedString {
        append(messageError.value)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val dbAuth = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    if(dbAuth.tokenPushDao().getTokenPushNotif() == null)
        getNotificationToken(dbAuth)

    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val permissionTest = remember{ mutableStateOf(false) }
    val delayed = remember{ mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        permissionTest.value = locationPermission.hasPermission
        delay(1000)
        delayed.value = true
    } //LaunchEffect

    //viewmodel Login
    val loginViewModel: LoginVM = viewModel(
        factory = LoginVMFactory(loginRepository = LoginRepo())
    )

    val userSesionState = remember(loginViewModel) {loginViewModel.UserData}.observeAsState() //LiveData

    //Viewmodel Renew
    val renewViewModel: RenewVM = viewModel(
        factory = RenewVMFactory(loginRepository = RenewRepo())
    )

    val renewState = remember(renewViewModel) {renewViewModel.RenewData}.observeAsState() //LiveData

    //Column principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(shapePrincipalColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Image(
            modifier = Modifier
                .background(lGradient2)
                .padding(top = 20.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.banner_feet),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(60.dp))

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
                color = textShapePrincipalColor,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "CAMPECHE",
                fontSize = 14.sp,
                letterSpacing = 0.2.sp,
                fontFamily = ToolBox.gmxFontRegular,
                color = textShapePrincipalColor,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        } //Column encabezado titulos


        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(15.dp)
        ) {
            //Column formulario
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!delayed.value || onProccesing.value || onProccessingNewPassword.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.Transparent),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        strokeWidth = 4.dp
                    )
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = "Cargando",
                        fontSize = 10.sp,
                        fontFamily = ToolBox.montseFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                } else {

                    if (locationPermission.hasPermission) {
                        Text(
                            modifier = Modifier.padding(bottom = 15.dp),
                            text = "Bienvenid@",
                            fontSize = 19.sp,
                            fontFamily = ToolBox.montseFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        //CORREO ELECTRONICO
                        TextFieldData(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            textFieldValue = email,
                            textLabel = "correo electrónico",
                            txColor = MaterialTheme.colorScheme.primary,
                            maxChar = 80,
                            enabled = enabledInput.value,
                            textPlaceHolder = "correo@server.com",
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
                            modifier = Modifier.fillMaxWidth(0.9f),
                            textFieldValue = password,
                            textLabel = "Contraseña",
                            txColor = MaterialTheme.colorScheme.primary,
                            maxChar = 25,
                            enabled = enabledInput.value,
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.None,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    prepareLogin.value = true
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

                        Spacer(modifier = Modifier.height(15.dp))

                        RoundedButton(
                            modifier = Modifier
                                .padding(horizontal = 30.dp, vertical = 10.dp)
                                .fillMaxWidth()
                                .height(40.dp),
                            text = "Iniciar sesión",
                            fSize = 15.sp,
                            shape = RoundedCornerShape(8.dp),
                            textColor = Color.White,
                            backColor = MaterialTheme.colorScheme.surface,
                            estatus = onProccesing,
                            onClick = {
                                prepareLogin.value = true
                            } //onClick
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .height(40.dp)
                                    .clickable {
                                        getNewPassword.value = true
                                    }
                                    .height(40.dp)
                                    .wrapContentHeight(align = Alignment.CenterVertically),
                                text = "Recuperar cuenta",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                            Text(
                                modifier = Modifier
                                    .height(40.dp)
                                    .height(40.dp)
                                    .wrapContentHeight(align = Alignment.CenterVertically),
                                text = "  /  ",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                modifier = Modifier
                                    .height(40.dp)
                                    .clickable {
                                        navC.navigate(Router.REGISTRO_VIEW.route)
                                    }
                                    .height(40.dp)
                                    .wrapContentHeight(align = Alignment.CenterVertically),
                                text = "Crear cuenta",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                        } //ROW RECUPERAR CONTRASEÑA

                        Spacer(modifier = Modifier.height(30.dp))

                        Text(
                            modifier = Modifier
                                .clickable {
                                    localUriHandler.openUri("http://www.segobcampeche.gob.mx/index.php/aviso-de-privacidad")
                                },
                            text = "Ver política de privacidad",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(70.dp))
                        Text(
                            modifier = Modifier
                                .height(40.dp)
                                .height(40.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            text = "Secretaría de Protección y Seguridad Ciudadana",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )

                    } else {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = Icons.Filled.LocationOff,
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = "Detectamos que no proporcionaste el permiso de UBICACIÓN y es indispensable para el correcto funcionamiento de la aplicación.",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Justify,
                            fontFamily = ToolBox.montseFont,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                                .clickable {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data =
                                                Uri.fromParts("package", context.packageName, null)
                                        }
                                    context.startActivity(intent)
                                },
                            text = "Configurar permisos",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 17.sp,
                            fontFamily = ToolBox.montseFont,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }//Check permiso localizacion
            }//Column formulario
        }//Card

        if (prepareNewPassword.value) {
            if (email.value.isBlank() || !ToolBox.isEmailValid(email.value)) {
                prepareNewPassword.value = false
                showSheetError.value = true
                messageError.value = "El correo electrónico no es válido para recuperar la contraseña"
            } else {
                if (ToolBox.testConectivity(context)) {
                    prepareNewPassword.value = false
                    textoBotonLogin.value = "Iniciando"
                    onProccessingNewPassword.value = true
                    val jsonObj = JSONObject()
                    jsonObj.put("email", email.value)
                    renewViewModel.DoRenew(context, jsonObj)
                    enabledInput.value = false
                } else {
                    prepareNewPassword.value = false
                    showSheetError.value = true
                    messageError.value =
                        "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
                }
            }//condicionm para validar credenciales no vacías
        }

        if (prepareLogin.value) {
            var testInput = checkDataInputLogin(
                email.value,
                password.value,
                locationPermission.hasPermission
            )
            if (testInput.length > 0) {
                prepareLogin.value = false
                showSheetError.value = true
                messageError.value = testInput
            } else {
                if (ToolBox.testConectivity(context)) {
                    var d = "${Build.MODEL}  ${Build.MANUFACTURER}"
                    prepareLogin.value = false
                    textoBotonLogin.value = "Iniciando"
                    onProccesing.value = true
                    val jsonObj = JSONObject()
                    jsonObj.put("email", email.value)
                    jsonObj.put("password", password.value)
                    jsonObj.put("device", d)
                    jsonObj.put("notificationUserToken", dbAuth.tokenPushDao().getTokenPushNotif())
                    loginViewModel.DoLoginUser(context, jsonObj)
                    enabledInput.value = false
                } else {
                    prepareLogin.value = false
                    showSheetError.value = true
                    messageError.value =
                        "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
                }
            }//condicionm para validar credenciales no vacías
        }

        if (showSheetError.value) {
            ErrorDialog(
                title = "Error de inicio de sesión",
                info = info,
                context = context, onConfirmation = {
                    showSheetError.value = false
                    onProccesing.value = false
                }
            )
        }

        //SE EJECUTA EL INICIO DE SESION
        if (onProccesing.value) {
            textoBotonLogin.value = "Iniciar sesión"
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            userSesionState.value?.let {
                when (userSesionState.value) {
                    is RequestResponse.Succes -> {
                        Log.i("Mi policia", "SUCCES")
                        onProccesing.value = false
                        navC.navigate(Router.HOME.route) {
                            popUpTo(navC.graph.id) { inclusive = true }
                        }
                    } //Succes
                    is RequestResponse.Error -> {
                        Log.i("Mi policia", "ERROR")
                        onProccesing.value = false
                        val errorLogin = userSesionState.value as RequestResponse.Error
                        messageError.value = "${errorLogin.errorMessage}"
                        showSheetError.value = true
                        onProccesing.value = false
                        loginViewModel.resetLogin()
                        enabledInput.value = true
                    }//Error
                    else -> {
                        onProccesing.value = false
                        enabledInput.value = true
                    }
                }//when
            }//observable let
        }//onProccesing

        //SE EJECUTA LA PETICION PARA RECUPERAR CONTRASEÑA
        if (onProccessingNewPassword.value) {
           // textoBotonLogin.value = "Iniciar sesión"
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            renewState.value?.let {
                when (renewState.value) {
                    is RequestResponse.Succes -> {
                        Log.i("Mi policia", "SUCCES")
                        onProccessingNewPassword.value = false
                        Toast.makeText(context, "Revisa la bandeja de tu correo electrónico y sigue las indicaciones", Toast.LENGTH_SHORT).show()
                        enabledInput.value = true
                    } //Succes
                    is RequestResponse.Error -> {
                        Log.i("Mi policia", "ERROR")
                        onProccessingNewPassword.value = false
                        val errorLogin = renewState.value as RequestResponse.Error
                        messageError.value = "${errorLogin.errorMessage}"
                        showSheetError.value = true
                        onProccessingNewPassword.value = false
                        renewViewModel.resetRenew()
                        enabledInput.value = true
                    }//Error
                    else -> {
                        onProccessingNewPassword.value = false
                        enabledInput.value = true
                    }
                }//when
            }//observable let
        }//onProccesingNewPassword
    } //Column principal


    if(getNewPassword.value){
        BottomRecuperarPassword(
            email = email,
            onGetNewPassword = {
                prepareNewPassword.value = true
            },
            onDismiss = {
                getNewPassword.value = false
            })
    }
} //Componible

fun checkDataInputLogin(curpData: String,
                        password: String,
                        permisssionLocation: Boolean): String{
    var valueReturn = ""
    if(curpData.isBlank() || password.isBlank())
        valueReturn = "No deje las credenciales de acceso vacías"
    else if(!permisssionLocation)
        valueReturn = "Debe otorgar el permiso de localización"
    return valueReturn
}