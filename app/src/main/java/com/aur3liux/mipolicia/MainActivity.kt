package com.aur3liux.mipolicia

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room.databaseBuilder
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.LocationData
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.ui.theme.NaatsTheme
import com.aur3liux.mipolicia.ui.theme.shapePrincipalColor
import com.aur3liux.mipolicia.view.auth.CloseSessionConfirmView
import com.aur3liux.mipolicia.view.Home
import com.aur3liux.mipolicia.view.LoadView
import com.aur3liux.mipolicia.view.ReglamentoTransito
import com.aur3liux.mipolicia.view.auth.LoginView
import com.aur3liux.mipolicia.view.VideoPlayer
import com.aur3liux.mipolicia.view.auth.RegistroView
import com.aur3liux.mipolicia.view.cibernetica.PoliciaCiberneticaView
import com.aur3liux.mipolicia.view.cibernetica.ReporteCiberneticaView
import com.aur3liux.mipolicia.view.subviews.AcompanamientoBancarioView
import com.aur3liux.mipolicia.view.subviews.DetallesMarcoLegal
import com.aur3liux.mipolicia.view.subviews.MarcoLegalView
import com.aur3liux.mipolicia.view.subviews.PerfilView
import com.aur3liux.mipolicia.view.subviews.QuejaFelicitacionView
import com.aur3liux.mipolicia.view.subviews.ReporteCiudadano
import com.aur3liux.mipolicia.view.subviews.SectorView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var context: Context
    //Datos de la geolocalizacion
    private var fusedLocCliente: FusedLocationProviderClient? = null
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private val INTERVAL: Long = 2000
    private val FASTED_INTERVAL: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        mLocationRequest = LocationRequest()
        fusedLocCliente = LocationServices.getFusedLocationProviderClient(context.applicationContext)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        enableEdgeToEdge()

        setContent {
            NaatsTheme {
                //window.statusBarColor = botonColor.toArgb()
                window.statusBarColor = shapePrincipalColor.toArgb()
                //Barra de abajo, botones de navegacion del telefono
                window.navigationBarColor = shapePrincipalColor.toArgb()

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Router.LOAD_VIEW.route){
                    //LOAD
                    composable(Router.LOAD_VIEW.route){
                        BackHandler(true) {}
                        LoadView(navC = navController)
                    }//Load

                    //ACCESO REGISTRO
                    composable(Router.REGISTRO_VIEW.route){
                        RegistroView(navC = navController)
                    }//Acceso-registro


                    //LOGIN
                    composable(Router.LOGIN.route){
                        BackHandler {true}
                        LoginView(navC = navController)
                    }//Login

                    //CLOSE SESSION
                    composable(Router.CLOSE_SESSION.route){
                        CloseSessionConfirmView(
                            navC = navController
                        )
                    }//Close session

                    //HOME
                    composable(
                        enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                delayMillis = 0,
                                durationMillis = 500,
                                easing = LinearEasing)) + slideIntoContainer(
                            animationSpec = tween(200, easing = LinearEasing),
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            initialOffset = { it }
                        )},
                        route = Router.HOME.route){
                        BackHandler(true) {}
                        Home(navC = navController)
                    }//Home

                    //PERFIL DE USUARIO
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.PERFIL_VIEW.route){
                        PerfilView(navC = navController)
                    }//Perfil de usuario

                    //REPORTE CIUDADANO
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.REPORTE_CIUDADANO.route){
                        ReporteCiudadano(navC = navController)
                    }//Datos del sector

                    //QUEJAS O FELICITACIONES
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.QUEJAS_FELICITACIONES.route){
                        QuejaFelicitacionView(navC = navController)
                    }//Datos del sector


                    //DATOS DEL SECTOR
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.SECTOR_VIEW.route){
                        SectorView(navC = navController)
                    }//Datos del sector


                    //ACOMPAÑAMIENTO BANCARIO
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.ACOMPANAMIENTO_BANCARIO_VIEW.route){
                        AcompanamientoBancarioView(navC = navController)
                    }//Datos del sector


                    //MARCO LEGAL
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.MARCOLEGAL_VIEW.route){
                        MarcoLegalView(navC = navController)
                    }//Subcategoria


                    //DETALLES MARCO LEGAL
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                initialOffset = { it }
                            )},
                        route = Router.DETALLES_MARCOLEGAL.route){ data ->
                        var seccion = data.arguments!!.getString("seccion")
                        DetallesMarcoLegal(navC = navController, seccion = seccion!!)
                    }//Subcategoria

                    //REGLAMENTO TRANSITO
                    composable(Router.REGLAMENTO_TRANSITO.route){
                        ReglamentoTransito(navC = navController)
                    }//Close session

                    //POLICIA CIBERNETICA
                    composable(
                        enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) + slideIntoContainer(
                            animationSpec = tween(200, easing = LinearEasing),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            initialOffset = { it }
                        )},
                        route = Router.POLICIA_CIBERNETICA.route){
                        PoliciaCiberneticaView(navC = navController)
                    }

                    //REPORTE CIBERNETICA
                    composable(
                        enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) + slideIntoContainer(
                            animationSpec = tween(200, easing = LinearEasing),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            initialOffset = { it }
                        )},
                        route = Router.REPORTE_CIBERNETICA.route){
                        ReporteCiberneticaView(navC = navController)
                    }

                    //POLICIA CIBERNETICA
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
                        route = Router.POLICIA_CIBERNETICA.route) {
                        PoliciaCiberneticaView(navC = navController)
                    }


                    //VIDEO PLAYER
                    composable(
                        enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) + slideIntoContainer(
                            animationSpec = tween(200, easing = LinearEasing),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            initialOffset = { it }
                        )},
                        route = Router.VIDEO_PLAYER.route) {
                        VideoPlayer()
                    }

                }//NavHost
            }
        }
    } //OnCreate


    override fun onStart(){
        super.onStart()
        startLocationUpdates(context)
        Log.i(Store.APP.name, "REINCIANDO")
    } //onStart

    override fun onResume() {
        super.onResume()
        context = applicationContext
        Log.i(Store.APP.name, "REANUDANDO")
        startLocationUpdates(context)
    } //onResume

    fun startLocationUpdates(context: Context) {
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTED_INTERVAL)

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(context.applicationContext)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        fusedLocCliente = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),2000)
            //return
        }
        Looper.myLooper()?.let {
            fusedLocCliente!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                it
            )
        }
    } //startlocationUpdates

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation!!)
        }
    } //mLocationCallback

    fun stopLocationUpdates() {
        fusedLocCliente!!.removeLocationUpdates(mLocationCallback)
        Log.i(Store.APP.name, "Location updates removed")
    } //stopLocationUpdates

    fun onLocationChanged(location: Location) {
        mLastLocation = location
        Log.i(Store.APP.name, "UBICACION ${mLastLocation.latitude},  ${mLastLocation.longitude} ")

        val db = databaseBuilder(context,
            AppDb::class.java,
            Store.DB.NAME).build()
        val locationDb = db.locationDao()

        GlobalScope.launch {
            var dataLoc = LocationData(
                id =0,
                latitud = mLastLocation.latitude,
                longitud = mLastLocation.longitude)
            locationDb.updateLocation(dataLoc)

            Log.i(Store.APP.name, "UBICACION ACTUALIZADA")
        }

        db.close()
        stopLocationUpdates()
    } //onLocationChanges
}