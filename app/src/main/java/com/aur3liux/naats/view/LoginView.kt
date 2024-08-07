package com.aur3liux.naats.view

import android.Manifest
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import com.aur3liux.naats.R
import com.aur3liux.naats.Router
import com.aur3liux.naats.ToolBox
import com.aur3liux.naats.components.BottomSheet
import com.aur3liux.naats.components.PermissionLocationTurned
import com.aur3liux.naats.components.PoliticaPrivacidadTurned
import com.aur3liux.naats.components.RoundedButton
import com.aur3liux.naats.components.TextFieldData
import com.aur3liux.naats.model.RequestResponse
import com.aur3liux.naats.services.LoginRepo
import com.aur3liux.naats.ui.theme.botonColor
import com.aur3liux.naats.ui.theme.dGradient1
import com.aur3liux.naats.ui.theme.dGradient2
import com.aur3liux.naats.ui.theme.lGradient1
import com.aur3liux.naats.ui.theme.lGradient2
import com.aur3liux.naats.viewmodel.LoginVM
import com.aur3liux.naats.viewmodel.LoginVMFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.json.JSONObject

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navC: NavController){

    val textoBotonLogin = remember{ mutableStateOf("Iniciar sesión") }



    //-- DATOS DE ENTRADA
    val curp = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val context = LocalContext.current

    //---------
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val prepareLogin = remember{ mutableStateOf(false) }
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
    val loginViewModel: LoginVM = viewModel(
        factory = LoginVMFactory(loginRepository = LoginRepo())
    )

    val userSesionState = remember(loginViewModel) {loginViewModel.UserData}.observeAsState() //LiveData

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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

            Image(
                modifier = Modifier
                    .background(lGradient2)
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                painter = painterResource(id = R.drawable.logo_h),
                contentDescription = "background",
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "Bienvenid@",
                fontSize = 25.sp,
                fontFamily = ToolBox.gmxFontRegular,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )


            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = "Ingresa tus credenciales de acceso",
                fontSize = 14.sp,
                fontFamily = ToolBox.montseFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Box(modifier = Modifier
                .imePadding()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)){

                    TextFieldData(
                        modifier = Modifier.fillMaxWidth(),
                        textFieldValue = curp,
                        textLabel = "CURP",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 80,
                        enabled = enabledInput.value,
                       // trailingIcon = { Icon(Icons.Filled.People, contentDescription = "") },
                        keyboardType = KeyboardType.Text,
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
                }//Column
            } //Row

            Spacer(modifier = Modifier.height(5.dp))

            RoundedButton(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                text = "Iniciar sesión",
                fSize = 15.sp,
                textColor = Color.White,
                backColor = botonColor,
                estatus = onProccesing ,
                onClick = {
                    prepareLogin.value = true
                } //onClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier
                .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(35.dp)
                        .weight(0.5f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { navC.navigate(Router.ACCESO_REGISTRO.route)  }) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                        contentAlignment = Alignment.Center){
                        Text(
                            text = "Olvidé mi contraseña",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.tertiary)
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(35.dp)
                        .weight(0.5f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { navC.navigate(Router.ACCESO_REGISTRO.route)  }) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                        contentAlignment = Alignment.Center){
                        Text(
                            text = "Crear cuenta nueva",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.tertiary)
                    }
                }

            } //ROW de botones contraseña - crear cuenta


            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(35.dp)
                    .fillMaxWidth()
                    .clickable { navC.navigate(Router.AVISO_PRIVACIDAD.route) },
                textAlign = TextAlign.Center,
                text = "Ver política de privacidad",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary)

            
            PermissionLocationTurned(locationPermission = locationPermission)
            PoliticaPrivacidadTurned(hasRead = hasReadPoliticaPrivacidad )

            if(onProccesing.value) {
                Text(text = "Iniciando sesión", style = MaterialTheme.typography.bodyLarge)
            }

        }//Column

        if(prepareLogin.value) {
            var testInput = checkDataInputLogin(curp.value,
                password.value,
                locationPermission.hasPermission,
                hasReadPoliticaPrivacidad.value)
            if(testInput.length > 0){
                    prepareLogin.value = false
                    showSheetError.value = true
                    messageError.value = testInput
            }else {
                if(ToolBox.testConectivity(context)){
                    prepareLogin.value = false
                    textoBotonLogin.value = "Iniciando"
                    onProccesing.value = true
                    val jsonObj = JSONObject()
                    jsonObj.put("email", curp.value)
                    jsonObj.put("password", password.value)
                    loginViewModel.DoLoginUser(context, jsonObj)
                    enabledInput.value = false
                }else {
                    prepareLogin.value = false
                    showSheetError.value = true
                    messageError.value = "No tienes acceso a internet, conéctate a una red y vuelve a intentarlo"
                }
            }//condicionm para validar credenciales no vacías

        }

        if (showSheetError.value) {
            BottomSheet(
                text = messageError.value,
                title = "Error de inicio de sesión") {
                showSheetError.value = false
                onProccesing.value = false
            }
        }
    }//Surface

    if(onProccesing.value) {
        textoBotonLogin.value = "Iniciar sesión"
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        userSesionState.value?.let {
            when(userSesionState.value){
                is RequestResponse.Succes -> {
                    Log.i("NAATS","SUCCES" )
                    onProccesing.value = false
                    navC.navigate(Router.HOME.route) {
                        popUpTo(navC.graph.id) { inclusive = true }
                    }
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("NAATS","ERROR" )
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
}

fun checkDataInputLogin(curpData: String,
                        password: String,
                        permisssionLocation: Boolean,
                        readPrivacy: Boolean): String{
    var valueReturn = ""
    if(curpData.isBlank() || password.isBlank())
        valueReturn = "No deje datos vacíos"
    else if(!permisssionLocation)
        valueReturn = "Debe otorgar el permiso de localización"
    else if(!readPrivacy)
        valueReturn = "Es importante que indique que ha leido la política de privacidad"
    return valueReturn
}