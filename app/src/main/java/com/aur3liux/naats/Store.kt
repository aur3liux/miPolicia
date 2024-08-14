package com.aur3liux.naats

class Store {
    object APP {

        const val name = "Naat´s"
        const val txDelito = "Seleccione el tipo de delito"
    }

    object API_URL {
        const val API_GOOGLE = "AIzaSyCaNrRXeqbx6JvtXHNH_Ai2334c8BWUBcE"
        const val BASE_URL = "https://predapi.it-works.mx"
    }

    object DB {
        const val NAME = "N44ts.db"
        const val TB_USER = "UsuarioNa44tsTbl"
        const val TB_SESION = "SessionN44tsTbl"
        const val TB_LOCATIONS = "LocationsN44tsTbl"
    }

    object User {
        const val USER = "userName"
        const val TOKEN = "tokenAccess"
    }

    object SESION {
        const val STATUS = "statusSesion"
    }

    object Locations {
        const val LATITUD = "latitud"
        const val LONGITUD = "longitud"
    }

}