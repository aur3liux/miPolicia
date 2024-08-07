package com.aur3liux.naats.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.naats.model.RequestCurp
import com.aur3liux.naats.model.RequestResponse
import com.aur3liux.naats.services.CurpRepo
import org.json.JSONObject
import javax.inject.Inject


class ConsultaCurpVMFactory(private val curpRepository: CurpRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConsultaCurpVM::class.java)){
            return ConsultaCurpVM(curpRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}

class ConsultaCurpVM @Inject constructor(private val masterRepository: CurpRepo): ViewModel() {
    //Para controlar los datos del usuario
    private var _userCurp: MutableLiveData<RequestCurp> = MutableLiveData<RequestCurp>()
    var CurpData: LiveData<RequestCurp> = _userCurp

    //******************  Iniciar sesion
    fun DoConsultaCurp(context: Context, curp: String): RequestCurp {
        try {
            val result = masterRepository.getCurpData(
                context = context,
                curp = curp
            )
            MediatorLiveData<RequestCurp>().apply {
                addSource(result) {
                    _userCurp.value = it
                }
                observeForever {
                    // Log.i("OBSERVER", UserData!!.value.toString())
                }
            }
            return RequestCurp.Error(0, "Please wait")
        } catch (ex: Exception) {
            return RequestCurp.Error(0, "Error de acceso ${ex.message}")
        }
    }//Login



    @SuppressLint("NullSafeMutableLiveData")
    fun resetCurp() {
        _userCurp.value = null
    }


}