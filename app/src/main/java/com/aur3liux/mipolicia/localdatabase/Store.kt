package com.aur3liux.naats.localdatabase

class Store {
    object APP {
        const val name = "Naat´s"
        const val txDelito = "Tipo de delito"
        const val txsubDelito = "Elegir subcategoría*"
    }

    object API_URL {
        const val API_GOOGLE = "AIzaSyCaNrRXeqbx6JvtXHNH_Ai2334c8BWUBcE"
        //const val BASE_URL = "https://predapi.it-works.mx"
        const val BASE_URL = "http://187.157.28.108:2024/apip/public"
    }

    object DB {
        const val NAME = "N44ts.db"
        const val TB_USER = "UsuarioNa44tsTbl"
        const val TB_SESION = "SessionN44tsTbl"
        const val TB_TOKEN_PUSH = "Tok3nPushN44tsTbl"
        const val TB_LOCATIONS = "LocationsN44tsTbl"
        const val TB_MYPREDENUNCIAS = "MyPredenunciasN44tsTbl"
        const val TB_PREDENUNCIA_TMP = "PredenunciaTmpN44tsTbl"
        const val TB_AVISOS = "AvisosN44tsTbl"
    }

    object User {
        const val CURP = "curp"
        const val USER = "userName"
        const val TOKEN = "tokenAccess"
        const val NOMBRE = "nombre"
        const val PATERNO = "paterno"
        const val MATERNO = "materno"
        const val SEXO = "sexo"
        const val CORREO = "correo"
        const val TELEFONO = "telefono"
        const val CIUDAD = "ciudad"
        const val LOCALIDAD = "localidad"
        const val COLONIA = "colonia"
        const val CALLE = "calle"
        const val EDO_CIVIL = "estadoCivil"
        const val OCUPACION = "ocupacion"
        const val F_NACIMIENTO = "fNacimiento"
    }

    object SESION {
        const val STATUS = "statusSesion"
    }

    object TOKEN_PUSH_NOTIFICATION {
        const val TOKEN = "token"
    }

    object Locations {
        const val LATITUD = "latitud"
        const val LONGITUD = "longitud"
    }

    object AVISOS{
        const val ref = "ref"
        const val folioAviso = "folio"
        const val descripcion = "descripcion"
        const val tipo = "tipo"
        const val fecha = "fecha"
    }

    object PREDENUNCIA{
        const val folio = "folioPred"
        const val estatus = "estatusPred"
        const val modulo = "moduloPred"
        const val ciudad = "ciudad"
        const val fechaCreacion = "fechaPred"
        const val horaCreacion = "horaPred"
        const val fechaLastModif = "fechaLastCreaPred"
        const val horaLastModif = "horaLastCreaPred"
        const val delito = "delitoPred"
        const val subDelito = "subdelitoPred"
        const val descripcion = "descripcionPred"
    }

    object PREDENUNCIA_TMP{
        const val indice = "indice"
        const val latitudPred = "latitudPred"
        const val longitudPred = "longitudtudPred"
        const val delito = "delitoPred"
        const val indexDelito = "indexDelito"
        const val subDelito = "subdelitoPred"
        const val indexSubdelito = "indexSubdelito"
        const val descripcion = "descripcionPred"
    }

}