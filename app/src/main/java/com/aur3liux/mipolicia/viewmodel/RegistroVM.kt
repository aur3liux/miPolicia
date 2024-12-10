package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.RegistroRepo
import org.json.JSONObject
import javax.inject.Inject

class RegistroVMFactory(private val registroRepository: RegistroRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegistroVM::class.java)){
            return RegistroVM(registroRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class RegistroVM @Inject constructor(private val masterRepository: RegistroRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _userRegistro: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var RegistroData: LiveData<RequestResponse> = _userRegistro

    //******************  Lanzar registro
    fun DoRegistroUser(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.doRegistro(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _userRegistro.value = it
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
    fun resetRegistro() {
        _userRegistro.value = null
    }


}