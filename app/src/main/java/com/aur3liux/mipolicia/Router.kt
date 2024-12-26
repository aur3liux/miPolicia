package com.aur3liux.mipolicia


sealed class Router(val route: String) {
    //Estos Graph estan en Home
    object PERFIL_VIEW: Router("PerfilView")
    object SECTOR_VIEW: Router("SectorView")
    object MARCOLEGAL_VIEW: Router("MarcoLegalView")
    object DETALLES_MARCOLEGAL: Router("DetallesMarcoLegal/{seccion}"){
        fun detallesMarcoLegal(seccion: String) = "DetallesMarcoLegal/$seccion"
    }
    object AVISO_PRIVACIDAD: Router("AvisoPrivacidad")
    object REGLAMENTO_TRANSITO: Router("ReglamentoTransito")
    object REPORTE_CIUDADANO: Router("ReporteCiudadano")
    object QUEJAS_FELICITACIONES: Router("QuejaFelicitacionView")

    object VIDEO_PLAYER: Router("VideoPlayer")
    object POLICIA_CIBERNETICA: Router("PoliciaCiberneticaView")
    object REPORTE_CIBERNETICA: Router("ReporteCiberneticaView")

    //Estos están en MainActivity
    object HOME: Router("Home")
    object LOAD_VIEW: Router("LoadView")
    object LOGIN: Router("LoginView")
    object REGISTRO_VIEW: Router("RegistroView")
    object CLOSE_SESSION: Router("CloseSessionConfirm")

}