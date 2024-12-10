package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.LogOutRepo
import org.json.JSONObject
import javax.inject.Inject

class LogOutVMFactory(private val logoutRepository: LogOutRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LogOutVM::class.java)){
            return LogOutVM(logoutRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class LogOutVM @Inject constructor(private val masterRepository: LogOutRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _userLogOut: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var UserData: LiveData<RequestResponse> = _userLogOut

    //******************  Iniciar sesion
    fun DoLogOutUser(context: Context): RequestResponse {
        try {
            val result = masterRepository.doLogOut(
                context = context
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _userLogOut.value = it
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
    fun resetLogOut() {
        _userLogOut.value = null
    }


}