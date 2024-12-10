package com.aur3liux.naats.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.naats.model.RequestResponse
import com.aur3liux.naats.services.DownloadExpedienteRepo
import org.json.JSONObject
import javax.inject.Inject

class DownloadExpedienteVMFactory(private val downloadRepository: DownloadExpedienteRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MiExpedienteVM::class.java)){
            return MiExpedienteVM(downloadRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class MiExpedienteVM @Inject constructor(private val masterRepository: DownloadExpedienteRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _expediente: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var Expediente: LiveData<RequestResponse> = _expediente

    //****************** Obtenemos el QR  de la predenuncia
    fun downloadExpediente(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.DownloadMyExpediente(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _expediente.value = it
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
    fun resetDownloadExpediente() {
        _expediente.value = null
    }


}