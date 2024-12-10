package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.ConsultaFolioRepo
import org.json.JSONObject
import javax.inject.Inject

class ConsultaFolioVMFactory(private val folioRepository: ConsultaFolioRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConsultaFolioVM::class.java)){
            return ConsultaFolioVM(folioRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class ConsultaFolioVM @Inject constructor(private val masterRepository: ConsultaFolioRepo): ViewModel() {
    //Consulta el folio de una predenuncia
    private var _folioData: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var FolioData: LiveData<RequestResponse> = _folioData

    //******************  Iniciar sesion
    fun DoConsultaFolio(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.doConsultaFolio(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _folioData.value = it
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
    fun resetConsultaFolio() {
        _folioData.value = null
    }


}