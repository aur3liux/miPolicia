package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.IntentollamadaRepo
import org.json.JSONObject
import javax.inject.Inject

class IntentoLlamadaVMFactory(private val itentoRepository: IntentollamadaRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IntentoLlamadaVM::class.java)){
            return IntentoLlamadaVM(itentoRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class IntentoLlamadaVM @Inject constructor(private val masterRepository: IntentollamadaRepo): ViewModel() {
    //Consulta el folio de una predenuncia
    private var _callData: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var CallData: LiveData<RequestResponse> = _callData

    //******************  Iniciar sesion
    fun DoIntentoLlamada(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.doRegistroIntentoLlamada(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _callData.value = it
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
    fun resetIntentoLlamada() {
        _callData.value = null
    }


}