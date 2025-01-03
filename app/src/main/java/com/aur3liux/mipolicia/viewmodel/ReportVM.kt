package com.aur3liux.mipolicia.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.mipolicia.model.RequestResponse
import com.aur3liux.mipolicia.services.ReporteRepo
import org.json.JSONObject
import javax.inject.Inject

class ReportVMFactory(private val reportRepository: ReporteRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReportVM::class.java)){
            return ReportVM(reportRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class ReportVM @Inject constructor(private val masterRepository: ReporteRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _report: MutableLiveData<RequestResponse> = MutableLiveData<RequestResponse>()
    var ReportData: LiveData<RequestResponse> = _report

    //******************  Enviar reporte
    fun DoSentReport(context: Context, jsonObject: JSONObject): RequestResponse {
        try {
            val result = masterRepository.doSendReport(
                context = context,
                json = jsonObject
            )
            MediatorLiveData<RequestResponse>().apply {
                addSource(result) {
                    _report.value = it
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
    fun resetSentReport() {
        _report.value = null
    }


}