package com.aur3liux.naats.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.localdatabase.Store
import com.aur3liux.naats.model.PredenunciaMetaData
import com.aur3liux.naats.model.RequestPredenuncia
import org.json.JSONObject
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class PredenunciaRepo @Inject constructor() {

    //-- INICIO DE SESION
    fun doPredenuncia(context: Context, jsonObj: JSONObject): MutableLiveData<RequestPredenuncia> {
        val _userData: MutableLiveData<RequestPredenuncia> = MutableLiveData<RequestPredenuncia>()
        val url = "${Store.API_URL.BASE_URL}/api/complaints/register"

        //-- DATOS PARA LA BASE DE DATOS LOCAL
        val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
            .allowMainThreadQueries()
            .build()
        try {
            var queue = Volley.newRequestQueue(context)
            val jsonObjectRequest =
                JsonObjectRequest(Request.Method.POST, url, jsonObj, { response ->
                    Log.i("NAATS", "Response %s".format(response.toString()))
                    if (response.getBoolean("permitido")) {
                        val dataResponse = response.getJSONObject("data")
                        _userData.postValue(RequestPredenuncia.Succes(
                            PredenunciaMetaData(
                                folio = dataResponse.getString("folio"),
                                estatus = dataResponse.getString("estatus"),
                                modulo = dataResponse.getString("modulo")
                            )))
                    } else {
                        val errMg = response.getString("message")
                        _userData.postValue(
                            RequestPredenuncia.Error(
                                estatusCode = -1,
                                errorMessage = errMg.toString()
                            )
                        )
                    }
                }, { error ->
                    if (error.networkResponse == null) {
                        _userData.postValue(
                            RequestPredenuncia.Error(
                                estatusCode = -1,
                                errorMessage = "Problemas de conexión con el servidor"
                            )
                        )
                    } else {
                        val errorCode = error.networkResponse.statusCode
                        when(errorCode){
                            400 ->{
                                _userData.postValue(
                                    RequestPredenuncia.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "No se puede procesar la información que envías, verifica tus datos si error persiste reportalo a soporte técnico."
                                    )
                                )
                            } // BAD REQUEST
                            401 -> {
                                _userData.postValue(
                                    RequestPredenuncia.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "Las credenciales de acceso no tienen permiso, verifica que estén correctas o intenta con otra."
                                    )
                                )
                            }//NO AUTORIZADO
                            404 -> {
                                _userData.postValue(
                                    RequestPredenuncia.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "El recurso solicitado no existe, repórtalo a soporte técnico"
                                    )
                                )
                            }//RECURSO NO ENCONTRADO
                            else -> {
                                _userData.postValue(
                                    RequestPredenuncia.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "El recurso solicitado no fue encontrado en el servidor"
                                    )
                                )
                            } //else del when
                        }// when
                    } // else
                })
            jsonObjectRequest.setRetryPolicy(
                DefaultRetryPolicy(
                    90000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            )
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            _userData.postValue(
                RequestPredenuncia.Error(
                    estatusCode = 0,
                    errorMessage = "No se puede procesar la información"
                )
            )
        } catch (ex: SocketTimeoutException) {
            _userData.postValue(
                RequestPredenuncia.Error(
                    estatusCode = 6000,
                    errorMessage = "Tiempo de conexión excedido"
                )
            )
        }
        return _userData
    } // INICIO DE SESION
}

