package com.aur3liux.mipolicia.model

sealed class RequestCurp {
    class Succes(val data: CurpData): RequestCurp()
    class Error(val estatusCode: Int, val errorMessage: String?): RequestCurp()
}