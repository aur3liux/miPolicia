package com.aur3liux.naats.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.naats.model.RequestResponse
import com.aur3liux.naats.services.DownloadAvisosRepo
import org.json.JSONObject
import javax.inject.Inject

class DownloadAvisosVMFactory(private val downloadRepository: DownloadAvisosRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AvisosVM::class.java)){
            return AvisosVM(downloadRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class AvisosVM @Inject constructor(private val masterRepository: DownloadAvisosRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _aviso: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var Avisos: LiveData<RequestResponse> = _aviso

    //****************** Obtenemos el QR  de la predenuncia
    fun downloadAvisos(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.DownloadAvisos(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _aviso.value = it
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
    fun resetDownloadAvisos() {
        _aviso.value = null
    }


}