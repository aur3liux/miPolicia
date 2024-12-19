package com.aur3liux.mipolicia.view.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.view.bottomsheets.BottomSheetError
import com.aur3liux.mipolicia.components.RoundedButton
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LoginRepo
import com.aur3liux.mipolicia.ui.theme.botonColor
import com.aur3liux.mipolicia.ui.theme.dGradient1
import com.aur3liux.mipolicia.ui.theme.dGradient2
import com.aur3liux.mipolicia.ui.theme.lGradient1
import com.aur3liux.mipolicia.ui.theme.lGradient2
import com.aur3liux.mipolicia.viewmodel.LoginVM
import com.aur3liux.mipolicia.viewmodel.LoginVMFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CloseSessionConfirmView(navC: NavController){

    val textoBotonLogin = remember{ mutableStateOf("Cerrar sesión") }
    val context = LocalContext.current

    val prepareCloseSession = remember{ mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }

    var darkTheme = isSystemInDarkTheme()
    val color1 = if(darkTheme) lGradient1 else dGradient1
    val color2 = if(darkTheme) lGradient2 else dGradient2

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }


    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val dataUser = db.userDao().getUserData()

    //viewmodel
    val loginViewModel: LoginVM = viewModel(
        factory = LoginVMFactory(loginRepository = LoginRepo())
    )

    val userSesionState = remember(loginViewModel) {loginViewModel.UserData}.observeAsState() //LiveData

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = color1,
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                navC.popBackStack()
                            },
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
                })
        }
    ) {
        Column(modifier = Modifier
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

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
                contentAlignment = Alignment.Center) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .imePadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier.size(60.dp),
                                painter = painterResource(id = R.drawable.ic_atencion),
                                contentDescription = "background",
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                text = "Confirme el cierre de la sesión",
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            text = "Se perderán los datos de la sesión actual ",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        RoundedButton(
                            modifier = Modifier
                                .padding(horizontal = 30.dp, vertical = 20.dp)
                                .fillMaxWidth()
                                .height(70.dp),
                            text = textoBotonLogin.value,
                            fSize = 20.sp,
                            textColor = Color.White,
                            backColor = botonColor,
                            shape =   RoundedCornerShape(15.dp),
                            estatus = onProccesing,
                            onClick = {
                                prepareCloseSession.value = true
                            } //onClick
                        )

                        Spacer(modifier = Modifier.height(100.dp))

                        if (onProccesing.value) {
                            Text(
                                text = "Cerrando sesión",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }//Column 1
                } //Card

                if (showSheetError.value) {
                    BottomSheetError(
                        text = messageError.value,
                        title = "Error de cierre de sesión"
                    ) {
                        showSheetError.value = false
                        onProccesing.value = false
                    }
                }
            }//Box
        }//Column
    } //scaffold

    if(onProccesing.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        userSesionState.value?.let {
            when(userSesionState.value){
                is RequestResponse.Succes -> {
                    Log.i("CRONOS","SUCCES" )
                    onProccesing.value = false
                    navC.navigate(Router.LOGIN.route)
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("CRONOS","ERROR" )
                    onProccesing.value = false
                    val errorLogin = userSesionState.value as RequestResponse.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onProccesing.value = false
                    loginViewModel.resetLogin()
                }//Error
                else -> {
                    onProccesing.value = false
                }
            }//when
        }//observable let

    }//onProccesing

}