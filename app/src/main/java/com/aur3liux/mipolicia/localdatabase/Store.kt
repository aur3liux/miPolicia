package com.aur3liux.mipolicia.localdatabase

/*
* Variant: debugAndroidTest
Config: debug
Store: /Users/aureliolopeztest/.android/debug.keystore
Alias: AndroidDebugKey
MD5: 7C:A3:5C:2A:4A:F8:65:7A:AF:BA:14:3F:30:70:EF:7A
SHA1: 90:1D:93:68:E2:4C:86:19:25:81:B1:DF:42:51:0B:4F:FF:2B:9D:3B
SHA-256: 1D:0E:D6:47:4B:A2:84:B5:17:80:51:ED:C2:83:0E:61:F6:E2:1D:1C:48:D8:88:7D:8E:1A:A2:F4:1C:66:C7:F5
Valid until: jueves, 9 de abril de 2054
* */

class Store {
    object APP {
        const val name = "Mi policía"
        const val txCiberDelito = "Ataque del que fuíste víctima"
        const val txReportes = "Seleccione el tipo de reporte"
        const val txUbicacion = "Vacía"
        const val txDescripcion = "Descripción de motivos"
        const val txNombreAgente = "Si sabe su nombre, escribalo aquí"
        const val txNumeroUnidad = "Si tiene un numero de unidad"
    }

    object API_URL {
        const val API_GOOGLE = "AIzaSyCaNrRXeqbx6JvtXHNH_Ai2334c8BWUBcE"
        const val BASE_URL = "http://187.141.117.119/mipolicia-api/public"
    }

    object DB {
        const val NAME = "Mipolic14.db"
        const val TB_USER = "UsuarioMipolic14Tbl"
        const val TB_SESION = "SessionMipolic14Tbl"
        const val TB_TOKEN_PUSH = "Tok3nPushMipolic14Tbl"
        const val TB_LOCATIONS = "LocationsMipolic14Tbl"
        const val TB_CALLS = "Llamadas911Tbl"

        const val TB_MYPREDENUNCIAS = "MyPredenunciasMipolic14Tbl"
        const val TB_PREDENUNCIA_TMP = "PredenunciaTmpMipolic14Tbl"
        const val TB_AVISOS = "AvisosMipolic14Tbl"
    }

    object User {
        const val EMAIL = "email"
        const val NOMBRE = "nombre"
        const val APELLIDOS = "apellidos"
        const val TELEFONO = "telefono"
        const val MUNICIPIO = "municipio"
        const val LOCALIDAD = "localidad"
        const val COLONIA = "colonia"
        const val CP = "cp"
        const val TOKEN = "tokenAccess"
        const val NOTIFICATION_TOKEN = "notificationToken"
        const val DEVICE = "device"
    }

    object SESION {
        //0 Debe iniciar sesion, 1 Sesion iniciada
        const val STATUS = "statusSesion"
    }

    object TOKEN_PUSH_NOTIFICATION {
        const val TOKEN = "token"
    }

    object Locations {
        const val LATITUD = "latitud"
        const val LONGITUD = "longitud"
    }

    //-----------------------------------

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