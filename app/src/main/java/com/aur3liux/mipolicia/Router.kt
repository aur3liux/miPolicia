package com.aur3liux.naats


sealed class Router(val route: String) {
    //Estos Graph estan en Home
    object MAP_HOME_VIEW: Router("MapHome")
    object FGECAM_VIEW: Router("FichaFgecamView")
    object PERFIL_VIEW: Router("PerfilView")
    object BUZON_VIEW: Router("BuzonView")

    //Estos están en MainActivity
    object HOME: Router("Home")
    object LOAD_VIEW: Router("LoadView")
    object PANTALLA_INICIAL: Router("PantallaInicial")
    object LOGIN: Router("LoginView")
    object ACCESO_REGISTRO: Router("AccesoRegistroView")
    object FINISH_REGISTRO: Router("RegistroFinishView")
    object CLOSE_SESSION: Router("CloseSessionConfirm")
    object AVISO_PRIVACIDAD: Router("AvisoPrivacidad")
    object PREDENUNCIA: Router("Predenuncia")
    object HELP_VIEW: Router("HelpView")
    object EXPEDIENTE_LIST: Router("Expediente")
    object SUBCATEGORIA_DELITOS: Router("ListSubcategoriaDenuncia/{index}"){
        fun createRoute(index: Int) = "ListSubcategoriaDenuncia/$index"
    }
    object FINISH_PREDENUNCIA: Router("FinishPredenuncia/{folio}"){
        fun finishPredenuncia(folio: String) = "FinishPredenuncia/$folio"
    }
    object DETALLES_PREDENUNCIA: Router("DetallesPredenuncia/{folio}"){
        fun detallesPredenuncia(folio: String) = "DetallesPredenuncia/$folio"
    }
}