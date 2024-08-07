package com.aur3liux.naats.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aur3liux.naats.model.RequestResponse
import androidx.room.Room
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aur3liux.naats.localdatabase.AppDb
import com.aur3liux.naats.Store
import com.aur3liux.naats.model.CurpData
import com.aur3liux.naats.model.RequestCurp
import org.json.JSONObject
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class CurpRepo @Inject constructor() {

    //-- INICIO DE SESION
    fun getCurpData(context: Context, curp: String): MutableLiveData<RequestCurp> {
        val _userCurp: MutableLiveData<RequestCurp> = MutableLiveData<RequestCurp>()
        _userCurp.postValue(RequestCurp.Succes(data = CurpData(
            curp = "GOCA731205HCCNCR00",
            nombre = "Arquímedes",
            materno = "Gonzalez",
             paterno  = "Cauich",
            sexo = "Masculino",
            fNacimiento = "05-12-1973")))
       // val url = "${Store.API_URL.BASE_URL}/api/login"
        /*
        try {
            var queue = Volley.newRequestQueue(context)
            val jsonObjectRequest =
                JsonObjectRequest(Request.Method.POST, url, jsonObj, { response ->
                    Log.i("NAATS", "Response %s".format(response.toString()))
                    if (response.getBoolean("permitido")) {
                       // val dataResponse = response.getJSONObject("data")
                        val userData = UserData(
                            userName = jsonObj.getString("email"),
                            tokenAccess = response.getString("token_access")
                        )
                        //De manera local se almacena el inicio de sesion
                        _userData.postValue(RequestResponse.Succes())
                    } else {
                        val errMg = response.getString("message")
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
        */
        return _userCurp
    } // INICIO DE SESION


    //-- CERRAR SESION
    fun doCloseSesion(context: Context): MutableLiveData<RequestResponse> {
        val _userData: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
        val url = "${Store.API_URL.BASE_URL}/api/user/logout"

        //-- DATOS PARA LA BASE DE DATOS LOCAL
        val db = Room.databaseBuilder(context, AppDb::class.java, Store.DB.NAME)
            .allowMainThreadQueries()
            .build()

        val dataUser = db.userDao().getUserData()
        val credentials = dataUser.tokenAccess

        try {
            var queue = Volley.newRequestQueue(context)
            val jsonObjectRequest = doRequestAPI(
                method = Request.Method.POST,
                url = url, {  response ->
                    Log.i("CRONOS", "Response %s".format(response.toString()))
                    if (response.getBoolean("status")) {
                        _userData.postValue(RequestResponse.Succes())
                        db.sesionDao().deleteSesion()
                        db.userDao().deleteAllUser()
                    } else {
                        val errMg = response.getString("message")
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
                        when(val errorCode = error.networkResponse.statusCode){
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
                    }
                   },
                credentials = credentials
            )

            jsonObjectRequest.setRetryPolicy(
                DefaultRetryPolicy(90000,
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
    } // CERRAR SESION


    class doRequestAPI(
        method:Int, url: String,
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

