package com.aur3liux.mipolicia.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aur3liux.mipolicia.model.RequestResponse
import androidx.room.Room
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.localdatabase.UserData
import com.aur3liux.mipolicia.model.SectorInfo
import com.aur3liux.mipolicia.model.SectorResponse
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.Exception

class ConsultaSectorRepo @Inject constructor() {
    //-- CONSULTA SECTOR
    fun doConsultaSector(context: Context, latitud: Double, longitud: Double, device: String): MutableLiveData<SectorResponse> {
        val _userData: MutableLiveData<SectorResponse> = MutableLiveData<SectorResponse>()
        val url = "${Store.API_URL.BASE_URL}/api/sector/current?latitude=${latitud}&longitude=${longitud}&device=$device"

        //-- DATOS PARA LA BASE DE DATOS LOCAL
        val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
            .allowMainThreadQueries()
            .build()

        val dataUser = db.userDao().getUserData()
        val credentials = dataUser.tokenAccess
        try {
            var queue = Volley.newRequestQueue(context)
            val jsonObjectRequest = DoRequestCloseSession(
                method = Request.Method.GET,
                url = url, { response ->
                    Log.i("MI POLICIA", "Response %s".format(response.toString()))
                    if (response.getBoolean("success")) {
                        try {
                               val dataResponse = response.getJSONObject("data")
                            val sectorInfo= SectorInfo(
                                nombreSector = dataResponse.getJSONObject("sector").getString("sectores"),
                                nombreDirector = "${dataResponse.getJSONObject("responsable").getString("fullname_do")}",
                                nombreResponsable_A = "${dataResponse.getJSONObject("responsable").getString("fullname_schedule_a")}",
                                nombreResponsable_B = "${dataResponse.getJSONObject("responsable").getString("fullname_schedule_b")}",
                                address = dataResponse.getJSONObject("sector").getString("address"),
                                phone = dataResponse.getJSONObject("responsable").getString("phone"),
                                phone_do = dataResponse.getJSONObject("responsable").getString("phone_do"),
                                photo_Director =dataResponse.getJSONObject("responsable").getString("photo_url_do"),
                                photo_A = dataResponse.getJSONObject("responsable").getString("photo_url_a"),
                                photo_B = dataResponse.getJSONObject("responsable").getString("photo_url_b")
                            )

                            _userData.postValue(SectorResponse.Succes(sectorInfo))
                        }catch (ex: Exception){
                            _userData.postValue(
                                SectorResponse.Error(
                                    estatusCode = -1,
                                    errorMessage = "No se pudo recuperar la información del sector"
                                )
                            )
                        }

                    } else {
                        val errMg = response.getString("message")
                        _userData.postValue(
                            SectorResponse.Error(
                                estatusCode = -1,
                                errorMessage = errMg.toString()
                            )
                        )
                    }
                }, { error ->
                    if (error.networkResponse == null) {
                        _userData.postValue(
                            SectorResponse.Error(
                                estatusCode = -1,
                                errorMessage = "Problemas de conexión con el servidor"
                            )
                        )
                    } else {
                        val errorCode = error.networkResponse.statusCode
                        when(errorCode){
                            400 ->{
                                _userData.postValue(
                                    SectorResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "No se puede procesar la información que envías, verifica tus datos si error persiste reportalo a soporte técnico."
                                    )
                                )
                            } // BAD REQUEST
                            401 -> {
                                _userData.postValue(
                                    SectorResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "Las credenciales de acceso no tienen permiso, verifica que estén correctas o intenta con otra."
                                    )
                                )
                            }//NO AUTORIZADO
                            404 -> {
                                _userData.postValue(
                                    SectorResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "El recurso solicitado no existe, repórtalo a soporte técnico"
                                    )
                                )
                            }//RECURSO NO ENCONTRADO
                            else -> {
                                _userData.postValue(
                                    SectorResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "El recurso solicitado no fue encontrado en el servidor"
                                    )
                                )
                            } //else del when
                        }// when
                    } // else
                },
                credentials = credentials)
            jsonObjectRequest.setRetryPolicy(
                DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            )
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            _userData.postValue(
                SectorResponse.Error(
                    estatusCode = 0,
                    errorMessage = "No se puede procesar la información"
                )
            )
        } catch (ex: SocketTimeoutException) {
            _userData.postValue(
                SectorResponse.Error(
                    estatusCode = 6000,
                    errorMessage = "Tiempo de conexión excedido"
                )
            )
        }
        return _userData
    } // CIERRE DE SESION


    class DoRequestCloseSession(
        method:Int,
        url: String,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener,
        credentials:String
    ) : JsonObjectRequest(method,url, null, listener, errorListener) {

        private var mCredentials:String = credentials

        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            val auth = "Bearer $mCredentials"
            headers["Authorization"] = auth
            return headers
        }
    }
}

