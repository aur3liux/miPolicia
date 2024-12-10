package com.aur3liux.naats.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aur3liux.naats.model.RequestResponse
import androidx.room.Room
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.localdatabase.SesionData
import com.aur3liux.naats.localdatabase.Store
import com.aur3liux.naats.localdatabase.UserData
import org.json.JSONObject
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class RegistroRepo @Inject constructor() {

    //-- INICIO DE SESION
    fun doRegistro(context: Context, jsonObj: JSONObject): MutableLiveData<RequestResponse> {
        val _userData: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
        val url = "${Store.API_URL.BASE_URL}/api/accounts/register"

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
                        //val dataResponse = response.getJSONObject("data")
                        val userData = UserData(
                            curp = jsonObj.getString("curp"),
                            userName = response.getString("username"),
                            tokenAccess = response.getString("token_access"),
                            nombre = jsonObj.getString("nombre"),
                            paterno = jsonObj.getString("paterno"),
                            materno = jsonObj.getString("materno"),
                            sexo = jsonObj.getString("sexo"),
                            correo = jsonObj.getString("email"),
                            telefono = jsonObj.getString("telefono"),
                            ciudad = "",
                            localidad = "",
                            colonia = "",
                            calle = "",
                            edoCivil = "",
                            ocupacion = "",
                            fNacimiento = ""
                        )
                        //De manera local se almacena el inicio de sesion
                        db.userDao().updateUser(user = userData)
                        db.sesionDao().insertSesion(SesionData(1, 1))
                        _userData.postValue(RequestResponse.Succes())
                    } else {
                        val errMg = response.getString("msg_error")
                        _userData.postValue(
                            RequestResponse.Error(
                                estatusCode = -1,
                                errorMessage = errMg.toString()
                            )
                        )
                    }

                }, { error ->
                    if (error.networkResponse == null) {
                        _userData.postValue(
                            RequestResponse.Error(
                                estatusCode = -1,
                                errorMessage = "Problemas de conexión con el servidor"
                            )
                        )
                    } else {
                        val errorCode = error.networkResponse.statusCode
                        when(errorCode){
                            400 ->{
                                _userData.postValue(
                                    RequestResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "No se puede procesar la información que envías, verifica tus datos si error persiste reportalo a soporte técnico."
                                    )
                                )
                            } // BAD REQUEST
                            401 -> {
                                _userData.postValue(
                                    RequestResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "Las credenciales de acceso no tienen permiso, verifica que estén correctas o intenta con otra."
                                    )
                                )
                            }//NO AUTORIZADO
                            404 -> {
                                _userData.postValue(
                                    RequestResponse.Error(
                                        estatusCode = errorCode,
                                        errorMessage = "El recurso solicitado no existe, repórtalo a soporte técnico"
                                    )
                                )
                            }//RECURSO NO ENCONTRADO
                            else -> {
                                _userData.postValue(
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
                    90000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            )
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            _userData.postValue(
                RequestResponse.Error(
                    estatusCode = 0,
                    errorMessage = "No se puede procesar la información"
                )
            )
        } catch (ex: SocketTimeoutException) {
            _userData.postValue(
                RequestResponse.Error(
                    estatusCode = 6000,
                    errorMessage = "Tiempo de conexión excedido"
                )
            )
        }
        return _userData
    } // INICIO DE SESION
}

