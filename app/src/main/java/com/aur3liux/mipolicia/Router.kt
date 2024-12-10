package com.aur3liux.mipolicia


sealed class Router(val route: String) {
    //Estos Graph estan en Home
    object FGECAM_VIEW: Router("FichaFgecamView")
    object PERFIL_VIEW: Router("PerfilView")
    object MARCOLEGAL_VIEW: Router("MarcoLegalView")
    object DETALLES_MARCOLEGAL: Router("DetallesMarcoLegal/{seccion}"){
        fun detallesMarcoLegal(seccion: String) = "DetallesMarcoLegal/$seccion"
    }
    object BUZON_VIEW: Router("BuzonView")

    object VIDEO_PLAYER: Router("VideoPlayer")
    object POLICIA_CIBERNETICA: Router("PoliciaCiberneticaView")
    object REPORTE_CIBERNETICA: Router("ReporteCiberneticaView")

    //Estos están en MainActivity
    object HOME: Router("Home")
    object LOAD_VIEW: Router("LoadView")
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