package com.aur3liux.mipolicia.view.bottomviews

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.R
import com.aur3liux.mipolicia.Router
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ToolBox
import com.aur3liux.mipolicia.components.ConfirmDialog
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LogOutRepo
import com.aur3liux.mipolicia.ui.theme.cronosColor
import com.aur3liux.mipolicia.ui.theme.lGradient2
import com.aur3liux.mipolicia.viewmodel.LogOutVM
import com.aur3liux.mipolicia.viewmodel.LogOutVMFactory
import org.json.JSONObject

@Composable
fun PerfilView(navC: NavController) {
    val context = LocalContext.current
    val db = databaseBuilder(context,
        AppDb::class.java,
        Store.DB.NAME)
        .allowMainThreadQueries()
        .build()

    val user = db.userDao().getUserData()

    val confirmCloseSession = rememberSaveable { mutableStateOf(false) }
    val onCloseSession = rememberSaveable { mutableStateOf(false) }

    val showSheetError = remember { mutableStateOf(false) }
    val messageError = remember { mutableStateOf("") }

    //viewmodel
    val logoutViewModel: LogOutVM = viewModel(
        factory = LogOutVMFactory(logoutRepository = LogOutRepo())
    )

    //LiveData
    val closeSesionState = remember(logoutViewModel) {
        logoutViewModel.UserData
    }.observeAsState()

    Column( modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        if(user == null){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Transparent),
                color = cronosColor,
                strokeWidth = 4.dp
            )
        }else{
            Image(
                modifier = Modifier
                    .background(lGradient2)
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                painter = painterResource(id = R.drawable.gobierno_todos_banner),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        modifier = Modifier
                            .height(50.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        text = "Datos del usuario",
                        fontSize = 17.sp,
                        fontFamily = ToolBox.quatroSlabFont,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = R.drawable.logo_mp),
                        contentDescription = "",
                        contentScale = ContentScale.Fit
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    //NOMBRE COMPLETO
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.padding(start = 10.dp),
                            imageVector = Icons.Filled.PersonPin, contentDescription = "")
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "", //"${user.nombre} ${user.paterno} ${user.materno}" ,
                            fontSize = 11.sp,
                            fontFamily = ToolBox.montseFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row nombre completo

                    HorizontalDivider()

                    //CURP
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                            text = "Usuario",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${user.email}",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row CURP

                    HorizontalDivider()

                    //EMAIL
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                            text = "Correo",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${user.email}",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    } //Row EMAIL

                    HorizontalDivider()

                    //TELEFONO
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
                            text = "Tel.",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${user.telefono}",
                            fontSize = 12.sp,
                            fontFamily = ToolBox.quatroSlabFont,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    //AVISO DE PRIVACIDAD
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd){
                        Text(
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { navC.navigate(Router.AVISO_PRIVACIDAD.route) },
                            text = "Aviso de privacidad",
                            fontSize = 15.sp,
                            fontFamily = ToolBox.montseFont,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Medium
                        )
                    }


                    if(!onCloseSession.value)
                        //CERRAR SESION
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd){
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 10.dp)
                                        .clickable { confirmCloseSession.value = true },
                                    text = "Cerrar sesión",
                                    fontSize = 15.sp,
                                    fontFamily = ToolBox.montseFont,
                                    letterSpacing = 0.5.sp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontWeight = FontWeight.Medium
                                )
                    } else {
                        Column {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(Color.Transparent),
                                color = cronosColor,
                                strokeWidth = 4.dp
                            )
                            Text(text = "Cerrando sesión")
                        } //Column
                    }
                }//Card
            } //Column
        } //Validamos que el usuario no sea nulo
    } //Column

    //DIALOGO PARA CONFIRMAR CIERRE DE SESION
    if(confirmCloseSession.value){
        ConfirmDialog(
            title = "Confirmación",
            info = "Confirme que desea cerrar la sesion en éste dispositivo.",
            titleAceptar = "Confirmar",
            titleCancelar = "Cancelar",
            onAceptar = {
                logoutViewModel.DoLogOutUser(context)
                confirmCloseSession.value = false
                onCloseSession.value = true
            },
            onCancelar = {
                confirmCloseSession.value = false
            }
        )
    }

    //LIVEDATA PARA CERRAR SESION
    if(onCloseSession.value) {
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        closeSesionState.value?.let {
            when(closeSesionState.value){
                is RequestResponse.Succes -> {
                    Log.i("MIPOLICIA","SUCCES" )
                    onCloseSession.value = false
                    navC.navigate(Router.LOGIN.route) {
                        popUpTo(navC.graph.id) { inclusive = true }
                    }
                } //Succes
                is RequestResponse.Error -> {
                    Log.i("MI POLICIA","ERROR" )
                    onCloseSession.value = false
                    val errorLogin = closeSesionState.value as RequestResponse.Error
                    messageError.value = "${errorLogin.errorMessage}"
                    showSheetError.value = true
                    onCloseSession.value = false
                    logoutViewModel.resetLogOut()
                }//Error
                else -> {
                    onCloseSession.value = false
                }
            }//when
        }//observable let
    }//onProccesing

}