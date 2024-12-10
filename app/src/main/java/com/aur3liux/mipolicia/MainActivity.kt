package com.aur3liux.naats

/*
Alias: AndroidDebugKey
MD5: 7C:A3:5C:2A:4A:F8:65:7A:AF:BA:14:3F:30:70:EF:7A
SHA1: 90:1D:93:68:E2:4C:86:19:25:81:B1:DF:42:51:0B:4F:FF:2B:9D:3B
SHA-256: 1D:0E:D6:47:4B:A2:84:B5:17:80:51:ED:C2:83:0E:61:F6:E2:1D:1C:48:D8:88:7D:8E:1A:A2:F4:1C:66:C7:F5
Valid until: jueves, 9 de abril de 2054
 */

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
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.localdatabase.LocationData
import com.aur3liux.naats.localdatabase.Store
import com.aur3liux.naats.ui.theme.NaatsTheme
import com.aur3liux.naats.ui.theme.botonColor
import com.aur3liux.naats.view.auth.AccesoRegistroView
import com.aur3liux.naats.view.AvisoPrivacidad
import com.aur3liux.naats.view.auth.CloseSessionConfirmView
import com.aur3liux.naats.view.predenuncia.DetallesPredenucia
import com.aur3liux.naats.view.predenuncia.FinishPredenuncia
import com.aur3liux.naats.view.Home
import com.aur3liux.naats.view.Expediente
import com.aur3liux.naats.view.LoadView
import com.aur3liux.naats.view.auth.LoginView
import com.aur3liux.naats.view.predenuncia.ListSubcategoriaDenuncia
import com.aur3liux.naats.view.PantallaInicial
import com.aur3liux.naats.view.auth.RegistroFinishView
import com.aur3liux.naats.view.bottomviews.BuzonView
import com.aur3liux.naats.view.predenuncia.PredenunciaEnvio
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
                window.statusBarColor = botonColor.toArgb()
                window.navigationBarColor = botonColor.toArgb()

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Router.LOAD_VIEW.route){
                    //LOAD
                    composable(Router.LOAD_VIEW.route){
                        BackHandler(true) {}
                        LoadView(navC = navController)
                    }//Load

                    //POLITICA DE PRIVACIDAD INICIAL
                    composable(
                        Router.PANTALLA_INICIAL.route){
                        BackHandler(true) {}
                        PantallaInicial(navC = navController)
                    }//Load

                    //ACCESO REGISTRO
                    composable(Router.ACCESO_REGISTRO.route){
                        AccesoRegistroView(navC = navController)
                    }//Acceso-registro


                    //FINISH REGISTRO
                    composable(Router.FINISH_REGISTRO.route){
                        RegistroFinishView(navC = navController)
                    }//Acceso-registro-finish


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

                    //PREDENUNCIA
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
                        route = Router.PREDENUNCIA.route){
                        BackHandler(true) {}
                        PredenunciaEnvio(navC = navController)
                    }

                    //AVISO PRIVACIDAD
                    composable(Router.AVISO_PRIVACIDAD.route){
                        AvisoPrivacidad(navC = navController)
                    }//Close session


                    //LISTADO DE SUBCATEGORIAS
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
                        route = Router.SUBCATEGORIA_DELITOS.route){ index ->
                        var i = index.arguments!!.getString("index")
                        ListSubcategoriaDenuncia(navc = navController, indexSubcategoria = i!!.toInt())
                    }//Subcategoria

                    //FINISH PREDENUNCIA
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
                        route = Router.FINISH_PREDENUNCIA.route){folio ->
                        var f = folio.arguments!!.getString("folio")
                        BackHandler(true) {}
                        FinishPredenuncia(navc = navController, folio = f!!)
                    }//Close session


                    //LISTADO DE MIS PREDENUNCIAS
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
                        route = Router.EXPEDIENTE_LIST.route){
                        Expediente(navC = navController)
                    }//Subcategoria

                    //DETALLES PREDENUNCIA
                    composable(Router.DETALLES_PREDENUNCIA.route){ folio ->
                        var f = folio.arguments!!.getString("folio")
                        DetallesPredenucia(navC = navController, folio = f!!)
                    }//DETALLES

                    //BUZON DE AVISOS
                    composable(
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    delayMillis = 0,
                                    durationMillis = 500,
                                    easing = LinearEasing)) + slideIntoContainer(
                                animationSpec = tween(200, easing = LinearEasing),
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                initialOffset = { it }
                            )},
                        route = Router.BUZON_VIEW.route){
                        BuzonView(navC = navController)
                    }

                }//NavHost
            }
        }
    } //OnCreate


    override fun onStart(){
        super.onStart()
        startLocationUpdates(context)
        Log.i("SIRENA", "REINCIANDO")
    } //onStart

    override fun onResume() {
        super.onResume()
        context = applicationContext
        Log.i("SIRENA", "REANUDANDO")
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
        Log.i("SIRENA", "Location updates removed")
    } //stopLocationUpdates

    fun onLocationChanged(location: Location) {
        mLastLocation = location
        Log.i("SIRENA", "UBICACION ${mLastLocation.latitude},  ${mLastLocation.longitude} ")

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

            Log.i("SIRENA", "UBICACION ACTUALIZADA")
        }

        db.close()
        stopLocationUpdates()
    } //onLocationChanges
}