package com.aur3liux.mipolicia.model

sealed class RequestPredenuncia {
    class Succes(val metadata: PredenunciaMetaData): RequestPredenuncia()
    class Error(val estatusCode: Int, val errorMessage: String?): RequestPredenuncia()
}