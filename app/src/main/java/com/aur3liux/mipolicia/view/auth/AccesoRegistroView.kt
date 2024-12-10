package com.aur3liux.mipolicia.view.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
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
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetError
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.components.TextFieldData
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.lGradient1

import com.google.accompanist.permissions.ExperimentalPermissionsApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AccesoRegistroView(navC: NavController) {
    val textoBotonLogin = remember{ mutableStateOf("Iniciar sesión") }

    //-- DATOS DE ENTRADA
    val email = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val curp = rememberSaveable{ mutableStateOf("") }
    val nombre = rememberSaveable{ mutableStateOf("") }
    val paterno = rememberSaveable{ mutableStateOf("") }
    val materno = rememberSaveable{ mutableStateOf("") }
    val fNacimiento = rememberSaveable{ mutableStateOf("") }
    val sexo = rememberSaveable{ mutableStateOf("") }
    val context = LocalContext.current

    //---------
    val focusManager = LocalFocusManager.current
    var passwordVisibility by remember { mutableStateOf(false) }
    val confirmData = remember{ mutableStateOf(false) }
    val consultandoCurpData = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val enabledInput = remember { mutableStateOf(true) }
    val messageError = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    /*
    val consultaCurpViewModel: ConsultaCurpVM = viewModel(
        factory = ConsultaCurpVMFactory(curpRepository = CurpRepo()))
    val curpData = remember(consultaCurpViewModel) {consultaCurpViewModel.CurpData}.observeAsState() //LiveData

*/
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
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {


                Image(
                    painter = painterResource(R.drawable.logo_mp),
                    contentDescription = "Circle Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                )

                TextFieldData(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    textFieldValue = email,
                    textLabel = "Correo electrónico",
                    txColor = MaterialTheme.colorScheme.primary,
                    maxChar = 80,
                    enabled = enabledInput.value,
                    textPlaceHolder = "correo@server.com",
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

                Spacer(modifier = Modifier.height(5.dp))

                    TextFieldData(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        textFieldValue = curp,
                        textLabel = "CURP",
                        txColor = MaterialTheme.colorScheme.primary,
                        maxChar = 18,
                        enabled = enabledInput.value,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters,
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


                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                        text = "${nombre.value}",
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                        text = "${paterno.value} ${materno.value}",
                        fontSize = 15.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                        text = "${sexo.value}",
                        fontSize = 15.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )


                Spacer(modifier = Modifier.height(7.dp))
                if(!confirmData.value){
                    RoundedButton(
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 10.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        text = "Verificar datos",
                        fSize = 15.sp,
                        textColor = Color.White,
                        backColor = botonColor,
                        estatus = onProccesing ,
                        onClick = {
                            if(curp.value.isBlank() || !ToolBox.isValidCurp(curp.value)) {
                                messageError.value = "La CURP no es válida, por favor ingrese una CURP con el formato adecuado, puede tomarla de su INE"
                                showSheetError.value = true
                            }else {
                                enabledInput.value = false
                                onProccesing.value = true
                                //consultaCurpViewModel.DoConsultaCurp(context, curp.value)
                                consultandoCurpData.value = true
                            }
                        } //onClick
                    )
                }else {
                    RoundedButton(
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 10.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        text = "Aceptar y continuar",
                        fSize = 15.sp,
                        textColor = Color.White,
                        backColor = botonColor,
                        estatus = consultandoCurpData ,
                        onClick = {
                            navC.navigate(Router.FINISH_REGISTRO.route)
                        } //onClick
                    )
                }
            }//Column


            if (showSheetError.value) {
                BottomSheetError(
                    text = messageError.value,
                    title = "Error al procesar los datos") {
                    showSheetError.value = false
                    onProccesing.value = false
                }
            }
    } //Scaffold
/*
    if(consultandoCurpData.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        curpData.value?.let {
            when(curpData.value){
                is RequestCurp.Succes -> {
                    val resultCurp = curpData.value as RequestCurp.Succes
                    Log.i("Mi policia","SUCCES" )
                    consultandoCurpData.value = false
                    nombre.value = resultCurp.data.nombre
                    paterno.value = resultCurp.data.paterno
                    materno.value = resultCurp.data.materno
                    sexo.value = resultCurp.data.sexo
                    fNacimiento.value = resultCurp.data.fNacimiento
                    confirmData.value = true
                    consultaCurpViewModel.resetCurp()
                } //Succes
                is RequestCurp.Error -> {
                    Log.i("Mi policia","ERROR" )
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

 */
}
