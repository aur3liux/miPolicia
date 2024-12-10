package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LoginRepo
import org.json.JSONObject
import javax.inject.Inject

class LoginVMFactory(private val loginRepository: LoginRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginVM::class.java)){
            return LoginVM(loginRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class LoginVM @Inject constructor(private val masterRepository: LoginRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _userLogin: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var UserData: LiveData<RequestResponse> = _userLogin

    //******************  Iniciar sesion
    fun DoLoginUser(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.doLogin(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _userLogin.value = it
                }
                observeForever {
                    // Log.i("OBSERVER", UserData!!.value.toString())
                }
            }
            return RequestResponse.Error(0, "Please wait")
        } catch (ex: Exception) {
            return RequestResponse.Error(0, "Error de acceso ${ex.message}")
        }
    }//Login


    @SuppressLint("NullSafeMutableLiveData")
    fun resetLogin() {
        _userLogin.value = null
    }


}