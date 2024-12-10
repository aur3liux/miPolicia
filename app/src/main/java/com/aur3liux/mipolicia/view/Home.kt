package com.aur3liux.naats.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aur3liux.naats.R
import com.aur3liux.naats.Router
import com.aur3liux.naats.ToolBox
import com.aur3liux.naats.view.bottomviews.BottomNavigationItem
import com.aur3liux.naats.view.bottomviews.FichaFgecamView
import com.aur3liux.naats.view.bottomviews.PerfilView
import com.aur3liux.naats.view.bottomviews.MapHome
import com.aur3liux.naats.view.bottomviews.HelpView

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navC: NavController) {
    val context = LocalContext.current

    val confirmCloseSession = rememberSaveable { mutableStateOf(false) }

    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val showDialogHelp = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {},
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface) {
                //obtenemos la lista de botones de navegacion
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->

                    //itera sobre los botones de navegacion
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.background,
                            selectedTextColor = MaterialTheme.colorScheme.inverseSurface,
                            unselectedIconColor = MaterialTheme.colorScheme.inverseSurface,
                            unselectedTextColor = MaterialTheme.colorScheme.inverseSurface,
                            disabledIconColor = Color.Gray,
                            disabledTextColor = Color.Gray,
                            selectedIndicatorColor = MaterialTheme.colorScheme.inverseSurface
                        ),
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
                            ToolBox.soundEffect(context, R.raw.slide)
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
                startDestination = Router.MAP_HOME_VIEW.route) {
             composable(enterTransition = {
                 fadeIn(
                     animationSpec = tween(
                         delayMillis = 0,
                         durationMillis = 700,
                         easing = LinearEasing)) + slideIntoContainer(
                     animationSpec = tween(200, easing = LinearEasing),
                     towards = AnimatedContentTransitionScope.SlideDirection.End,
                     initialOffset = { it }
                 )},
                 route = Router.MAP_HOME_VIEW.route) {
                    MapHome(navC = navC)
                }
                //FICHA FGECAM
                composable(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                delayMillis = 0,
                                durationMillis = 700,
                                easing = LinearEasing)) + slideIntoContainer(
                            animationSpec = tween(200, easing = LinearEasing),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            initialOffset = { it }
                        )},
                    route = Router.FGECAM_VIEW.route) {
                    FichaFgecamView(navC = navController)
                }
                //PERFIL
                composable(
                    enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) + slideIntoContainer(
                        animationSpec = tween(200, easing = LinearEasing),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        initialOffset = { it }
                    )},
                    route = Router.PERFIL_VIEW.route) {
                    PerfilView(navC = navC)
                }
                //AVISO DE PRIVACIDAD
                composable(
                    enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) + slideIntoContainer(
                        animationSpec = tween(200, easing = LinearEasing),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        initialOffset = { it }
                    )},
                    route = Router.AVISO_PRIVACIDAD.route){
                     AvisoPrivacidad(navC = navController)
                }
                 //AYUDA
                 composable(
                     enterTransition = {
                         fadeIn(
                             animationSpec = tween(
                                 delayMillis = 0,
                                 durationMillis = 700,
                                 easing = LinearEasing)) + slideIntoContainer(
                             animationSpec = tween(200, easing = LinearEasing),
                             towards = AnimatedContentTransitionScope.SlideDirection.End,
                             initialOffset = { it }
                         )},
                     route = Router.HELP_VIEW.route) {
                     HelpView(navC = navController)
                 }
         } //NavHost


        if(confirmCloseSession.value) {
            navC.navigate(Router.CLOSE_SESSION.route)
            confirmCloseSession.value = false
        }
    } //Scaffold
}