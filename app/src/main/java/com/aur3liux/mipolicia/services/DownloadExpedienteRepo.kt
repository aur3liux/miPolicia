package com.aur3liux.mipolicia.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aur3liux.mipolicia.model.RequestResponse
import androidx.room.Room
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aur3liux.mipolicia.localdatabase.AppDb
import com.aur3liux.mipolicia.localdatabase.Store
import com.aur3liux.mipolicia.localdatabase.MyPredenunciaData
import com.aur3liux.mipolicia.localdatabase.SesionData
import org.json.JSONObject
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class DownloadExpedienteRepo @Inject constructor() {

    //-- Descarga el listado de expediente
    fun DownloadMyExpediente(context: Context, jsonObj: JSONObject): MutableLiveData<RequestResponse> {
        val _expedienteData: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
        val url = "${Store.API_URL.BASE_URL}/api/complaints/list"

        //-- DATOS PARA LA BASE DE DATOS LOCAL
        val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
            .allowMainThreadQueries()
            .build()
        try {
            var queue = Volley.newRequestQueue(context)
            val jsonObjectRequest =
                JsonObjectRequest(Request.Method.POST, url, jsonObj, { response ->
                    Log.i("NAATS", "Response %s".format(response.toString()))
                    if (response.getBoolean("respuesta")) {
                        val dataResponse = response.getJSONArray("list")
                        //RECORREMOS LA LISTA
                        for(i in 0 until dataResponse.length()){
                            val predenunciaObj = dataResponse.getJSONObject(i)
                            Log.i("PERSONA", "${predenunciaObj}")
                            val pred = MyPredenunciaData(
                                folio = predenunciaObj.getString("folio"),
                                estatus = predenunciaObj.getString("estatus"),
                                delito = predenunciaObj.getString("delito"),
                                subDelito = "",
                                modulo = "",
                                ciudad ="",
                                descripcion = "",
                                fecha = predenunciaObj.getString("fecha"),
                                hora = predenunciaObj.getString("hora"),
                                fechaModif = "",
                                horaModif = "")

                            db.myPredenunciaDao().insertPredenuncia(pred)
                        } //for predenuncias expediente
                        db.sesionDao().upDateSession(SesionData(1, 2))
                        _expedienteData.postValue(RequestResponse.Succes())
                    } else {
                        val errMg = response.getString("msg_error")
                        _expedienteData.postValue(
                            RequestResponse.Error(
                                estatusCode = -1,
                                errorMessage = errMg.toString()
                            )
                        )
                    }
                }, { error ->
                    if (error.networkResponse == null) {
                        _expedienteData.postValue(
                            RequestResponse.Error(
                                estatusCode = -1,
                                errorMessage = "Problemas de conexión con el servidor"
                            )
                        )
                    } else {
                        val errorCode = error.networkResponse.statusCode
                        when(errorCode){
                            400 ->{
                                _expedienteData.postValue(
                                    RequestResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "No se puede procesar la información que envías, verifica tus datos si error persiste reportalo a soporte técnico."
                                    )
                                )
                            } // BAD REQUEST
                            401 -> {
                                _expedienteData.postValue(
                                    RequestResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "Las credenciales de acceso no tienen permiso, verifica que estén correctas o intenta con otra."
                                    )
                                )
                            }//NO AUTORIZADO
                            404 -> {
                                _expedienteData.postValue(
                                    RequestResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "El recurso solicitado no existe, repórtalo a soporte técnico"
                                    )
                                )
                            }//RECURSO NO ENCONTRADO
                            else -> {
                                _expedienteData.postValue(
                                    RequestResponse.Error(
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
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            )
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            _expedienteData.postValue(
                RequestResponse.Error(
                    estatusCode = 0,
                    errorMessage = "No se puede procesar la información"
                )
            )
        } catch (ex: SocketTimeoutException) {
            _expedienteData.postValue(
                RequestResponse.Error(
                    estatusCode = 6000,
                    errorMessage = "Tiempo de conexión excedido"
                )
            )
        }
        return _expedienteData
    } // INICIO DE SESION

}

