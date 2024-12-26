package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.model.SectorResponse
import com.aur3liux.mipolicia.services.ConsultaSectorRepo
import javax.inject.Inject

class ConsultaSectorVMFactory(private val sectorRepository: ConsultaSectorRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConsultaSectorVM::class.java)){
            return ConsultaSectorVM(sectorRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class ConsultaSectorVM @Inject constructor(private val masterRepository: ConsultaSectorRepo): ViewModel() {
    //Consulta el folio de una predenuncia
    private var _callData: MutableLiveData<SectorResponse> = MutableLiveData<SectorResponse>()
    var SectorData: LiveData<SectorResponse> = _callData

    //******************  Iniciar sesion
    fun DoIntentoLlamada(context: Context, latitud: Double, longitud: Double): SectorResponse {
        try {
            val result = masterRepository.doConsultaSector(
                context = context,
                latitud = latitud,
                longitud = longitud
            )
            MediatorLiveData<SectorResponse>().apply {
                addSource(result) {
                    _callData.value = it
                }
                observeForever {
                    // Log.i("OBSERVER", UserData!!.value.toString())
                }
            }
            return SectorResponse.Error(0, "Please wait")
        } catch (ex: Exception) {
            return SectorResponse.Error(0, "Error de acceso ${ex.message}")
        }
    }//Login


    @SuppressLint("NullSafeMutableLiveData")
    fun resetConsultaSector() {
        _callData.value = null
    }


}