package com.aur3liux.mipolicia.model

data class SectorInfo(val nombreSector: String = "",
                  val nombreDirector: String = "",
                  val nombreResponsable_A: String = "",
                  val nombreResponsable_B: String = "",
                  val address: String = "",
                  val phone: String = "",
                  val phone_do: String = "",
                  val photo_Director: String = "",
                  val photo_A: String = "",
                  val photo_B: String = "")

sealed class SectorResponse {
    class Succes(val sectorData: SectorInfo): SectorResponse()
    class Error(val estatusCode: Int, val errorMessage: String?): SectorResponse()
}
