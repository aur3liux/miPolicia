package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.RenewRepo
import org.json.JSONObject
import javax.inject.Inject

class RenewVMFactory(private val loginRepository: RenewRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RenewVM::class.java)){
            return RenewVM(loginRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class RenewVM @Inject constructor(private val masterRepository: RenewRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _userRenew: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var RenewData: LiveData<RequestResponse> = _userRenew

    //******************  Iniciar sesion
    fun DoRenew(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.doRenew(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _userRenew.value = it
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
    fun resetRenew() {
        _userRenew.value = null
    }


}