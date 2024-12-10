package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestPredenuncia
import com.aur3liux.mipolicia.services.PredenunciaRepo
import org.json.JSONObject
import javax.inject.Inject

class PredenunciaVMFactory(private val predenunciaRepository: PredenunciaRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PredenunciaVM::class.java)){
            return PredenunciaVM(predenunciaRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class PredenunciaVM @Inject constructor(private val masterRepository: PredenunciaRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _metaData: MutableLiveData<RequestPredenuncia> = MutableLiveData<RequestPredenuncia>()
    var PredenunciaMetaData: LiveData<RequestPredenuncia> = _metaData

    //******************  Lanzar solicitud
    fun DoSendPredenuncia(context: Context, jsonObject: JSONObject): RequestPredenuncia {
        try {
            val result = masterRepository.doPredenuncia(
                context = context,
                jsonObj = jsonObject
            )
            MediatorLiveData<RequestPredenuncia>().apply {
                addSource(result) {
                    _metaData.value = it
                }
                observeForever {
                    // Log.i("OBSERVER", UserData!!.value.toString())
                }
            }
            return RequestPredenuncia.Error(0, "Please wait")
        } catch (ex: Exception) {
            return RequestPredenuncia.Error(0, "Error de acceso ${ex.message}")
        }
    }//Login


    @SuppressLint("NullSafeMutableLiveData")
    fun resetPredenuncia() {
        _metaData.value = null
    }


}