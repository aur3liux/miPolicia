package com.aur3liux.naats.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.aur3liux.naats.Router
import com.aur3liux.naats.Store
import com.aur3liux.naats.components.InfoDialog
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.ui.theme.dGradient1
import com.aur3liux.naats.ui.theme.dGradient2
import com.aur3liux.naats.ui.theme.lGradient1
import com.aur3liux.naats.ui.theme.lGradient2
import com.aur3liux.naats.view.subviews.BottomNavigationItem
import com.aur3liux.naats.view.subviews.BuscaEvento
import com.aur3liux.naats.view.subviews.BuscaPersona
import com.aur3liux.naats.view.subviews.BuscaVehiculo
import com.aur3liux.naats.view.subviews.NavigationDrawer
import com.aur3liux.naats.view.subviews.PanelSearch
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navC: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var darkTheme = isSystemInDarkTheme()
    val color1 = if (darkTheme) lGradient1 else dGradient1
    val color2 = if (darkTheme) lGradient2 else dGradient2

    val confirmCloseSession = rememberSaveable { mutableStateOf(false) }

    //Datos a consultar por persona
    val nombre = rememberSaveable { mutableStateOf("") }
    val paterno = rememberSaveable { mutableStateOf("") }
    val materno = rememberSaveable { mutableStateOf("") }
    val licencia = rememberSaveable { mutableStateOf("") }
    val rfc = rememberSaveable { mutableStateOf("") }
    val curp = rememberSaveable { mutableStateOf("") }
    val alias = rememberSaveable { mutableStateOf("") }
    val telefono = rememberSaveable { mutableStateOf("") }
    val personaExacta = remember { mutableStateOf(false) }

    //Datos a consultar por vehiculo
    val placa = rememberSaveable { mutableStateOf("") }
    val serie = rememberSaveable { mutableStateOf("") }
    val vehiculoExacto = remember { mutableStateOf(false) }

    //Datos a consultar por evento
    val delito = rememberSaveable{ mutableStateOf(Store.APP.txDelito) }
    val direccion = rememberSaveable{ mutableStateOf("") }
    val ubicacion = rememberSaveable{ mutableStateOf("") }
    val fechaInicio = rememberSaveable{ mutableStateOf("") }
    val fechaFin = rememberSaveable{ mutableStateOf("") }
    val eventoExacto  = rememberSaveable{ mutableStateOf(false) }

    //-- DATOS PARA LA BASE DE DATOS LOCAL
    val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
        .allowMainThreadQueries()
        .build()
    val usuario = db.userDao().getUserData()

    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val showDialogHelp = remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                backColor = color2,
                userName = usuario.userName,
                showHelp = showDialogHelp,
                confirmCloseSession = confirmCloseSession) {
                scope.launch {
                    drawerState.close()
                }
            }
        }) {
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
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }//scope
                                },
                            imageVector = Icons.Filled.Menu, contentDescription = "")
                    })
                },
            bottomBar = {
                NavigationBar(containerColor = Color.Transparent) {
                    //obtenemos la lista de botones de navegacion
                    BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->

                        //itera sobre los botones de navegacion
                        NavigationBarItem(
                            selected = navigationItem.route == currentDestination?.route,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } //onClick
                        )
                    } //BottomNavigationItem
                }//NavigationBar
            }) {
             NavHost(
                    navController = navController,
                    startDestination = Router.PANEL_SEARCH.route,
                    modifier = Modifier.padding(5.dp)) {
                    composable(Router.PANEL_SEARCH.route) {
                        PanelSearch(
                            navC = navC,
                            nombre = nombre,
                            paterno = paterno,
                            materno = materno,
                            licencia = licencia,
                            rfc = rfc,
                            curp = curp,
                            alias = alias,
                            telefono = telefono,
                            placa = placa,
                            serie = serie,
                            delito = delito,
                            direccion = direccion,
                            ubicacion = ubicacion,
                            fecha_inicio = fechaInicio,
                            fecha_fin = fechaFin
                        )
                    }
                    composable(Router.BUSCA_PERSONA.route) {
                        BuscaPersona(
                            nombre = nombre,
                            paterno = paterno,
                            materno = materno,
                            licencia = licencia,
                            rfc = rfc,
                            curp = curp,
                            alias = alias,
                            telefono = telefono,
                            personaExacta = personaExacta,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                    composable(Router.BUSCA_VEHICULO.route) {
                        BuscaVehiculo(
                            placa = placa,
                            serie = serie,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                    composable(Router.BUSCA_EVENTO.route) {
                        BuscaEvento(
                            delito = delito,
                            direccion = direccion,
                            ubicacion = ubicacion,
                            fecha_inicio = fechaInicio,
                            fecha_fin = fechaFin,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                } //NavHost


            if(showDialogHelp.value){
                InfoDialog(
                    title = "Ayuda",
                    info = "Para realizar una consulta es necesario que en los formularios de las otras pantallas escribas los datos que deseas buscar, una vez capturados ya podrás ejecutar la consulta.",
                    onConfirmation = {
                        showDialogHelp.value = false
                })
            }

            if(confirmCloseSession.value) {
                navC.navigate(Router.CLOSE_SESSION.route)
                confirmCloseSession.value = false
            }
        } //Scaffold
    } //ModalNavigationDrawer
}