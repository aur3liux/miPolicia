package com.aur3liux.naats.model

sealed class RequestResponse {
    class Succes(): RequestResponse()
    class Error(val estatusCode: Int, val errorMessage: String?): RequestResponse()
}