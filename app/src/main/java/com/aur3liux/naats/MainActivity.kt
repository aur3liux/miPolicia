package com.aur3liux.naats

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aur3liux.naats.ui.theme.NaatsTheme
import com.aur3liux.naats.ui.theme.botonColor
import com.aur3liux.naats.view.AccesoRegistro
import com.aur3liux.naats.view.AvisoPrivacidad
import com.aur3liux.naats.view.CloseSessionConfirmView
import com.aur3liux.naats.view.Home
import com.aur3liux.naats.view.LoadView
import com.aur3liux.naats.view.LoginView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        enableEdgeToEdge()
        setContent {
            NaatsTheme {
                window.statusBarColor = botonColor.toArgb()
                window.navigationBarColor = botonColor.toArgb()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Router.LOAD_VIEW.route){
                    //LOAD
                    composable(Router.LOAD_VIEW.route){
                        BackHandler(true) {}
                        LoadView(navC = navController)
                    }//Load

                    //ACCESO REGISTRO
                    composable(Router.ACCESO_REGISTRO.route){
                        AccesoRegistro(navC = navController)
                    }//Acceso-registro


                    //LOGIN
                    composable(Router.LOGIN.route){
                        LoginView(navC = navController)
                    }//Login

                    //CLOSE SESSION
                    composable(Router.CLOSE_SESSION.route){
                        CloseSessionConfirmView(
                            navC = navController
                        )
                    }//Close session

                    //HOME
                    composable(Router.HOME.route){
                        BackHandler(true) {}
                        Home(navC = navController)
                    }//Home

                    //AVISO PRIVACIDAD
                    composable(Router.AVISO_PRIVACIDAD.route){
                        AvisoPrivacidad()
                    }//Close session

                }//NavHost
            }
        }
    }
}
