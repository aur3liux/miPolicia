package com.aur3liux.mipolicia.model

data class SectorInfo(val nombreSector: String = "",
                  val nombreResponsable: String = "",
                  val address: String = "",
                  val phone: String = "",
                  val photo: String = "")

sealed class SectorResponse {
    class Succes(val sectorData: SectorInfo): SectorResponse()
    class Error(val estatusCode: Int, val errorMessage: String?): SectorResponse()
}
